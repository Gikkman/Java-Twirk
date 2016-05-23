package com.gikk.twirk.types;

public enum EMOTE_SIZE{
	SMALL("/1.0"),
	MEDIUM("/2.0"),
	LARGE("/3.0");		
	public final String value;
	private EMOTE_SIZE(String val){ this.value = val; }
}
