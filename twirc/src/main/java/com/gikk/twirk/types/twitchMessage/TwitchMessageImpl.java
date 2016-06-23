package com.gikk.twirk.types.twitchMessage;

import java.util.LinkedList;
import java.util.List;

import com.gikk.twirk.types.emote.Emote;

class TwitchMessageImpl implements TwitchMessage {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private final String line, tag, prefix, command, target, content;	
	private final LinkedList<Emote> emotes;
	
	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************	
	TwitchMessageImpl(GikkDefault_TwitchMessageBuilder builder){
		this.line 	= builder.line;
		this.tag 	= builder.tag;
		this.prefix = builder.prefix;
		this.command= builder.command;
		this.target = builder.target;
		this.content= builder.content;
		this.emotes = builder.emotes;
	}	
	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	public String getRaw(){
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
	
	public boolean hasEmotes(){
		return emotes.size() != 0;
	}

	public List<Emote> getEmotes(){
		return emotes;
	}
	
	public String toString(){
		return line;
	}
	
}
