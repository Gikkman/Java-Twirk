package com.gikk.twirk.types.userstate;

import com.gikk.twirk.types.USER_TYPE;

public interface Userstate {
	public int getColor();
	public String getDisplayName();
	public int getUserID();
	public boolean isMod();
	public boolean isSub();
	public boolean isTurbo();
	public String[] getBadges();
	public USER_TYPE getUserType();
	public int[] getEmoteSets();

}
