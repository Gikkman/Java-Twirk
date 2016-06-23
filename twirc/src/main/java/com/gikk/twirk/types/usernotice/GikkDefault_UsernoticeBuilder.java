package com.gikk.twirk.types.usernotice;

import java.util.List;

import com.gikk.twirk.types.AbstractTwitchUserFields;
import com.gikk.twirk.types._PARSING_UTIL;
import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_UsernoticeBuilder extends AbstractTwitchUserFields implements UsernoticeBuilder{
	private final static String LOGIN_IDENTIFIER = "login=";
	private final static String MESSAGE_ID_IDENTIFIER = "msg-id=";
	private final static String SYSTEM_MESSAGE_IDENTIFIER = "system-msg=";
	private final static String MONTHS_IDENTIFIER = "msg-param-months=";
	
	
	String loginName;
	List<Emote> emotes;
	boolean hasEmotes;
	int months;
	String subMessage;
	String messageID;
	String systemMessage;
	
	@Override
	public Usernotice build(TwitchMessage message) {
		parseUserProperties(message);
		
		String tag = message.getTag(), temp = "";
		this.hasEmotes 	= message.hasEmotes();
		this.emotes 	= message.getEmotes();
		this.subMessage = message.getContent();
		this.loginName	   = _PARSING_UTIL.parseString(LOGIN_IDENTIFIER, tag);
		this.messageID     = _PARSING_UTIL.parseString(MESSAGE_ID_IDENTIFIER, tag);
		this.systemMessage = _PARSING_UTIL.parseString(SYSTEM_MESSAGE_IDENTIFIER, tag).replace("\\s", " ");
		
		temp = _PARSING_UTIL.parseString(MONTHS_IDENTIFIER, tag);
		this.months = temp.isEmpty() ? 0 : Integer.parseInt(temp);
		
		return new UsernoticeImpl(this);
	}	
}
