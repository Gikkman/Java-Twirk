package com.gikk.twirk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**Class for handling all incoming IRC traffic (after the initial connection is established). <br><br>
 * 
 * @author Gikkman
 *
 */
class InputThread extends Thread{
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private final Twirk connection;
	private final BufferedReader reader;
	
	private boolean isConnected = true;
	private boolean havePinged = false;
	
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
	                	havePinged = false;
	                	
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
	            	//If we time out, that means we haven't seen anything from server in a while.
	            	//We first attempt to ping the server, to see if it is still there.
	            	//If we time out again without receiving a responce from the server, we have disconnected.
	            	if( !havePinged ){
	            		connection.serverMessage("PING " + System.currentTimeMillis());
	            		havePinged = true;	            		
	            	}
	            	else {
	            		isConnected = false;
	            	}
	            }
	            catch (IOException e) {
	            	//This probably means we force closed the socket. In case something else occurred, we print the StackTrace
	            	String message = e.getMessage();
	            	if( (message.contains("Socket Closed")) ){
	            		//Ignore
	            	} else if ( message.contains("Connection reset") || message.contains("Stream closed")) {
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
	/**
	 * Tells the InputThread that we have disconnected. <br>
	 * This does not release the InputThread from waiting for messages, it simply
	 * makes the InputThread not start listening for messages again. To finish the
	 * InputThread, close the socket afterwards.
	 */
	public void end() {
		isConnected = false;
	}
}
