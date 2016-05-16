package com.gikk.twirk;

import java.util.LinkedList;

/**We need a specialized messaging queue to be able to handle <ul>
 * <li>A) one consumer/multiple producers and 
 * <li>B) being able to put messages to the front and back of the queue.
 *</ul>
 * We also want the {@link #next()} method to block until there is anything to send to the IRC server in the 
 * queue.<br><br>
 * 
 * Due to these reasons, we cannot use a normal queue. Thus we use this thread safe and blocking implementation.
 * 
 * @author Gikkman
 *
 */
class OutputQueue {
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private final LinkedList<String> queue = new LinkedList<String>();
	
	//***********************************************************************************************
	//											PUBLIC
	//***********************************************************************************************
	/**Adds a message to the back of the output queue
	 * 
	 * @param s The message to add to the queue
	 */
	public void add(String s){
		synchronized (queue) {
			queue.add(s);
			queue.notify();
		}
	}
	
	/**Adds a message to the front of the output queue.<br>
	 * Can be useful for prioritized messages
	 * 
	 * @param s The message to add to the queue
	 */
	public void addFirst(String s){
		synchronized (queue) {
			queue.addFirst(s);
			queue.notify();
		}
	}
	
	/**A <b>blocking</b> call that retrieves the next message from the queue.
	 * If no message is currently in the queue, this method will block until a message appears.
	 * 
	 * @return The next message OR <code>null</code>(if we were interrupted and there were no message in the queue)
	 */
	public String next(){
		synchronized (queue) {
			if( !hasNext() ){
				try { queue.wait(); } 
				catch (InterruptedException e) { 
					/* Being interrupted either means that there now is an element in the queue or
					 * that the application is shutting down.
					 * Anyway, we don't need to care about being interrupted, we simply proceed as
					 * usual and let the thread waiting for input handle the potential null return */ 					
				}
			}	
			if( !hasNext() )
				return null;
			
			String message = queue.getFirst();
			queue.removeFirst();
			return message;
		}
	}
		
	/**Checks if there are any elements currently in the queue
	 * 
	 * @return {@code true} if there are any messages in the queue
	 */
	public boolean hasNext(){
		synchronized (queue) {
			return queue.size() > 0;
		}
	}
	
	/**This will cause all threads waiting for new content in the {@code queue} to wake up. <br>
	 * If there is no content when this call is issued, waiting threads will return {@code null}
	 */
	void releaseWaitingThreads(){
		synchronized(queue){
			queue.notifyAll();
		}
	}
}
