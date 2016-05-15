package com.gikk.twirk.types;

/**Class for IrcUsers. Easier than sending a bunch of strings everywhere.
 * 
 * @author Gikkman
 *
 */
public class TwirkUser {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************	
	private final String name;
	private TwirkUserstate userState = null;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	
	public TwirkUser(TwirkMessage message) {
		this.userState = new TwirkUserstate( message );
		this.name = userState.displayName;
	}

	//***********************************************************
	// 				PUBLIC
	//***********************************************************		
	public String getUsername(){
		return name.toLowerCase();
	}
	
	public TwirkUserstate getUserstate(){
		return userState;
	}
	
	public void setUserstate( TwirkUserstate userstate ){
		this.userState = userstate;
	}
}
