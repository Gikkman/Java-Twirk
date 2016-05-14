package com.gikk.twirc.messages;

import java.util.LinkedList;

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
	private String line, tag, prefix, command, target, content;
	
	private LinkedList<TwirkEmote> emotes = null;
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
		
		//TODO: Parse emotes
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
}
