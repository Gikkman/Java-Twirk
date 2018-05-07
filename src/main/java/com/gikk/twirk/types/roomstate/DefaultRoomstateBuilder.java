package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

class DefaultRoomstateBuilder implements RoomstateBuilder{

	String broadcasterLanguage;
	int r9kMode;
	int subMode;
	int slowModeTimer;
	String rawLine;

    @Override
	public Roomstate build(TwitchMessage message) {
		this.rawLine = message.getRaw();
		TagMap r = message.getTagMap();

		broadcasterLanguage = r.getAsString(TwitchTags.ROOM_LANG);
		r9kMode = r.getAsInt(TwitchTags.R9K_ROOM);
		slowModeTimer = r.getAsInt(TwitchTags.SLOW_DURATION);
		subMode = r.getAsInt(TwitchTags.SUB_ONLY_ROOM);

		return new RoomstateImpl(this);
	}
}
