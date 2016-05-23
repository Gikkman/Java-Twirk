package com.gikk.twirk.types.subscriberEvent;

import static org.junit.Assert.assertTrue;

import com.gikk.twirk.types.SUB_EVENT;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilderDefault;

public class TestSubscriberEvent {
	private static final String SUB_NOTICE = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :Gikkman just subscribed!";
	private static final String RESUB_NOTICE = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :Slikkman subscribed for 12 months in a row!";
	private static final String RESUB_AWAY = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :13 viewers resubscribed while you were away!";

	private static final String SUB_HOST = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #target :Likkman just subscribed to target!";
	private static final String RESUB_HOST = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #target :Trikkman subscribed to target for 2 months in a row!";
	
	public static void testSubEvent(){
		testEvent(SUB_NOTICE, SUB_EVENT.NEW_SUB, "Gikkman", -1, -1);
		testEvent(RESUB_NOTICE, SUB_EVENT.RESUB, "Slikkman", 12, -1);
		testEvent(RESUB_AWAY, SUB_EVENT.RESUB_AWAY, "", -1, 13);
		testEvent(SUB_HOST, SUB_EVENT.HOST_NEW_SUB, "Likkman", -1, -1);
		testEvent(RESUB_HOST, SUB_EVENT.HOST_RESUB, "Trikkman", 2, -1);		
	}
	
	private static void testEvent(String EVENT, SUB_EVENT EVENT_TYPE, String subscriber, int months, int resubAmount ){
		TwitchMessage message = new TwitchMessageBuilderDefault().build(EVENT);
		SubscriberEvent subEvent = new SubscriberEventBuilderDefault().build(message);
		
		assertTrue( subEvent.getEventType() == EVENT_TYPE);
		assertTrue( subEvent.getSubscriber().matches( subscriber ) );
		assertTrue( subEvent.getMonths() == months );
		assertTrue( subEvent.getResubAmount() == resubAmount );
	}
	
	
}
