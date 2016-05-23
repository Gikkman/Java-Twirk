package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types.TWIRK_UTIL;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class RoomstateBuilderDefault implements RoomstateBuilder{
	private static final String LANGUAGE_IDENTIFIER = "broadcaster-lang=";
	private static final String R9K_IDENTIFIER = "r9k=";
	private static final String SUBS_IDENTIFIER = "subs-only=";
	private static final String SLOW_IDENTIFIER = "slow=";
	
	String broadcasterLanguage;
	int r9kMode;
	int subMode;
	int slowModeTimer;
	
	public Roomstate build(TwitchMessage message) {
		String tag = message.getTag();
		
		String temp = TWIRK_UTIL.parseFeature(LANGUAGE_IDENTIFIER, tag);
		broadcasterLanguage = temp.isEmpty() ? null : temp;
		
		temp = TWIRK_UTIL.parseFeature(R9K_IDENTIFIER, tag);
		r9kMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = TWIRK_UTIL.parseFeature(SLOW_IDENTIFIER, tag);
		slowModeTimer = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = TWIRK_UTIL.parseFeature(SUBS_IDENTIFIER, tag);
		subMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		return new RoomstateImpl(this);
	}	
}
