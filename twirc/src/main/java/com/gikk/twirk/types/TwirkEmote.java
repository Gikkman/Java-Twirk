package com.gikk.twirk.types;

import java.util.LinkedList;

/**Class for representing a Twitch emote, which can be embedded into chat messages.<br><br>
 * 
 * An emote has three features: <ul>
 * <li>EmoteID - The numeric ID for the emote.
 * <li>Pattern - The emote's string pattern (Ex. 'Kappa')
 * <li>Indices - For each time an emote is present in a message, the emotes begin- and end index must be listed. Begin is inclusive, end is exclusive
 * </ul>
 * For example, if the message is: {@code Kappa PogChamp Kappa}<br> the TwirkEmote for Kappa will be:<ul>
 * <li>EmoteID = 25
 * <li>Pattern = Kappa
 * <li>Indices = (0,5), (15,20)
 * 
 * @author Gikkman
 *
 */
public class TwirkEmote {
	private final static String EMOTE_URL_BASE 	= "http://static-cdn.jtvnw.net/emoticons/v1/";	
	public static enum SIZE{
		SMALL("/1.0"),
		MEDIUM("/2.0"),
		LARGE("/3.0");
		
		private final String value;
		private SIZE(String val){ this.value = val; }
	}
	
	/** The emotes numeric ID */
	public int emoteID;
	/** A list of pairs on indices. Each pair is a BEGIN-END pair of a emote occurrence in the message */
	public LinkedList<EmoteIndices> indices = new LinkedList<EmoteIndices>();
	/** The emote's pattern. For example: 'Kappa' */
	public String pattern;
	
	void setEmoteID(int emoteID){
		this.emoteID = emoteID;
	}
	
	void setPattern(String pattern){
		this.pattern = pattern;
	}
	
	void addIndices(int begin, int end){
		this.indices.add( new EmoteIndices(begin, end) );
	}
	
	/**Emote images can be downloaded from Twitch's server, and come in three
	 * sizes. Use this method to get the address for the emote.
	 * 
	 * @param imageSize Emotes comes in three sizes. Specify which size you want
	 * @return The address for the emote's image
	 */
	public String emoteImageUrl(SIZE imageSize){
		return EMOTE_URL_BASE + emoteID + imageSize.value;
	}
	
	public String toString(){
		String out = emoteID + " " + ( pattern == null ? "NULL" : pattern) + "[ ";
		
		for( EmoteIndices index : indices )
			out += index.toString();
		
		out += " ]";
		
		return out;
	}
	
	/**Class for representing the beginning and end of an emote occurrence within a message
	 * 
	 * @author Gikkman
	 */
	public class EmoteIndices{
		public final int beingIndex;
		public final int endIndex;
		
		private EmoteIndices(int begin, int end){
			this.beingIndex = begin;
			this.endIndex = end;
		}
		
		public String toString(){
			return "(" +beingIndex + "," + endIndex + ")";
		}
	}
}
