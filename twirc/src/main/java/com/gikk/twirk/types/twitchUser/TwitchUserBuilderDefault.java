package com.gikk.twirk.types.twitchUser;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.userstate.Userstate;
import com.gikk.twirk.types.userstate.UserstateBuilder;

public class TwitchUserBuilderDefault implements TwitchUserBuilder {

	Userstate userstate;
	
	@Override
	public TwitchUser build(TwitchMessage message, UserstateBuilder userstateBuilder) {	
		this.userstate = userstateBuilder.build(message);
		return new TwitchUserImpl( this );
	}		
}
