package com.gikk.twirk.types.notice;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public interface NoticeBuilder {
	public Notice build(TwitchMessage message);
}
