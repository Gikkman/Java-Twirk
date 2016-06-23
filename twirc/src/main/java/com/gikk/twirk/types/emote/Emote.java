package com.gikk.twirk.types.emote;

import java.util.LinkedList;

import com.gikk.twirk.enums.EMOTE_SIZE;

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
public interface Emote {
	

	/** Fetches the emotes numeric ID.
	 * 
	 * @return The emotes numeric ID
	 */
	public int getEmoteID();
	
	/** A list of pairs on indices. Each pair is a BEGIN-END pair of a emote occurrence in the message.<br><br>
	 * 
	 * For example, if the message is: {@code Kappa PogChamp Kappa}<br> the list for emote 'Kappa' will include these pairs:<ul>
	 * <li> (0,5)
	 * <li> (15,20)
	 * </ul>
	 * 
	 * @return The indices this emote takes up in the associated message
	 */
	public LinkedList<EmoteIndices> getIndices();
	
	/** The emote's pattern. For example: 'Kappa'
	 * 
	 * @return The emote's pattern
	 */
	public String getPattern();
	
	/**Emote images can be downloaded from Twitch's server, and come in three
	 * sizes. Use this method to get the address for the emote.
	 * 
	 * @param imageSize Emotes comes in three sizes. Specify which size you want
	 * @return The address for the emote's image
	 */
	public String getEmoteImageUrl(EMOTE_SIZE imageSize);
	
	/**Class for representing the beginning and end of an emote occurrence within a message
	 * 
	 * @author Gikkman
	 */	
	public static class EmoteIndices{
		public final int beingIndex;
		public final int endIndex;
		
		public EmoteIndices(int begin, int end){
			this.beingIndex = begin;
			this.endIndex = end;
		}
		
		public String toString(){
			return "(" +beingIndex + "," + endIndex + ")";
		}
	}
}
