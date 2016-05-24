package com.gikk.twirk.types.notice;

public class TestNotice {
	private final static String SUB_MODE_ON_MESSAGE = "@msg-id=subs_on :tmi.twitch.tv NOTICE #gikkman :This room is now in subscribers-only mode.";
	private final static String SUB_MODE_OFF_MESSAGE = "@msg-id=subs_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in subscribers-only mode.";
	private final static String R9K_MODE_ON_MESSAGE = "@msg-id=r9k_on :tmi.twitch.tv NOTICE #gikkman :This room is now in r9k mode.";
	private final static String R9K_MODE_OFF_MESSAGE = "@msg-id=r9k_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in r9k mode.";
	private final static String SLOW_MODE_120_MESSAGE = "@msg-id=slow_on :tmi.twitch.tv NOTICE #gikkman :This room is now in slow mode. You may send messages every 120 seconds.";
	private final static String SLOW_MODE_OFF_MESSAGE = "@msg-id=slow_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in slow mode.";
	private final static String HOST_MODE_ON = "@msg-id=host_on :tmi.twitch.tv NOTICE #gikkman :Now hosting GikkBot.";
	private final static String HOST_MODE_OFF = "@msg-id=host_off :tmi.twitch.tv NOTICE #gikkman :Exited host mode.";
	
	public static void test(){
		
	}
}
