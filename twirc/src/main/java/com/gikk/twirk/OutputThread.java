package com.gikk.twirk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;

/**This class handles all outgoing IRC traffic.<br><br>
 * 
 * The implementation is intended to be thread safe and handle all potential errors (<u>keyword: INTENDED</u>). 
 * That means that we can have multiple threads feeding the message queue safely and still operate without any trouble.
 * 
 * @author Simon
 *
 */
class OutputThread extends Thread{
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private final Twirk connection;
	private final BufferedWriter writer;
	private final OutputQueue queue;
	
	private boolean isConnected = true;
	
	private int MESSAGE_GAP_MILLIS = 1500;	//We may not send more than 20 messages to the Twitch server / 30 seconds
	
	//***********************************************************************************************
	//											CONSTRUCTOR
	//***********************************************************************************************
	public OutputThread(Twirk connection, OutputQueue queue, BufferedReader reader, BufferedWriter writer){
		this.connection = connection;
		this.queue = queue;
		this.writer = writer;
		
		this.setName("Twirk-OutputThread");
	}
	
	@Override
	public void run(){
		String line;
        while( isConnected ){
        	try {
        		line = queue.next();
	        	if( line != null ) {
	        		sendLine(line);
	        	}
	        	else {
	        		//If we get a null line from the queue, it might mean that the application interrupted the thread
	        		// and wants us to shut down.
	        		isConnected = connection.isConnected();	
	        	}        	
	        	 Thread.sleep(MESSAGE_GAP_MILLIS); } 
        	catch (Exception ignored) { 
        		/* Being interrupted probably means that we are about to shut down.
        		 * If the socket is closed, it also means that we are about to shut down.
        		 * 
        		 * If we are about to close down, isConnected will be set to false so we can just go back
        		 * to the loop and automatically terminate from there.  */ 
    		}
        }
    }
	
	//***********************************************************************************************
	//											PUBLIC
	//***********************************************************************************************
	
	/**Circumvents the message queue completely and attempts to send the message at once. Should only be used for sending
	 * PING responses.
	 * 
	 * @param message
	 */
	public void quickSend(String message) {
		try {
			sendLine(message);
		} catch (SocketException e) {
			System.err.println("Could not QuickSend message. Socket was closed (OutputThread @ Twirk)");
		}		
	}
	
	/**Tells the thread to stop execution. Future messages written to the {@code OutputQueue} will
	 * not be sent by this {@code OutputThread} unless it is started again.
	 */
	public void end() {
		isConnected = false;
		this.queue.releaseWaitingThreads();
	}
	
	//***********************************************************************************************
	//											PRIVATE
	//***********************************************************************************************

	/**Sends a message AS IS, without modifying it in any way. Users of this method are responsible for
	 * formating the string correctly: 
	 * <br>
	 * That means, who ever uses this method has to manually assign channel data and the similar to the
	 * message.
	 * 
	 * @param message The message to write to out BufferedWriter
	 */
	private void sendLine(String message) throws SocketException{
		if( !isConnected ){
			System.err.println("Twirk is not connected! Sending messages will not succeed!");
		}
		/**An IRC message may not be longer than 512 characters. Also, they must end with \r\n,
		 * so if the supplied message is longer than 510 characters, we have to cut it short.
		 * 
		 * While it might be an alternative to split the message and send it in different batches,
		 * that would mean that we lose potential commands and such. Hence, instead we just drop 
		 * everything beyond the 510th character.
		 */
		if( message.length() > 510 ){
			message = message.substring(0, 511);
		}		
		
		try{
			System.out.println("OUT " + message);
			synchronized (writer) {		
				writer.write(message + "\r\n");
				writer.flush();
			}
		} catch (IOException e){
			if( e.getMessage().matches("Stream closed") ){
				System.err.println("Cannot send message: " + message +" Stream closed");
				return;
			}
			e.printStackTrace();
		}
	}
}
