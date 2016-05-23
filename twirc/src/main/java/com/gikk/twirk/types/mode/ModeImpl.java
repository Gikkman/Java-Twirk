package com.gikk.twirk.types.mode;

class ModeImpl implements Mode{
	private final String user;
	private final MODE_EVENT  event;
	
	ModeImpl( ModeBuilderDefault builder ){
		this.event = builder.event;
		this.user =  builder.user;
	}

	@Override
	public MODE_EVENT getEvent() {
		return event;
	}

	@Override
	public String getUser() {
		return user;
	}
}
