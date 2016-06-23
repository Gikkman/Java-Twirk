package com.gikk.twirk.types.notice;

import com.gikk.twirk.enums.NOTICE_EVENT;

class NoticeImpl implements Notice {
	private final NOTICE_EVENT event;
	private final String message;
	private final String rawLine;
	private final String rawEvent;
	
	NoticeImpl(GikkDefault_NoticeBuilder builder) {
		this.event = builder.event;
		this.message = builder.message;
		this.rawLine = builder.rawLine;
		this.rawEvent = builder.rawEvent;
	}

	@Override
	public NOTICE_EVENT getEvent() {
		return event;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getRaw() {
		return rawLine;
	}

	@Override
	public String getRawNoticeID() {
		return rawEvent;
	}
	
	
}
