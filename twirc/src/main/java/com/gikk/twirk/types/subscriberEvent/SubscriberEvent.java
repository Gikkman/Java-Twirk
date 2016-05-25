package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.SUB_EVENT;

public interface SubscriberEvent extends AbstractType{
	
	public String getSubscriber();
	public SUB_EVENT getEventType();
	public int getValue();
}
