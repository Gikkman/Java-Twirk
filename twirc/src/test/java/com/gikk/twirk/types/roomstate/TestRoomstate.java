package com.gikk.twirk.types.roomstate;

import static org.junit.Assert.assertTrue;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.GikkDefault_TwitchMessageBuilder;

public class TestRoomstate {
	private final static String JOIN = "@broadcaster-lang=;emote-only=0;r9k=0;slow=0;subs-only=0 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String SUB_MODE_ON_MESSAGE = "@subs-only=1 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String SUB_MODE_OFF_MESSAGE = "@subs-only=0 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String R9K_MODE_ON_MESSAGE = "@r9k=1 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String R9K_MODE_OFF_MESSAGE = "@r9k=0 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String SLOW_MODE_120_MESSAGE = "@slow=120 :tmi.twitch.tv ROOMSTATE #gikkman";
	private final static String SLOW_MODE_OFF_MESSAGE = "@slow=0 :tmi.twitch.tv ROOMSTATE #gikkman";
	
	public static void test(){
		doTest(JOIN, "", 0, 0, 0);
		doTest(SUB_MODE_ON_MESSAGE,  "", -1, 1, -1  );
		doTest(SUB_MODE_OFF_MESSAGE, "", -1, 0, -1 );
		doTest(R9K_MODE_ON_MESSAGE,  "", 1, -1, -1  );
		doTest(R9K_MODE_OFF_MESSAGE, "", 0, -1, -1 );
		doTest(SLOW_MODE_120_MESSAGE,"", -1, -1, 120);
		doTest(SLOW_MODE_OFF_MESSAGE,"", -1, -1, 0);
	}

	private static void doTest(String STRING, String lang, int r9k, int sub, int slow ) {
		TwitchMessage message = new GikkDefault_TwitchMessageBuilder().build(STRING);
		Roomstate room        = new GikkDefault_RoomstateBuilder().build(message);
		
		assertTrue("Got: " + room.getBroadcasterLanguage() + " Expected: " + lang, room.getBroadcasterLanguage().equals(lang) );
		assertTrue("Got: " + room.get9kMode() 			   + " Expected: " + r9k , room.get9kMode() == r9k);
		assertTrue("Got: " + room.getSubMode() 			   + " Expected: " + sub  ,room.getSubMode() == sub);
		assertTrue("Got: " + room.getSlowModeTimer() 	   + " Expected: " + slow ,room.getSlowModeTimer() == slow);
		assertTrue("Got: " + room.getRaw()		     	   + " Expected: " + STRING,room.getRaw().equals(STRING));
		
	}
}
