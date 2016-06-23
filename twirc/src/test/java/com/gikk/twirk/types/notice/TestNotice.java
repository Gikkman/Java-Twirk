package com.gikk.twirk.types.notice;

import static org.junit.Assert.assertTrue;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.enums.NOTICE_EVENT;
import com.gikk.twirk.types.twitchMessage.GikkDefault_TwitchMessageBuilder;

public class TestNotice {
	private final static String SUB_MODE_ON_MESSAGE = "@msg-id=subs_on :tmi.twitch.tv NOTICE #gikkman :This room is now in subscribers-only mode.";
	private final static String SUB_MODE_OFF_MESSAGE = "@msg-id=subs_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in subscribers-only mode.";
	private final static String R9K_MODE_ON_MESSAGE = "@msg-id=r9k_on :tmi.twitch.tv NOTICE #gikkman :This room is now in r9k mode.";
	private final static String R9K_MODE_OFF_MESSAGE = "@msg-id=r9k_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in r9k mode.";
	private final static String SLOW_MODE_120_MESSAGE = "@msg-id=slow_on :tmi.twitch.tv NOTICE #gikkman :This room is now in slow mode. You may send messages every 120 seconds.";
	private final static String SLOW_MODE_OFF_MESSAGE = "@msg-id=slow_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in slow mode.";
	private final static String HOST_MODE_ON = "@msg-id=host_on :tmi.twitch.tv NOTICE #gikkman :Now hosting GikkBot.";
	private final static String HOST_MODE_OFF = "@msg-id=host_off :tmi.twitch.tv NOTICE #gikkman :Exited host mode.";
	private final static String UNRECOGNIZED = "@msg-id=unrecognized_cmd :tmi.twitch.tv NOTICE #gikkman :Unrecognized command: /do";
	
	public static void test(){
		doTest(SUB_MODE_ON_MESSAGE, NOTICE_EVENT.SUB_MODE_ON, "This room is now in subscribers-only mode.", "subs_on");
		doTest(SUB_MODE_OFF_MESSAGE, NOTICE_EVENT.SUB_MODE_OFF, "This room is no longer in subscribers-only mode.", "subs_off");
		doTest(R9K_MODE_ON_MESSAGE, NOTICE_EVENT.R9K_MODE_ON, "This room is now in r9k mode.", "r9k_on");
		doTest(R9K_MODE_OFF_MESSAGE, NOTICE_EVENT.R9K_MODE_OFF, "This room is no longer in r9k mode.", "r9k_off");
		doTest(SLOW_MODE_120_MESSAGE, NOTICE_EVENT.SLOW_MODE_ON, "This room is now in slow mode. You may send messages every 120 seconds.", "slow_on");
		doTest(SLOW_MODE_OFF_MESSAGE, NOTICE_EVENT.SLOW_MODE_OFF, "This room is no longer in slow mode.", "slow_off");
		doTest(HOST_MODE_ON, NOTICE_EVENT.HOST_MODE_ON, "Now hosting GikkBot.", "host_on");
		doTest(HOST_MODE_OFF, NOTICE_EVENT.HOST_MODE_OFF, "Exited host mode.", "host_off");
		doTest(UNRECOGNIZED, NOTICE_EVENT.OTHER, "Unrecognized command: /do", "unrecognized_cmd");
	}

	private static void doTest(String line, NOTICE_EVENT EVENT, String theMessage, String rawNoticeID) {
		TwitchMessage message = new GikkDefault_TwitchMessageBuilder().build(line);
		Notice notice = new GikkDefault_NoticeBuilder().build(message);
		
		assertTrue( notice.getEvent() == EVENT );
		assertTrue( notice.getMessage().equals(theMessage) );
		assertTrue( notice.getRawNoticeID().equals(rawNoticeID) );
		assertTrue( line.equals( notice.getRaw() ));
		
	}
}
