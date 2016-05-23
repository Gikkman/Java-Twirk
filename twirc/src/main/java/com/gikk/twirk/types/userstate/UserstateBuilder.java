package com.gikk.twirk.types.userstate;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface UserstateBuilder {
	public Userstate build(TwitchMessage message);
}
