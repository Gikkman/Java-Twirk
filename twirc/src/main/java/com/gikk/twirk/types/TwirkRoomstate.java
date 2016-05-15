package com.gikk.twirk.types;

/**Class for representing a ROOMSTATE from Twitch.<br><br>
 * 
 * A ROOMSTATE means that there were some changes to the rooms attributes. When you first join a room, you will get a snapshot
 * of the rooms current ROOMSTATE. If changes occur, only the relevant changes will be included in the ROOMSTATE
 * A room has four different attributes <ul>
 * <li>broadcasterLanguage - The chat language when broadcaster language mode is enabled, and empty otherwise.
 * <li>r9k - R9K mode means that messages longer than 9 characters must be unique. Useful for supressing copypastas and avoid spamming long messages
 * <li>SubMode - Only subs and mods can write. 
 * <li>SlowMode - Only mods can post frequent, all others must wait a designated time between posting messages.
 * 
 * @author Gikkman
 *
 */
public class TwirkRoomstate {
	private static final String LANGUAGE_IDENTIFIER = "broadcaster-lang=";
	private static final String R9K_IDENTIFIER = "r9k=";
	private static final String SUBS_IDENTIFIER = "subs-only=";
	private static final String SLOW_IDENTIFIER = "slow=";
	
	/** The chat language when broadcaster language mode is enabled, and empty otherwise.
	 * Might be null, if this ROOMSTATE had no information about it */
	public final String broadcasterLanguage;
	/** If 1, it means that R9K mode is enabled. 0 means it is disabled. -1 means that this ROOMSTATE did not mention r9k */
	public int r9kMode;
	/** If 1, it means that sub mode is enabled. 0 means it is disabled. -1 means that this ROOMSTATE did not mention subMode */
	public int subMode;
	/** If greater than 0, it means that users must wait more than X seconds between posting messages. -1 means that this ROOMSTATE did not mention slowMode */
	public int slowModeTimer;
	
	public TwirkRoomstate(TwirkMessage message) {
		String tag = message.getTag();
		
		String temp = parseFeature(LANGUAGE_IDENTIFIER, tag);
		broadcasterLanguage = temp.isEmpty() ? null : temp;
		
		temp = parseFeature(R9K_IDENTIFIER, tag);
		r9kMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = parseFeature(SLOW_IDENTIFIER, tag);
		slowModeTimer = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = parseFeature(SUBS_IDENTIFIER, tag);
		subMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
	}
	
	public String toString(){
		return LANGUAGE_IDENTIFIER + broadcasterLanguage 
			   + " " +
			   (r9kMode == 0 ? "" : (R9K_IDENTIFIER + r9kMode))
			   + " " +
			   (slowModeTimer == 0 ? "" : (SLOW_IDENTIFIER + slowModeTimer))
			   + " " +
			   (subMode == 0 ? "" : (SUBS_IDENTIFIER + subMode));
	}

	private String parseFeature(String IDENTIFIER, String tag) {
		int begin = tag.indexOf( IDENTIFIER );
		int end =   tag.indexOf(';', begin);	
		//If begin == -1, it means this tag was not present. If begin + IDENTIFIER.lenght() == end, it means that the tag was empty
		if( begin == -1 || begin + IDENTIFIER.length() == end)
			return "";
		//If end == -1, it means that this tag was the last one. We can get valid data if we take from begin (and remove the IDENTIFIER) to the end of the tag.
		else if( end == -1 )
			return tag.substring(begin + IDENTIFIER.length());
		//If none of them returned -1, it means that we can get valid data between begin (and remove the IDENTIFIER) and end
		else
			return tag.substring(begin + IDENTIFIER.length(), end);
	}	
}
