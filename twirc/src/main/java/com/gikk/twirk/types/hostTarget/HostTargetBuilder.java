package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface HostTargetBuilder {
	public HostTarget build(TwitchMessage message);
}
