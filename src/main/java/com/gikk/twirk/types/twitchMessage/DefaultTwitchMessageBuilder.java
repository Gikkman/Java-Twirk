package com.gikk.twirk.types.twitchMessage;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.cheer.Cheer;
import com.gikk.twirk.types.cheer.CheerParser;
import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.emote.EmoteParser;
import java.util.List;

class DefaultTwitchMessageBuilder implements TwitchMessageBuilder{
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	String line, tag, prefix, command, target, content, id;
    int roomID;
	List<Emote> emotes;
    List<Cheer> cheers;
    TagMap tagMap;
    EmoteParser emoteParser = EmoteParser.getDefaultImplementation();

	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	@Override
	public TwitchMessage build(String chatLine) {
		if( chatLine.startsWith("@") ) {
            parseWithTag(chatLine);
        } else{
			parseWithoutTag(chatLine);
		}

		this.line = chatLine;
        this.tagMap = TagMap.getDefault(tag);
        this.id = tagMap.getAsString(TwitchTags.ID);
        this.roomID = tagMap.getAsInt(TwitchTags.ROOM_ID);
		this.emotes = emoteParser.parseEmotes(tagMap, content);
        this.cheers = CheerParser.parseCheer(tagMap, content);

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
	}

	private void parseWithoutTag(String line){
		tag = "";

		StringBuilder build = new StringBuilder();
		char c; int i = 0; //We start at 1 to remove the ':' at the beginning of the prefix

		//The prefix is everything up till the first space
		while( (c = line.charAt(i++)) != ' ' ) {
            build.append(c);
        }
		this.prefix = build.toString().trim();
		build.setLength(0);

		//The command is everything up till the second space
		do {
            build.append(c);
        } while( i < line.length() && (c = line.charAt(i++)) != ' ' );
		this.command = build.toString().trim();
		build.setLength(0);

		//The target is everything up till the ':', '+' or '-'
		do {
            build.append(c);
        } while( i < line.length() && (c = line.charAt(i++)) != ':' && c != '+' && c != '-');
		this.target = build.toString().trim();
		build.setLength(0);

		if( i == line.length() ){
			this.content = "";
			return;
		}

		//The content is everything else
		do {
            build.append( c );
        } while( i < line.length() && (c = line.charAt(i++)) != '\r' );
		String temp = build.toString().trim();
		this.content = temp.startsWith(":") ? temp.substring(1) : temp; //Strip the potential ':' at beginning of content
	}
}
