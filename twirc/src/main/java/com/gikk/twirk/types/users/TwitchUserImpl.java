package com.gikk.twirk.types.users;

import com.gikk.twirk.enums.USER_TYPE;


class TwitchUserImpl implements TwitchUser{
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
    private final String userName;
	private final String displayName;
	private final boolean isMod;
	private final boolean isSub;
	private final boolean isTurbo;
	private final int color;
	private final long userID;
	private final USER_TYPE userType;
	private final String[] badges;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	
	TwitchUserImpl(GikkDefault_TwitchUserBuilder builder) {
        this.userName    = builder.userName;
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

    @Override
    public String getUserName() {
        return userName;
    }
    
    @Override
	public String getDisplayName(){
		return displayName;
	}
	
    @Override
	public boolean isMod(){
		return isMod;
	}
	
    @Override
	public boolean isTurbo(){
		return isTurbo;
	}
	
    @Override
	public boolean isSub(){
		return isSub;
	}
	
    @Override
	public USER_TYPE getUserType(){
		return userType;
	}
	
    @Override
	public int getColor(){
		return color;
	}
	
    @Override
	public String[] getBadges(){
		return badges;
	}
    @Override
	public long getUserID(){
		return userID;
	}
}
