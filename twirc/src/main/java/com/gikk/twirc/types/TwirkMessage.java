package com.gikk.twirc.types;

import java.util.LinkedList;
import java.util.List;

/**An encapsulation of a message sent via Twitch chat. <br><br>
 * 
 * A message can have many different structures, but since they are based on the 
 * IRC protocol, they will have 1 to 4 different segments, each segment separated
 * with a space (the last segment, the actual message, may contain any amount of spaces).
 * <ul>
 * <li>Prefix - This segment usually contains knowledge about the sender of the message.
 * <li>Command - This segment usually specifies what command the message issued
 * <li>Target - This segment tells about whom the message is targeted at (a person, a channel)
 * <li>Content - This segment contains the actual text, intended for the receiver to read. 
 * </ul>
 * In some cases, the message will contain less segments than 4. In that case that a segments
 * aren't present, their content will be empty.<br><br>
 * 
 * @author Gikkman
 *
 */
public class TwirkMessage {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private static final String EMOTES_IDENTIFIER = "emotes="; 
	private String line, tag, prefix, command, target, content;
	
	private final LinkedList<TwirkEmote> emotes = new LinkedList<TwirkEmote>();
	private boolean hasEmotes = false;
	
	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************	
	public TwirkMessage(String line){
		if( line.startsWith("@") )
			parseWithTag(line);
		else{
			this.line = line;
			parseWithoutTag(line);
		}
		
	}
	
	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	/**Retrieves the entire message, exactly as received from the server <b>but</b> with the 
	 * tag segment removed
	 * 
	 * @return The unformatted chat line, with the tag removed
	 */
	public String getLine(){
		return line;
	}
	
	public String getTag(){
		return tag;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getCommand() {
		return command;
	}

	public String getTarget() {
		return target;
	}

	public String getContent() {
		return content;
	}
	
	public String toString(){
		return line;
	}
	
	public boolean hasEmotes(){
		return hasEmotes;
	}
	
	public final List<TwirkEmote> getEmotes(){
		return emotes;
	}
	
	//***********************************************************
	// 				PRIVATE
	//***********************************************************
	private void parseWithTag(String line){
		String[] parts = line.split(" ", 5);
		
		if( parts.length == 5) {
			content = parts[4].charAt(0) == ':' ? parts[4].substring(1) : parts[4];
		} else {
			content = "";
		}
		if( parts.length >= 4) {
			target = parts[3];
		}else {
			target = "";
		}
		if( parts.length >= 3) {
			command = parts[2];
		}else {
			command = "";
		}
		if( parts.length >= 2) {
			prefix = parts[1];
		}else {
			prefix = "";
		}
		if( parts.length >= 1) {
			tag = parts[0];
		} else {
			tag = "";
		}
		
		parseEmotes(tag, content);
	}

	private void parseWithoutTag(String line){
		String[] parts = line.split(" ", 4);
		
		if( parts.length == 4) {
			content = parts[3].charAt(0) == ':' ? parts[3].substring(1) : parts[3];
		} else {
			content = "";
		}
		if( parts.length >= 3) {
			target = parts[2];
		}else {
			target = "";
		}
		if( parts.length >= 2) {
			command = parts[1];
		}else {
			command = "";
		}
		if( parts.length >= 1) {
			prefix = parts[0];
		}else {
			prefix = "";
		}
		
		tag = "";
	}
	
	//***********************************************************
	// 			 PARSE EMOTES SECTION
	//***********************************************************
	private void parseEmotes(String tag, String content) {
		/* Emotes come in sets formated like this:
		 * 
		 * emotes=15614:0-6/4685:8-13,15-21		(Message = tmrToad tmrHat tmrHat)
		 * 
		 * The first number indicates the emotes ID. The emote ID is follower by a :
		 * The numbers separated by a - indicates the indices in the message that makes up the emote. 
		 *     If an emote appears several times in a message, the different index ranges are separated by a ,
		 */
		int begin = tag.indexOf( EMOTES_IDENTIFIER );
		int end =   tag.indexOf(';', begin);	
		if( begin == end || begin == -1){
			return;
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
		hasEmotes = true;		
		String emotesString = tag.substring( begin + EMOTES_IDENTIFIER.length(), end);
		
		TwirkEmote emote = new TwirkEmote();
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
				finalizeEmote(emote, emoteID, beginIndex, str.toString() );
				emote = new TwirkEmote();
				str.setLength(0);
				break;
			default:
				str.append(c);
			}
		}
		//The emotes segment ended, so we finish the current emote's begin-end pair, then we're done
		finalizeEmote(emote, emoteID, beginIndex, str.toString());
	}
	
	private void finalizeEmote(TwirkEmote emote, String emoteID, String beginIndex, String endIndex) {
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;	//The end index we receive from Twitch is inclusive, but Java is almost always exclusive
		emote.addIndices(begin, end);	
		
		emote.setEmoteID( Integer.parseInt( emoteID ) );
		emote.setPattern( content.substring(begin, end) );
		emotes.add(emote);		
	}

	private void addIndices(TwirkEmote emote, String beginIndex, String endIndex){
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;
		emote.addIndices(begin, end);	
	}
}
