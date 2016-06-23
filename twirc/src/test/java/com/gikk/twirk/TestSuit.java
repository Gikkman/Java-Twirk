package com.gikk.twirk;

import org.junit.Test;

import com.gikk.twirk.types.clearChat.TestClearChat;
import com.gikk.twirk.types.hostTarget.TestHostTarget;
import com.gikk.twirk.types.mode.TestMode;
import com.gikk.twirk.types.notice.TestNotice;
import com.gikk.twirk.types.roomstate.TestRoomstate;
import com.gikk.twirk.types.subscriberEvent.TestSubscriberEvent;
import com.gikk.twirk.types.twitchMessage.TestMessage;
import com.gikk.twirk.types.usernotice.TestUsernotice;
import com.gikk.twirk.types.users.TestUserstate;

public class TestSuit {
	
	@Test
	public void testPrivMsg(){		
		//This one tests both TwitchMessage & TwitchUser
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
	
	@Test
	public void testNotice(){
		TestNotice.test();
	}
	
	@Test
	public void testHost(){
		TestHostTarget.test();
	}
	
	@Test
	public void testMode(){
		TestMode.test();
	}
	
	@Test
	public void testRoomstate(){
		TestRoomstate.test();
	}
	
	@Test
	public void testUserstate(){
		TestUserstate.test();
	}
	
	@Test
	public void testUsernotice(){
		TestUsernotice.test();
	}
}
