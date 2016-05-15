package com.gikk.twirk.types;

/**Class for representing a CLEARCHAT from Twitch.<br><br>
 * 
 * A CLEARCHAT means that some parts of chat have been purged. CLEARCHAT comes in two forms: USER and COMPLETE. <ul>
 * <li>USER - A user has been purged. <code>target</code> tells us the name of the user whom was purged
 * <li>COMPLETE - The channel has been purged. <code>target</code> will be empty.
 * <br>
 * @author Gikkman
 */
public class TwirkClearChat {
	public static enum MODE{ USER, COMPLETE };

	/** Tells us the MODE of the CLEARCHAT. Can be USER, if a user was purged, or COMPLETE, if the entire chat was purged */
	public final MODE mode;
	/** Tells us the target of the CLEARCHAT. Might be a user name, if a user was purged, or empty, if the entire chat was purged */
	public final String target;
	
	/**Creates a new TwirkClearChat from a {@link TwirkMessage}. The user is responsible is responsible
	 * for making sure that the {@link TwirkMessage}'s command is CLEARCHAT
	 * 
	 * @param message The TwirkMessage
	 */
	public TwirkClearChat(TwirkMessage message){
		if( message.getContent().isEmpty() ){
			this.mode = MODE.COMPLETE;
			this.target = "";
		}
		else{
			this.mode = MODE.USER;
			this.target = message.getContent();
		}
	}
}
