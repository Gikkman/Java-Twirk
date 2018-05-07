package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.enums.CLEARCHAT_MODE;
import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

class DefaultClearChatBuilder implements ClearChatBuilder{
	CLEARCHAT_MODE mode;
	String target = "";
	int duration = -1;
	String reason = "";
	String rawLine;

	@Override
	public ClearChat build(TwitchMessage twitchMessage) {
		this.rawLine = twitchMessage.getRaw();

		if( twitchMessage.getContent().isEmpty() ){
			this.mode = CLEARCHAT_MODE.COMPLETE;
			this.target = "";
		}
		else{
			this.mode = CLEARCHAT_MODE.USER;
			this.target = twitchMessage.getContent();

			TagMap r = twitchMessage.getTagMap();
			this.duration = r.getAsInt(TwitchTags.BAN_DURATION);
			this.reason = r.getAsString(TwitchTags.BAN_REASON);
		}

		return new ClearChatImpl(this);
	}
}
