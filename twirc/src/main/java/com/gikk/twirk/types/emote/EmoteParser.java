package com.gikk.twirk.types.emote;

import java.util.LinkedList;

public class EmoteParser {
	private static final String EMOTES_IDENTIFIER = "emotes="; 

	public static LinkedList<Emote> parseEmotes(String content, String tag) {
		/* Emotes come in sets formated like this:
		 * 
		 * emotes=15614:0-6/4685:8-13,15-21		(Message = tmrToad tmrHat tmrHat)
		 * 
		 * The first number indicates the emotes ID. The emote ID is follower by a :
		 * The numbers separated by a - indicates the indices in the message that makes up the emote. 
		 *     If an emote appears several times in a message, the different index ranges are separated by a ,
		 *     
		 * So we find the begining of the emotes section and the end of the section.
		 * Then, check that the message actually contains an emotes section and that
		 * the emote section actually contains data.
		 */
		LinkedList<Emote> emotes = new LinkedList<Emote>();
		
		int begin = tag.indexOf( EMOTES_IDENTIFIER );
		int end =   tag.indexOf(';', begin );	
		if( begin == -1 || begin + EMOTES_IDENTIFIER.length() == end){
			return emotes;
		}
		
		/* This segment is a head ache, but here is a basic idea of what it is doing:
		 * Iterate through the emotes-section. 
		 *    The first part will be the emote ID. it is terminated by a :
		 *    The second part is the emote's start index. It is terminated by a -
		 *    The third part is the emote's end index. It is terminated EITHER by a / or a , or by the segment ending
		 *       If terminated by a , the emote is present more than once in the message. So the next part will be a new begin index
		 *       If terminated by a / there is another emote in the message. The next part is a new emote ID
		 *       If terminated by the segment ending we simply finish the last begin-end pair and we are done
		 */
		String emotesString = tag.substring( begin + EMOTES_IDENTIFIER.length(), end);
		
		EmoteImpl emote = new EmoteImpl();
		StringBuilder str = new StringBuilder();
		String emoteID = "", beginIndex = "";
		for( char c : emotesString.toCharArray() ){
			switch(c) {
			case ':':
				emoteID = str.toString();
				str.setLength(0);
				break;
			case '-' :
				beginIndex = str.toString();
				str.setLength(0);
				break;
			case ',':
				addIndices(emote, beginIndex, str.toString() );
				str.setLength(0);
				break;
			case '/':
				finalizeEmote(content, emotes, emote, emoteID, beginIndex, str.toString() );
				emote = new EmoteImpl();
				str.setLength(0);
				break;
			default:
				str.append(c);
			}
		}
		//The emotes segment ended, so we finish the current emote's begin-end pair, then we're done
		finalizeEmote(content, emotes, emote, emoteID, beginIndex, str.toString());
		
		return emotes;
	}
	
	private static void finalizeEmote(String content, LinkedList<Emote> emotes, EmoteImpl emote, String emoteID, String beginIndex, String endIndex) {
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;	//The end index we receive from Twitch is inclusive, but Java is almost always exclusive
		emote.addIndices(begin, end);	
		
		emote.setEmoteID( Integer.parseInt( emoteID ) );
		emote.setPattern( content.substring(begin, end) );
		emotes.add(emote);		
	}

	private static void addIndices(EmoteImpl emote, String beginIndex, String endIndex){
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;
		emote.addIndices(begin, end);	
	}
}
