package com.gikk.twirk.types;

import java.util.LinkedList;
import java.util.List;

/**An encapsulation of a message sent via Twitch chat. <br><br>
 * 
 * A message can have many different structures, but since they are based on the 
 * IRC protocol, they will have 1 to 5 different segments, each segment separated
 * with a space (the last segment, the actual message, may contain any amount of spaces).
 * <ul>
 * <li>Tag - This segment is not always present. It is Twitch's own specialized segment which contains data relevant
 * to the message in questing. Example of content is UserColor, Emotes and such. The order which elements appear in a Tag
 * is not guaranteed. A Tag always begins with a @
 * <li>Prefix - This segment usually contains knowledge about the sender of the message.
 * <li>Command - This segment usually specifies what command the message issued
 * <li>Target - This segment tells about whom the message is targeted at (a person, a channel)
 * <li>Content - This segment contains the actual text, intended for the receiver to read. 
 * </ul>
 * In some cases, the message will contain less segments than 5. In that case that a segments
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
	/**Parses a Chat line into a {@link #TwirkMessage}. The constructor will automatically detect the
	 * different segments, and place data relevant to the different segments in the correct variables.
	 * <br><br>
	 * If the message contains emotes, those will also be parsed, and the {@link #hasEmotes()} method will return true.
	 * 
	 * @param line The chat line which should be parsed into a TwirkMessage
	 */
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
	
	/**Retrieves this message's tag segment. It always starts with a @. Note that the 
	 * Tag segment will look vastly different for different Twitch commands, and some won't
	 * even have a tag.
	 * 
	 * @return The tag segment, or {@code null} if no tag was present
	 */
	public String getTag(){
		return tag;
	}

	/**Retrieves the message's prefix.<br><br>
	 * 
	 * A typical Twitch message looks something line this:<br>
	 * <code>:name!name@name.tmi.twitch.tv COMMAND #channel :message</code> <br><br>
	 * 
	 * The prefix is the part before the first space. It usually only contains data about the sender of 
	 * the message, but it might sometimes contain other information (PING messages for example just have PING as 
	 * their prefix).
	 * 
	 * @return The messages prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**Retrieves the message's command.<br>
	 * The command tells us what action triggered this message being sent.<br><br>
	 * 
	 * @return The message's command
	 */
	public String getCommand() {
		return command;
	}

	/**Retrieves the message's target.<br>
	 * The target tells us whom the message is intended towards. It can be a user, a channel, or something else.<br><br>
	 * 
	 * This segment is not always present.
	 * 
	 * @return The message's target, or {@code null} if no target
	 */
	public String getTarget() {
		return target;
	}

	/**Retrieves the message's content.<br>
	 * The content is the commands parameters. Most often, this is the actual chat message, but it may be many
	 * other things for other commands.<br><br>
	 * 
	 * This segment is not always present.
	 * 
	 * @return The message's content, or {@code null} if no content
	 */
	public String getContent() {
		return content;
	}
	
	/**Tells us if this message contained emotes or not. Only PRIVMSG and WHISPER can contain emotes.
	 * 
	 * @return {@code true} if the message contained emotes, <code>false</code> otherwise
	 */
	public boolean hasEmotes(){
		return hasEmotes;
	}
	
	/**Fetches a list of all all emotes in this message. Each emote is encapsulated in a {@link TwirkEmote}.
	 * The list might be empty, if the message contained no emotes.
	 * 
	 * @return List of emotes (might be empty)
	 */
	public final List<TwirkEmote> getEmotes(){
		return emotes;
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
		
		parseEmotes(tag, content);
		this.line = prefix + " " + command + " " + target + " " + content;
	}

	private void parseWithoutTag(String line){
		StringBuilder build = new StringBuilder();
		char c; int i = 1; //We start at 1 to remove the ':' at the beginning of the prefix
		
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
		
		//The target is everything up till the ':'
		do
			build.append(c);
		while( i < line.length() && (c = line.charAt(i++)) != ':' && c != '+' && c != '-');
		this.target = build.toString().trim();
		build.setLength(0);

		do
			build.append( c );
		while( i < line.length() && (c = line.charAt(i++)) != ':');
		String tempContent = build.toString().trim();
		this.content = tempContent.startsWith(":") ? tempContent.substring(1) : tempContent;	//We don't want the potential ':' in our content
			
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
