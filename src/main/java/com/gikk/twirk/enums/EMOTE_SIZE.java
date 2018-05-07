package com.gikk.twirk.enums;

/**Enum for representing different sizes of Twitch Emotes.<br><br>
 * 
 * Emotes comes in three different sizes: Small, Medium and Large.
 * 
 * @author Gikkman
 */
public enum EMOTE_SIZE{
	SMALL("/1.0"),
	MEDIUM("/2.0"),
	LARGE("/3.0");		
	public final String value;
	private EMOTE_SIZE(String val){ this.value = val; }
}
