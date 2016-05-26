package com.gikk.twirk.types.mode;

class ModeImpl implements Mode{
	private final String user;
	private final MODE_EVENT  event;
	private final String rawLine;
	
	ModeImpl( GikkDefault_ModeBuilder builder ){
		this.event = builder.event;
		this.user =  builder.user;
		this.rawLine = builder.rawLine;
	}

	@Override
	public MODE_EVENT getEvent() {
		return event;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getRaw() {
		return rawLine;
	}
}
