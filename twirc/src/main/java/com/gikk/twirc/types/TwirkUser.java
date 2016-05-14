package com.gikk.twirc.types;

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
	// 				STATIC
	//***********************************************************
	/**Creates a new IrcUser from the IrcMessage, if possible. If the message is of
	 * type PRIVMSG, JOIN, PART, or WHISPER, it should be possible to create a user
	 * 
	 * @param message
	 * @return An IrcUser if possible, or <code>null</code> if not
	 */
	public static TwirkUser create(TwirkMessage message) {
		String prefix = message.getPrefix();
		int nickIdx = prefix.indexOf('!');
		int nameIdx = prefix.indexOf('@');
		
		if( nickIdx == -1 || nameIdx == -1)
			return null;
		
		String nick = prefix.substring( prefix.charAt(0) == ':' ? 1 : 0, nickIdx);
		
		if( message.getTag().startsWith("@"))
			return new TwirkUser(nick, new TwirkUserstate( message.getTag(), message.getPrefix() ) );
		else
			return new TwirkUser(nick);
	}

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************	
	private TwirkUser(String name){
		this.name = name;
	}
	
	public TwirkUser(String name, TwirkUserstate userState) {
		this.name = name;
		this.userState = userState;
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
