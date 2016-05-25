package com.gikk.twirk.types.userstate;

import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.USER_TYPE;

public interface Userstate extends AbstractType{
	public int getColor();
	public String getDisplayName();
	public boolean isMod();
	public boolean isSub();
	public boolean isTurbo();
	public USER_TYPE getUserType();
	public int[] getEmoteSets();

}
