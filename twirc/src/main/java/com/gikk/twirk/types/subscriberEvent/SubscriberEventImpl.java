package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.SUB_EVENT;

class SubscriberEventImpl implements SubscriberEvent{
	private final String subscriber;
	private final int value;
	private final SUB_EVENT type;
	
	public SubscriberEventImpl(SubscriberEventBuilderDefault builder) {
		this.subscriber = builder.subscriber;
		this.value = builder.value;
		this.type = builder.type;
	}
	
	@Override
	public String getSubscriber() {
		return subscriber;
	}

	@Override
	public SUB_EVENT getEventType() {
		return type;
	}

	@Override
	public int getValue() {
		return value;
	}

	
	
}
