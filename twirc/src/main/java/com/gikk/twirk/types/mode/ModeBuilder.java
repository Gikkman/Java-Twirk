package com.gikk.twirk.types.mode;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface ModeBuilder {

	public Mode build(TwitchMessage message);
}
