package com.gikk.twirk.types.twitchUser;

import com.gikk.twirk.types.TWIRK_UTIL;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.userstate.Userstate;
import com.gikk.twirk.types.userstate.UserstateBuilder;

public class TwitchUserBuilderDefault implements TwitchUserBuilder {
	private static final String BADGE_IDENTIFIER 	= "badges=";
	private static final String	USER_ID_IDENTIFIER  = "user-id=";
	
	Userstate userstate;
	String[] badges;
	int userID;
	
	@Override
	public TwitchUser build(TwitchMessage message, UserstateBuilder userstateBuilder) {	
		this.userstate = userstateBuilder.build(message);
		
		String temp = TWIRK_UTIL.parseFeature(BADGE_IDENTIFIER, message.getTag());
		this.badges = temp.isEmpty() ? new String[0] : temp.split(",");
		
		temp = TWIRK_UTIL.parseFeature(USER_ID_IDENTIFIER, message.getTag());
		this.userID = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
		return new TwitchUserImpl( this );
	}		
}
