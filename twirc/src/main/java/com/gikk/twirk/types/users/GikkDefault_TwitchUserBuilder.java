package com.gikk.twirk.types.users;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_TwitchUserBuilder extends AbstractUserBuilder implements TwitchUserBuilder {
	
	@Override
	public TwitchUser build(TwitchMessage message) {	
		parseUserProperties(message);		
		return new TwitchUserImpl( this );
	}		
}
