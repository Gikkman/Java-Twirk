package com.gikk.twirk.types.twitchUser;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.userstate.UserstateBuilder;

public interface TwitchUserBuilder {
	public TwitchUser build(TwitchMessage message, UserstateBuilder userstateBuilder);
}
