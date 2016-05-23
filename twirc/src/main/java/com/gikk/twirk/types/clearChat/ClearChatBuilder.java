package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface ClearChatBuilder {	
	public ClearChat build(TwitchMessage twitchMessage);
}
