package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.SUB_EVENT;

class SubscriberEventImpl implements SubscriberEvent{
	private final String subscriber;
	private final int months;
	private final int resubAmount;
	private final SUB_EVENT type;
	
	public SubscriberEventImpl(SubscriberEventBuilderDefault builder) {
		this.subscriber = builder.subscriber;
		this.months = builder.months;
		this.type = builder.type;
		this.resubAmount = builder.resubAmount;
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
	public int getMonths() {
		return months;
	}

	@Override
	public int getResubAmount() {
		return resubAmount;
	}
	
}
