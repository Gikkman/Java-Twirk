package com.gikk.twirk.types.twitchUser;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.USER_TYPE;
import com.gikk.twirk.types.userstate.Userstate;

/**Class for representing a Twitch User's attributes<br><br>
 * 
 * Whenever we receive a PRIVMSG or WHISPER from Twitch (see {@link TwirkListener}, Twitch always sends us some 
 * information about the sender, such as what color the user has in chat on Twitch, how the users name should
 * be capitalized, if the user has Turbo, and so on. This class encapsulates all that info, and makes it easy 
 * to work with.
 * 
 * @author Gikkman
 *
 */
public class TwitchUserImpl implements TwitchUser{
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private Userstate userState = null;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	
	public TwitchUserImpl(TwitchUserBuilderDefault builder) {
		this.userState = builder.userstate;
	}

	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	public String getName(){
		return userState.getDisplayName();
	}
	
	public boolean isMod(){
		return userState.isMod();
	}
	
	public boolean isTurbo(){
		return userState.isTurbo();
	}
	
	public boolean isSub(){
		return userState.isSub();
	}
	
	public USER_TYPE getUserType(){
		return userState.getUserType();
	}
	
	public int getColor(){
		return userState.getColor();
	}
	
	public String[] getBadges(){
		return userState.getBadges();
	}
	public int getUserID(){
		return userState.getUserID();
	}
}
