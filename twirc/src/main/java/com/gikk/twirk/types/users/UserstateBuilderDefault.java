package com.gikk.twirk.types.users;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class UserstateBuilderDefault extends AbstractUserBuilder implements UserstateBuilder {
	
	@Override
	public Userstate build(TwitchMessage message) {
		parseUserProperties(message);
		return new UserstateImpl(this);
	}
}
