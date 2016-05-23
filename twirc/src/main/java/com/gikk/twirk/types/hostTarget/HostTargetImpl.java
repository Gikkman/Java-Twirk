package com.gikk.twirk.types.hostTarget;

class HostTargetImpl implements HostTarget{
	
	final HOSTTARGET_MODE mode;
	final String hoster;
	final int viwerAmount;
	
	HostTargetImpl(HostTargetBuilderDefault builder){
		this.mode = builder.mode;
		this.hoster = builder.hoster;
		this.viwerAmount = builder.viwerAmount;
	}

	@Override
	public HOSTTARGET_MODE getMode() {
		return mode;
	}

	@Override
	public String getHoster() {
		return hoster;
	}

	@Override
	public int getViewerCount() {
		return viwerAmount;
	}
}
