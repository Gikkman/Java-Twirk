package com.gikk.twirk.types.clearMsg;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

class DefaultClearMsgBuilder implements ClearMsgBuilder{
	String sender;
	String msgId;
	String msgContents;
	String rawLine;

	@Override
	public ClearMsg build(TwitchMessage twitchMessage) {
		this.rawLine = twitchMessage.getRaw();
	
		TagMap r = twitchMessage.getTagMap();
		
		this.msgContents = twitchMessage.getContent();
		this.msgId = r.getAsString("target-msg-id");
		this.sender = r.getAsString("login");

		return new ClearMsgImpl(this);
	}
}
