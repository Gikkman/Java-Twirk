package com.gikk.twirc;

/**Class for creating instances of Twirk.<br>
 * To build an instance of Twirk, the user has to supply the bot's nick and 
 * oAuth token. To generate a oAuth token, visit <a href="https://twitchapps.com/tmi/">https://twitchapps.com/tmi/</a><br><br>
 * 
 * If you want to change any setting except the required once, use one of the 
 * setter methods related to this object. When all settings are performed, use the
 * {@link #build()} method.
 * 
 * @author Gikkman
 *
 */
public class TwirkBuilder {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	boolean verboseMode = false;

	String server = "irc.chat.twitch.tv";
	int 	port  = 6667;
	
	String nick = "";
	String oauth = "";
	String channel = "";
	
	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	/**Creates a new TwirkBuilder. To build an instance of Twirk, we must have
	 * the bot's account nick, and the bot's IRC oAuth token (which can be retrieved from Twitch).
	 * See {@link TwirkBuilder}
	 * 
	 * @param channel The channel the bot should join
	 * @param nick The bot's account name (on Twitch)
	 * @param oauth The bot's IRC oAuth token (on Twitch)
	 */
	public TwirkBuilder(String channel, String nick, String oauth){
		this.channel = channel;
		this.nick = nick;
		this.oauth = oauth;
	}
	
	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	/**Sets the server which Twirk will try to connect to Twitch via
	 * 
	 * @param server The server adress
	 * @return 
	 */
	public TwirkBuilder setServer(String server){
		this.server = server;
		return this;
	}
	
	/**Sets the port which the Twirk object will try to connect to Twitch via
	 * 
	 * @param port The port number
	 * @return 
	 */
	public TwirkBuilder setPort(int port){
		this.port = port;
		return this;
	}
	
	/**Sets the Twirk object to VerboseMode<br>
	 * In VerboseMode, every message that is received by Twirk will be printed to console. Default value is {@code false}
	 * 
	 * @param verboseMode {@code true} is you want Twirk in VerboseMode
	 * @return 
	 */
	public TwirkBuilder setVerboseMode(boolean verboseMode){
		this.verboseMode = verboseMode;
		return this;
	}
	
	/**Creates a Twirk object, with the parameters assigned to this
	 * builder.
	 * 
	 * @return A configured Twirk object
	 */
	public Twirk build(){
		return new Twirk(this);
	}
	
}
