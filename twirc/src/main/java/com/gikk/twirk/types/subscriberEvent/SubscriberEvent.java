package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.SUB_EVENT;

public interface SubscriberEvent {
	
	public String getSubscriber();
	public SUB_EVENT getEventType();
	public int getMonths();
	public int getResubAmount();
}
