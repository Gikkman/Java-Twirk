package com.gikk.twirk.types.notice;



import com.gikk.twirk.enums.NOTICE_EVENT;
import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class DefaultNoticeBuilder implements NoticeBuilder {
	NOTICE_EVENT event;
	String message;
	String rawLine;
	String rawEvent;

	@Override
	public Notice build(TwitchMessage message) {
		TagMap r = message.getTagMap();
		this.rawEvent = r.getAsString(TwitchTags.MESSAGE_ID);
		this.event = NOTICE_EVENT.of(rawEvent);
		this.message = message.getContent();
		this.rawLine = message.getRaw();

		return new NoticeImpl(this);
	}
}
