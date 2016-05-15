package com.gikk.twirk.types;

/**Class for Twitch's ROOMSTATE
 * 
 * @author Gikkman
 *
 */
public class TwirkRoomstate {
	private static final String LANGUAGE_IDENTIFIER = "broadcaster-lang";
	private static final String R9K_IDENTIFIER = "r9k";
	private static final String SUBS_IDENTIFIER = "subs-only";
	private static final String SLOW_IDENTIFIER = "slow";
	
	
	public final String broadcasterLanguage;
	public int r9kTime;
	public int slowTime;
	public int subModeTime;
	
	public TwirkRoomstate(TwirkMessage message) {
		String tag = message.getTag();
		
		String temp = parseFeature(LANGUAGE_IDENTIFIER, tag);
		broadcasterLanguage = temp.isEmpty() ? "ENGLISH" : temp;
		
		temp = parseFeature(R9K_IDENTIFIER, tag);
		r9kTime = temp.isEmpty() ? 0 : Integer.parseInt(temp);
		
		temp = parseFeature(SLOW_IDENTIFIER, tag);
		slowTime = temp.isEmpty() ? 0 : Integer.parseInt(temp);
		
		temp = parseFeature(SUBS_IDENTIFIER, tag);
		subModeTime = temp.isEmpty() ? 0 : Integer.parseInt(temp);
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
