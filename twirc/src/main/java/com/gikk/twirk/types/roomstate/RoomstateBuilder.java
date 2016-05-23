package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface RoomstateBuilder {
	public Roomstate build(TwitchMessage message);
}
