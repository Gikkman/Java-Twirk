package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.types.CLEARCHAT_MODE;

class ClearChatImpl implements ClearChat{

	public final CLEARCHAT_MODE mode;
	public final String target;
	private final String reason;
	private final int duration;
	
	ClearChatImpl(ClearChatBuilderDefault builder){
		this.mode = builder.mode;
		this.target = builder.target;
		this.reason = builder.reason;
		this.duration = builder.duration;
	}

	@Override
	public CLEARCHAT_MODE getMode() {
		return mode;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getReason() {
		return reason;
	}
}
