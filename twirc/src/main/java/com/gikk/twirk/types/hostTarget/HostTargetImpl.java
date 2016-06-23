package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.enums.HOSTTARGET_MODE;

class HostTargetImpl implements HostTarget{
	
	final HOSTTARGET_MODE mode;
	final String target;
	final int viwerAmount;
	private final String rawLine;
	
	HostTargetImpl(GikkDefault_HostTargetBuilder builder){
		this.mode = builder.mode;
		this.target = builder.target;
		this.viwerAmount = builder.viwerAmount;
		this.rawLine = builder.rawLine;
	}

	@Override
	public HOSTTARGET_MODE getMode() {
		return mode;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public int getViewerCount() {
		return viwerAmount;
	}

	@Override
	public String getRaw() {
		return rawLine;
	}
}
