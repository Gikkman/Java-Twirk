package com.gikk.twirk.types.usernotice;

import java.util.List;

import com.gikk.twirk.types.AbstractTwitchUserFields;
import com.gikk.twirk.types._IDENTIFIERS;
import com.gikk.twirk.types._TagReader;
import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_UsernoticeBuilder extends AbstractTwitchUserFields implements UsernoticeBuilder{
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
		
		String tag = message.getTag();
		this.hasEmotes 	= message.hasEmotes();
		this.emotes 	= message.getEmotes();
		this.subMessage = message.getContent();
		
		_TagReader r = new _TagReader(tag);
		this.loginName	   = r.getAsString(_IDENTIFIERS.LOGIN_NAME);
		this.messageID     = r.getAsString(_IDENTIFIERS.MESSAGE_ID);
		this.systemMessage = r.getAsString(_IDENTIFIERS.SYSTEM_MESSAGE);
		this.months		   = r.getAsInt(_IDENTIFIERS.MONTHS);
		
		return new UsernoticeImpl(this);
	}	
}
