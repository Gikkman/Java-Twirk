package com.gikk.twirc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**Class for handling all incoming IRC traffic (after the initial connection is established). <br><br>
 * 
 * The implementation is intended to be thread safe and handle all potential errors (<u>keyword: INTENDED</u>).
 * 
 * @author Simon
 *
 */
class InputThread extends Thread{
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private final Twirk connection;
	private final BufferedReader reader;
	
	private boolean isConnected = true;
	
	//***********************************************************************************************
	//											CONSTRUCTOR
	//***********************************************************************************************
	public InputThread(Twirk connection, BufferedReader reader, BufferedWriter writer){
		this.connection = connection;
		this.reader  = reader;
		
		this.setName("Twirk-InputThread");
	}
	
	@Override
	public void run() {	
		try{
	        while (isConnected) {
	            try {
	                String line = null;
	                while ( (line = reader.readLine()) != null ) {           	
	                	try{
		                	connection.incommingMessage(line);
	                	} catch (Exception e) {
	                		System.err.println("Error in handling the incomming Irc Message");
	                		e.printStackTrace();
	                	}
	                }
	                //If we reach this line, it means the line was null. That only happens if the end of the stream's been reached
	                isConnected = false;
	            }
	            catch (SocketTimeoutException e){
	            	//If we time out, that means we haven't seen anything from server in a while, so we ping it
	            	connection.serverMessage("PING " + System.currentTimeMillis());
	            }
	            catch (IOException e) {
	            	//This probably means we force closed the socket. In case something else occurred, we print the StackTrace
	            	String message = e.getMessage();
	            	if( (message.indexOf("Socket Closed") >= 0) ){
	            		//Ignore
	            	} else if ( message.indexOf("Connection reset") >= 0 || message.indexOf("Stream closed") >= 0) {
	            		System.err.println( message );
	            	}
	            	else {
	            		e.printStackTrace();
	            	}
	            	isConnected = false;
				}
	        }
		} catch( Exception e ){
			/*
			 * This catch-block should only be reached if an exception occurred while we were inside one of the 
			 * inner catch-blocks. That should be very rare, but can occur if the thread is interrupted while 
			 * handling another exception
			 */
			e.printStackTrace();
		}
        
		//If we have been disconnected, we close the connection and clean up the resources held by the IrcConnection.
        //However, if we are disconnected intentionally, we don't need to try to disconnect again
		if( connection.isConnected() )
			connection.disconnect();
	}

	//***********************************************************************************************
	//											PUBLIC
	//***********************************************************************************************
	public void end() {
		isConnected = false;
	}
	public boolean isConnected(){
		return isConnected;
	}
}
