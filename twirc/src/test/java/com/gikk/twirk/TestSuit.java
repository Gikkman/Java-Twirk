package com.gikk.twirk;

import org.junit.Test;

import com.gikk.twirk.types.clearChat.TestClearChat;
import com.gikk.twirk.types.subscriberEvent.TestSubscriberEvent;
import com.gikk.twirk.types.twitchMessage.TestMessage;

public class TestSuit {
	
	@Test
	public void testPrivMsg(){		
		TestMessage.testPrivMsg();
	}
	
	@Test
	public void testSubEvent(){
		TestSubscriberEvent.testSubEvent();
	}
	
	@Test
	public void testClearChat(){
		TestClearChat.test();
	}
}
