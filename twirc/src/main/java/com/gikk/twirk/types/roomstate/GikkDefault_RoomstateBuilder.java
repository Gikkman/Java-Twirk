package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types._PARSING_UTIL;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_RoomstateBuilder implements RoomstateBuilder{
	private static final String LANGUAGE_IDENTIFIER = "broadcaster-lang=";
	private static final String R9K_IDENTIFIER = "r9k=";
	private static final String SUBS_IDENTIFIER = "subs-only=";
	private static final String SLOW_IDENTIFIER = "slow=";
	
	String broadcasterLanguage;
	int r9kMode;
	int subMode;
	int slowModeTimer;
	String rawLine;
	
	public Roomstate build(TwitchMessage message) {
		this.rawLine = message.getRaw();
		String tag = message.getTag();
		
		String temp = _PARSING_UTIL.parseString(LANGUAGE_IDENTIFIER, tag);
		broadcasterLanguage = temp.isEmpty() ? "" : temp;
		
		temp = _PARSING_UTIL.parseString(R9K_IDENTIFIER, tag);
		r9kMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = _PARSING_UTIL.parseString(SLOW_IDENTIFIER, tag);
		slowModeTimer = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		temp = _PARSING_UTIL.parseString(SUBS_IDENTIFIER, tag);
		subMode = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		return new RoomstateImpl(this);
	}	
}
