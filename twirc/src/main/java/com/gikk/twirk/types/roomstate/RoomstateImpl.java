package com.gikk.twirk.types.roomstate;

class RoomstateImpl implements Roomstate {
	private static final String LANGUAGE_IDENTIFIER = "broadcaster-lang=";
	private static final String R9K_IDENTIFIER = "r9k=";
	private static final String SUBS_IDENTIFIER = "subs-only=";
	private static final String SLOW_IDENTIFIER = "slow=";
	
	private final String broadcasterLanguage;
	private final  int r9kMode;
	private final  int subMode;
	private final  int slowModeTimer;
	private final String rawLine;
	
	RoomstateImpl(GikkDefault_RoomstateBuilder builder){
		this.broadcasterLanguage = builder.broadcasterLanguage;
		this.r9kMode = builder.r9kMode;
		this.subMode = builder.subMode;
		this.slowModeTimer = builder.slowModeTimer;
		this.rawLine = builder.rawLine;
	}

	@Override
	public String getBroadcasterLanguage() {
		return broadcasterLanguage;
	}

	@Override
	public int get9kMode() {
		return r9kMode;
	}

	@Override
	public int getSubMode() {
		return subMode;
	}

	@Override
	public int getSlowModeTimer() {
		return slowModeTimer;
	}
	
	public String toString(){
		return LANGUAGE_IDENTIFIER + broadcasterLanguage 
			   + " " +
			   (r9kMode == 0 ? "" : (R9K_IDENTIFIER + r9kMode))
			   + " " +
			   (slowModeTimer == 0 ? "" : (SLOW_IDENTIFIER + slowModeTimer))
			   + " " +
			   (subMode == 0 ? "" : (SUBS_IDENTIFIER + subMode));
	}

	@Override
	public String getRaw() {
		return rawLine;
	}
}
