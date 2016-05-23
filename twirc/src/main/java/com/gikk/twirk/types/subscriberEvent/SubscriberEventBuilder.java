package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface SubscriberEventBuilder {
	public SubscriberEvent build(TwitchMessage message);
}	
