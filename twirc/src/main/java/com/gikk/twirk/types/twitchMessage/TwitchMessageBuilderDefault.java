package com.gikk.twirk.types.twitchMessage;

import java.util.LinkedList;

public class TwitchMessageBuilderDefault implements TwitchMessageBuilder{
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private static final String EMOTES_IDENTIFIER = "emotes="; 
	String line, tag, prefix, command, target, content;
	
	LinkedList<Emote> emotes = new LinkedList<Emote>();
	
	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	@Override
	public TwitchMessage build(String chatLine) {
		if( chatLine.startsWith("@") )
			parseWithTag(chatLine);
		else{
			parseWithoutTag(chatLine);
		}
		
		this.line = chatLine;		
		return new TwitchMessageImpl(this);
	}
	//***********************************************************
	// 				PRIVATE
	//***********************************************************	
	private void parseWithTag(String line){
		String[] parts = line.split(" ", 5);
		
		if( parts.length == 5) {
			this.content = parts[4].startsWith(":") ? parts[4].substring(1) : parts[4]; //Strip the potential ':' at beginning of content
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
		tag = "";
		
		StringBuilder build = new StringBuilder();
		char c; int i = 0; //We start at 1 to remove the ':' at the beginning of the prefix
		
		//The prefix is everything up till the first space
		while( (c = line.charAt(i++)) != ' ' )
			build.append(c);		
		this.prefix = build.toString().trim();
		build.setLength(0);
		
		//The command is everything up till the second space
		do
			build.append(c);
		while( (c = line.charAt(i++)) != ' ' );
		this.command = build.toString().trim();
		build.setLength(0);
		
		//The target is everything up till the ':', '+' or '-'
		do
			build.append(c);
		while( i < line.length() && (c = line.charAt(i++)) != ':' && c != '+' && c != '-');
		this.target = build.toString().trim();
		build.setLength(0);
		
		if( i == line.length() ){
			this.content = "";
			return;
		}

		//The content is everything else
		do
			build.append( c );
		while( i < line.length() && (c = line.charAt(i++)) != '\r' );
		String temp = build.toString().trim();
		this.content = temp.startsWith(":") ? temp.substring(1) : temp; //Strip the potential ':' at beginning of content
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
		 *     
		 * So we find the begining of the emotes section and the end of the section.
		 * Then, check that the message actually contains an emotes section and that
		 * the emote section actually contains data.
		 */
		int begin = tag.indexOf( EMOTES_IDENTIFIER );
		int end =   tag.indexOf(';', begin );	
		if( begin == -1 || begin + EMOTES_IDENTIFIER.length() == end){
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
				finalizeEmote(emote, emoteID, beginIndex, str.toString() );
				emote = new EmoteImpl();
				str.setLength(0);
				break;
			default:
				str.append(c);
			}
		}
		//The emotes segment ended, so we finish the current emote's begin-end pair, then we're done
		finalizeEmote(emote, emoteID, beginIndex, str.toString());
	}
	
	private void finalizeEmote(EmoteImpl emote, String emoteID, String beginIndex, String endIndex) {
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;	//The end index we receive from Twitch is inclusive, but Java is almost always exclusive
		emote.addIndices(begin, end);	
		
		emote.setEmoteID( Integer.parseInt( emoteID ) );
		emote.setPattern( content.substring(begin, end) );
		emotes.add(emote);		
	}

	private void addIndices(EmoteImpl emote, String beginIndex, String endIndex){
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;
		emote.addIndices(begin, end);	
	}
}
