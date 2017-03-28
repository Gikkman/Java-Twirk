package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.emote.Emote;
import java.util.List;

class UsernoticeImpl implements Usernotice{
	private final String rawLine;
	private final String displayName;
	private final boolean isMod;
	private final boolean isSub;
	private final boolean isTurbo;
	private final int color;
	private final long userID;
	private final USER_TYPE userType;
	private final String[] badges;
	private final String loginName;
	private final List<Emote> emotes;
	private final boolean hasEmotes;
	private final int months;
	private final String subMessage;
	private final String systemMessage;
	
	public UsernoticeImpl(GikkDefault_UsernoticeBuilder builder){
		this.rawLine 		= builder.rawLine;
		this.displayName 	= builder.displayName;
		this.isSub 			= builder.isSub;
		this.isMod 			= builder.isMod;
		this.isTurbo 		= builder.isTurbo;
		this.color 			= builder.color;
		this.userID 		= builder.userID;
		this.userType 		= builder.userType;
		this.badges 		= builder.badges;
		this.loginName 		= builder.loginName;
		this.emotes 		= builder.emotes;
		this.hasEmotes 		= builder.hasEmotes;
		this.months 		= builder.months;
		this.subMessage 	= builder.subMessage;
		this.systemMessage 	= builder.systemMessage;
	}

	@Override
	public String getRaw() {
		return rawLine;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public boolean isMod() {
		return isMod;
	}

	@Override
	public boolean isTurbo() {
		return isTurbo;
	}

	@Override
	public boolean isSub() {
		return isSub;
	}

	@Override
	public USER_TYPE getUserType() {
		return userType;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public String[] getBadges() {
		return badges;
	}

	@Override
	public long getUserID() {
		return userID;
	}

	@Override
	public String getMessage() {
		return subMessage;
	}

	@Override
	public int getMonths() {
		return months;
	}

	@Override
	public String getSystemMessage() {
		return systemMessage;
	}

	@Override
	public boolean hasEmotes() {
		return hasEmotes;
	}

	@Override
	public List<Emote> getEmotes() {
		return emotes;
	}

	@Override
	public String getLogin() {
		return loginName;
	}
}
