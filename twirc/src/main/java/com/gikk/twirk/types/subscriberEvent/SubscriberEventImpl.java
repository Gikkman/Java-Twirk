package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.enums.SUB_EVENT;

class SubscriberEventImpl implements SubscriberEvent{
	private final String subscriber;
	private final int value;
	private final SUB_EVENT type;
	private final String rawLine;
	
	public SubscriberEventImpl(GikkDefault_SubscriberEventBuilder builder) {
		this.subscriber = builder.subscriber;
		this.value = builder.value;
		this.type = builder.type;
		this.rawLine = builder.rawLine;
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

	@Override
	public String getRaw() {
		return rawLine;
	}

	
	
}
