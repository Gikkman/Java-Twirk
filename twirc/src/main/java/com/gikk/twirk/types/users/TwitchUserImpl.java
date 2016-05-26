package com.gikk.twirk.types.users;

import com.gikk.twirk.types.USER_TYPE;


public class TwitchUserImpl implements TwitchUser{
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private final String displayName;
	private final boolean isMod;
	private final boolean isSub;
	private final boolean isTurbo;
	private final int color;
	private final int userID;
	private final USER_TYPE userType;
	private final String[] badges;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	
	public TwitchUserImpl(TwitchUserBuilderDefault builder) {
		this.displayName = builder.displayName;
		this.isMod 		 = builder.isMod;
		this.isSub 		 = builder.isSub;
		this.isTurbo 	 = builder.isTurbo;
		this.badges 	 = builder.badges;
		this.userID 	 = builder.userID;
		this.userType 	 = builder.userType;
		this.color 	 	 = builder.color;
	}

	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	public String getName(){
		return displayName;
	}
	
	public boolean isMod(){
		return isMod;
	}
	
	public boolean isTurbo(){
		return isTurbo;
	}
	
	public boolean isSub(){
		return isSub;
	}
	
	public USER_TYPE getUserType(){
		return userType;
	}
	
	public int getColor(){
		return color;
	}
	
	public String[] getBadges(){
		return badges;
	}
	public int getUserID(){
		return userID;
	}
}
