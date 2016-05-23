package com.gikk.twirk;

import com.gikk.twirk.types.clearChat.ClearChatBuilder;
import com.gikk.twirk.types.clearChat.ClearChatBuilderDefault;
import com.gikk.twirk.types.hostTarget.HostTargetBuilder;
import com.gikk.twirk.types.hostTarget.HostTargetBuilderDefault;
import com.gikk.twirk.types.mode.ModeBuilder;
import com.gikk.twirk.types.mode.ModeBuilderDefault;
import com.gikk.twirk.types.notice.NoticeBuilder;
import com.gikk.twirk.types.notice.NoticeBuilderDefault;
import com.gikk.twirk.types.roomstate.RoomstateBuilder;
import com.gikk.twirk.types.roomstate.RoomstateBuilderDefault;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilder;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilderDefault;
import com.gikk.twirk.types.twitchUser.TwitchUserBuilder;
import com.gikk.twirk.types.twitchUser.TwitchUserBuilderDefault;
import com.gikk.twirk.types.userstate.UserstateBuilder;
import com.gikk.twirk.types.userstate.UserstateBuilderDefault;

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
	int 	port  = 6697;
	
	String nick = "";
	String oauth = "";
	String channel = "";

	private ClearChatBuilder 	clearChatBuilder;    
	private HostTargetBuilder 	hostTargetBuilder;   
	private ModeBuilder 		modeBuilder;         
	private NoticeBuilder 		noticeBuilder;       
	private RoomstateBuilder 	roomstateBuilder;    
	private TwitchMessageBuilder twitchMessageBuilder;
	private TwitchUserBuilder 	twitchUserBuilder;   
	private UserstateBuilder 	userstateBuilder;    	
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
	
	public void setClearChatBuilder(ClearChatBuilder clearChatBuilder) {
		this.clearChatBuilder = clearChatBuilder;
	}

	public void setHostTargetBuilder(HostTargetBuilder hostTargetBuilder) {
		this.hostTargetBuilder = hostTargetBuilder;
	}

	public void setModeBuilder(ModeBuilder modeBuilder) {
		this.modeBuilder = modeBuilder;
	}

	public void setNoticeBuilder(NoticeBuilder noticeBuilder) {
		this.noticeBuilder = noticeBuilder;
	}

	public void setRoomstateBuilder(RoomstateBuilder roomstateBuilder) {
		this.roomstateBuilder = roomstateBuilder;
	}

	public void setTwitchMessageBuilder(TwitchMessageBuilder twitchMessageBuilder) {
		this.twitchMessageBuilder = twitchMessageBuilder;
	}

	public void setTwitchUserBuilder(TwitchUserBuilder twitchUserBuilder) {
		this.twitchUserBuilder = twitchUserBuilder;
	}

	public void setUserstateBuilder(UserstateBuilder userstateBuilder) {
		this.userstateBuilder = userstateBuilder;
	}
	
	public ClearChatBuilder getClearChatBuilder() {
		return clearChatBuilder != null ? clearChatBuilder : new ClearChatBuilderDefault();
	}

	public HostTargetBuilder getHostTargetBuilder() {
		return hostTargetBuilder != null ? hostTargetBuilder : new HostTargetBuilderDefault();
	}

	public ModeBuilder getModeBuilder() {
		return modeBuilder != null ? modeBuilder : new ModeBuilderDefault();
	}

	public NoticeBuilder getNoticeBuilder() {
		return noticeBuilder != null ? noticeBuilder : new NoticeBuilderDefault();
	}

	public RoomstateBuilder getRoomstateBuilder() {
		return roomstateBuilder != null ? roomstateBuilder : new RoomstateBuilderDefault();
	}

	public TwitchMessageBuilder getTwitchMessageBuilder() {
		return twitchMessageBuilder != null ? twitchMessageBuilder : new TwitchMessageBuilderDefault();
	}

	public TwitchUserBuilder getTwitchUserBuilder() {
		return twitchUserBuilder != null ? twitchUserBuilder : new TwitchUserBuilderDefault();
	}

	public UserstateBuilder getUserstateBuilder() {
		return userstateBuilder != null ? userstateBuilder : new UserstateBuilderDefault();
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
