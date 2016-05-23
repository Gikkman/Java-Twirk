package com.gikk.twirk.types.notice;

class NoticeImpl implements Notice {
	private final NOTICE_EVENT event;
	private final String message;
	
	NoticeImpl(NoticeBuilderDefault builder) {
		this.event = builder.event;
		this.message = builder.message;
	}

	@Override
	public NOTICE_EVENT getEvent() {
		return event;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
