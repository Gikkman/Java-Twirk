package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types._IDENTIFIERS;
import com.gikk.twirk.types._TagReader;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_RoomstateBuilder implements RoomstateBuilder{
	
	String broadcasterLanguage;
	int r9kMode;
	int subMode;
	int slowModeTimer;
	String rawLine;
	
	public Roomstate build(TwitchMessage message) {
		this.rawLine = message.getRaw();
		_TagReader r = new _TagReader(message.getTag());
		
		broadcasterLanguage = r.getAsString(_IDENTIFIERS.ROOM_LANG);
		r9kMode = r.getAsInt(_IDENTIFIERS.R9K_ROOM);
		slowModeTimer = r.getAsInt(_IDENTIFIERS.SLOW_DURATION);
		subMode = r.getAsInt(_IDENTIFIERS.SUB_ONLY_ROOM);
		
		return new RoomstateImpl(this);
	}	
}
