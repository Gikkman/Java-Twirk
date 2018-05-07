package com.gikk.twirk.types.twitchMessage;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.cheer.Cheer;
import com.gikk.twirk.types.emote.Emote;
import java.util.List;

class TwitchMessageImpl implements TwitchMessage {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private final String line, tag, prefix, command, target, content, id;
    private final int roomID;
	private final List<Emote> emotes;
    private final List<Cheer> cheers;
    private final TagMap tagMap;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	TwitchMessageImpl(DefaultTwitchMessageBuilder builder){
		this.line 	= builder.line;
		this.tag 	= builder.tag;
		this.prefix = builder.prefix;
		this.command= builder.command;
		this.target = builder.target;
		this.content= builder.content;
		this.emotes = builder.emotes;
        this.cheers = builder.cheers;
        this.tagMap = builder.tagMap;
        this.id     = builder.id;
        this.roomID = builder.roomID;
	}
	//***********************************************************
	// 				PUBLIC
	//***********************************************************
    @Override
	public String getRaw(){
		return line;
	}

    @Override
	public String getTag(){
		return tag;
	}

    @Override
	public String getPrefix() {
		return prefix;
	}

    @Override
	public String getCommand() {
		return command;
	}

    @Override
	public String getTarget() {
		return target;
	}

    @Override
	public String getContent() {
		return content;
	}

    @Override
	public boolean hasEmotes(){
		return !emotes.isEmpty();
	}

    @Override
	public List<Emote> getEmotes(){
		return emotes;
	}

    @Override
    public boolean isCheer(){
        return !cheers.isEmpty();
    }

    @Override
    public List<Cheer> getCheers(){
        return cheers;
    }

    @Override
	public String toString(){
		return line;
	}

    @Override
    public TagMap getTagMap() {
        return tagMap;
    }

    @Override
    public int getBits() {
        int bits = 0;
        for(Cheer c : getCheers()) {
            bits += c.getBits();
        }
        return bits;
    }

    @Override
    public long getSentTimestamp() {
        return tagMap.getAsLong(TwitchTags.TMI_SENT_TS);
    }

    @Override
    public long getRoomID() {
        return roomID;
    }

    @Override
    public String getMessageID() {
        return id;
    }
}
