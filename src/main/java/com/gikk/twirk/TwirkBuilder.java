package com.gikk.twirk;

import com.gikk.twirk.types.clearChat.ClearChatBuilder;
import com.gikk.twirk.types.clearMsg.ClearMsgBuilder;
import com.gikk.twirk.types.hostTarget.HostTargetBuilder;
import com.gikk.twirk.types.mode.ModeBuilder;
import com.gikk.twirk.types.notice.NoticeBuilder;
import com.gikk.twirk.types.reconnect.ReconnectBuilder;
import com.gikk.twirk.types.roomstate.RoomstateBuilder;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilder;
import com.gikk.twirk.types.usernotice.UsernoticeBuilder;
import com.gikk.twirk.types.users.TwitchUserBuilder;
import com.gikk.twirk.types.users.UserstateBuilder;
import java.net.Socket;
import java.util.function.Consumer;

/**Class for creating instances of {@link Twirk}.<br>
 * To build an instance of {@link Twirk}, the user has to supply the bot's nick and
 * oAuth token. To generate a oAuth token, visit <a href="https://twitchapps.com/tmi/">https://twitchapps.com/tmi/</a><br><br>
 *
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
	private boolean verboseMode = false;

	String server = "irc.chat.twitch.tv";
	int 	port  = 6697;
	boolean useSSL = true;

	String nick;
	String oauth;
	String channel;

	private ClearChatBuilder 	clearChatBuilder;
	private ClearMsgBuilder 	clearMsgBuilder;
	private HostTargetBuilder 	hostTargetBuilder;
	private ModeBuilder 		modeBuilder;
	private NoticeBuilder 		noticeBuilder;
	private RoomstateBuilder 	roomstateBuilder;
	private TwitchMessageBuilder twitchMessageBuilder;
	private TwitchUserBuilder 	twitchUserBuilder;
	private UserstateBuilder 	userstateBuilder;
	private UsernoticeBuilder	usernoticeBuilder;
	private SocketFactory		socketFactory;
	private int					pingIntervalSeconds = 15 + (5 * 60); // Twitch recommends pinging them every >5 minutes

	private Consumer<String>	errorLogger = System.err::println;
	private Consumer<String>	warnLogger = System.out::println;
	private Consumer<String>	infoLogger = System.out::println;
	private Consumer<String>	debugLogger = null;

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	/**Creates a new {@link TwirkBuilder}. To build an instance of {@link Twirk}, we must have
	 * the bot's account nick, and the bot's IRC oAuth token (which can be retrieved from Twitch).
	 * See {@link TwirkBuilder}
	 *
	 * @param channel The channel the bot should join
	 * @param nick The bot's account name (on Twitch)
	 * @param oauth The bot's IRC oAuth token (on Twitch)
	 */
	public TwirkBuilder(String channel, String nick, String oauth){
		this.channel = (channel.startsWith("#") ? channel : "#" + channel).toLowerCase();
		this.nick = nick;
		this.oauth = oauth;
	}

	//***********************************************************
	// 				PUBLIC - Setters
	//***********************************************************
	/**Sets the server which {@link Twirk} will try to connect to Twitch via
	 *
	 * @param server The server address
	 * @return this
	 */
	public TwirkBuilder setServer(String server){
		this.server = server;
		return this;
	}

	/**Sets the port which the {@link Twirk} object will try to connect to Twitch via
	 *
	 * @param port The port number
	 * @return this
	 */
	public TwirkBuilder setPort(int port){
		this.port = port;
		return this;
	}

	/**Decides whether we should use a SSL connection or not. Default is {@code true}. If you do
	 * change this, remember to change the port too, since Twitch listens for SSL and non-SSL traffic
	 * on different ports.
	 * See <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">
	 * 				https://github.com/justintv/Twitch-API/blob/master/IRC.md </a>
	 *<br>
	 * This setting will affect what type of {@link Socket} we create to connect to Twitch. If a custom {@link SocketFactory}
	 * has been assigned to this TwirkBuilder, the value assigned in this method is not used. This method's value is only
	 * relevant to decide what socket to create by default (a SSL socket or a non-SSL socket)
	 *
	 * @param ssl Whether we should use SSL connection or not.
	 * @return this
	 */
	public TwirkBuilder setSSL(boolean ssl) {
		this.useSSL = ssl;
		return this;
	}

	/**Sets the {@link Twirk} object to VerboseMode<br>
	 * In VerboseMode, every message that is received by {@link Twirk} will be printed to console. Default value is {@code false}
	 * <p>
	 * Note: This method is mostly kept for convenience. What it does in practise is assign {@code System.out.println}
	 * to the {@link TwirkBuilder#setDebugLogMethod}. You can fully customize which methods should consume the
	 * various logging messages, by utilizing the {@code TwirkBuilder#setXXXLogMethod} methods.
	 *
	 * @param verboseMode {@code true} is you want {@link Twirk} in VerboseMode
	 * @return this
	 */
	public TwirkBuilder setVerboseMode(boolean verboseMode){
		if(verboseMode) {
			this.debugLogger = System.out::println;
		}
		else {
			this.debugLogger = null;
		}
		return this;
	}

	/**Sets the {@link ClearChatBuilder}. Useful if you want to use your custom implementations of a {@link ClearChatBuilder}. If
	 * no {@link ClearChatBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param clearChatBuilder The {@link UserstateBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setClearChatBuilder(ClearChatBuilder clearChatBuilder) {
		this.clearChatBuilder = clearChatBuilder;
		return this;
	}

	/**Sets the HostTargetBuilder. Useful if you want to use your custom implementations of a HostTargetBuilder. If
	 * no HostTargetBuilder is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param hostTargetBuilder The {@link HostTargetBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setHostTargetBuilder(HostTargetBuilder hostTargetBuilder) {
		this.hostTargetBuilder = hostTargetBuilder;
		return this;
	}

	/**Sets the {@link ModeBuilder}. Useful if you want to use your custom implementations of a {@link ModeBuilder}. If
	 * no {@link ModeBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param modeBuilder The {@link ModeBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setModeBuilder(ModeBuilder modeBuilder) {
		this.modeBuilder = modeBuilder;
		return this;
	}

	/**Sets the {@link NoticeBuilder}. Useful if you want to use your custom implementations of a {@link NoticeBuilder}. If
	 * no {@link NoticeBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param noticeBuilder The {@link NoticeBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setNoticeBuilder(NoticeBuilder noticeBuilder) {
		this.noticeBuilder = noticeBuilder;
		return this;
	}

	/**Sets the {@link RoomstateBuilder}. Useful if you want to use your custom implementations of a {@link RoomstateBuilder}. If
	 * no {@link RoomstateBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param roomstateBuilder The {@link RoomstateBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setRoomstateBuilder(RoomstateBuilder roomstateBuilder) {
		this.roomstateBuilder = roomstateBuilder;
		return this;
	}

	/**Sets the {@link TwitchMessageBuilder}. Useful if you want to use your custom implementations of a {@link TwitchMessageBuilder}. If
	 * no {@link TwitchMessageBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param twitchMessageBuilder The {@link TwitchMessageBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setTwitchMessageBuilder(TwitchMessageBuilder twitchMessageBuilder) {
		this.twitchMessageBuilder = twitchMessageBuilder;
		return this;
	}

	/**Sets the {@link TwitchUserBuilder}. Useful if you want to use your custom implementations of a {@link TwitchUserBuilder}. If
	 * no {@link TwitchUserBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param twitchUserBuilder The {@link TwitchUserBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setTwitchUserBuilder(TwitchUserBuilder twitchUserBuilder) {
		this.twitchUserBuilder = twitchUserBuilder;
		return this;
	}

	/**Sets the {@link UsernoticeBuilder}. Useful if you want to use your custom implementations of a {@link UsernoticeBuilder}. If
	 * no {@link UsernoticeBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param usernoticeBuilder The {@link UsernoticeBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setUsernoticeBuilder(UsernoticeBuilder usernoticeBuilder) {
		this.usernoticeBuilder = usernoticeBuilder;
		return this;
	}

	/**Sets the {@link ReconnectBuilder}. Useful if you want to use your custom implementations of a {@link ReconnectBuilder}. If
	 * no {@link ReconnectBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param reconnectBuilder The {@link ReconnectBuilder} you want the {@link Twirk} object to use
	 * @return this
	 * @deprecated ReconnectBuilder was never used by the bot anyway, since there is nothing to
	 *  			process during a reconnect request. This method will be removed in future releases.
	 */
	public TwirkBuilder setReconnectBuilder(ReconnectBuilder reconnectBuilder) {
		return this;
	}

	/**Sets the {@link UserstateBuilder}. Useful if you want to use your custom implementations of a {@link UserstateBuilder}. If
	 * no {@link UserstateBuilder} is assigned, the created {@link Twirk} object will use the default implementation.
	 *
	 * @param userstateBuilder The {@link UserstateBuilder} you want the {@link Twirk} object to use
	 * @return this
	 */
	public TwirkBuilder setUserstateBuilder(UserstateBuilder userstateBuilder) {
		this.userstateBuilder = userstateBuilder;
		return this;
	}

    /**Sets the {@link SocketFactory}. Useful if you want to use your custom implementations of a {@link Socket}. If
	 * no {@link SocketFactory} is assigned, the created {@link Twirk} object will chose a
	 * suitable {@link SocketFactory} implementation, depending on whether SSL connection is chosen or not
	 * (default is UseSSL)
	 *
	 * @param socketFactory The {@link SocketFactory} that Twirk should use
	 * @return this
	 */
	public TwirkBuilder setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
		return this;
	}

	/**Sets the interval at which we will ping Twitch's chat interface, in case we haven't heard anything from it in a
	 * while. Twitch recommends setting this value to >5 minutes, so it defaults to 5:15.
	 * <br>
	 * A ping will only fire in case we haven't heard anything from Twitch for more than the set interval. Twitch also
	 * pings us every ~5 minutes, hence why they recommend setting this to more than 5 minutes. However, sometimes there
	 * are connection issues with Twitch, and someone using this library might want to ping Twitch more regularly to
	 * ensure connectivity.
	 * <br>
	 * Once the ping interval has passed, and we haven't seen a message from Twitch, we will send a PING message. If we
	 * the ping interval then passes again without a reply, the Twirk instance will issue a disconnect. Hence, if Twitch
	 * drops our connection somehow, we will disconnect after two times the ping interval.
	 * <br>
	 *  Note that this might upset Twitch, if done excessively. Do not set this value too low.
	 *
	 * @param intervalSeconds Ping interval, in seconds
	 * @return this
	 * @throws IllegalArgumentException If intervalSeconds <= 0
	 */
	public TwirkBuilder setPingInterval(int intervalSeconds) {
		if(intervalSeconds <= 0) throw new IllegalArgumentException("Timeout cannot be 0 or less");
		this.pingIntervalSeconds = intervalSeconds;
		return this;
	}

	/** Sets the method which will be called whenever an error message should be logged. If not set by the user,
	 * this defaults to {@link System#err }. To disable logging at this level,
	 * 	 * set this to {@code null}
	 * <br>
	 * Should you want to set this to, say, {@link java.util.logging.Logger#severe(String)} ()}, you can
	 * use the syntax <br>
	 * <pre> builder.setErrorLogger(logger::severe) </pre>
	 *
	 * @param errorLogMethod the method which to call to log error messages
	 * @return this
	 */
	public TwirkBuilder setErrorLogMethod(Consumer<String> errorLogMethod) {
		this.errorLogger = errorLogMethod;
		return this;
	}

	/** Sets the method which will be called whenever a warning message
	 * should be logged. If not set by the user, this defaults to {@link System#out }. To disable logging at this level,
	 * 	 * set this to {@code null}
	 * <br>
	 * Should you want to set this to, say, {@link java.util.logging.Logger#warning(String)}, you can
	 * use the syntax <br>
	 * <pre> builder.setErrorLogger(logger::warning) </pre>
	 *
	 * @param warningLogMethod the method which to call to log warning messages
	 * @return this
	 */
	public TwirkBuilder setWarningLogMethod(Consumer<String> warningLogMethod) {
		this.warnLogger = warningLogMethod;
		return this;
	}

	/** Sets the method which will be called whenever an info message
	 * should be logged. If not set by the user, this defaults to {@link System#out }. To disable logging at this level,
	 * set this to {@code null}
	 * <br>
	 * Should you want to set this to, say, {@link java.util.logging.Logger#info(String)}, you can
	 * use the syntax <br>
	 * <pre> builder.setErrorLogger(logger::info) </pre>
	 *
	 * @param infoLogMethod the method which to call to log info messages
	 * @return this
	 */
	public TwirkBuilder setInfoLogMethod(Consumer<String> infoLogMethod) {
		this.infoLogger = infoLogMethod;
		return this;
	}

	/** Sets the method which will be called whenever an debug message
	 * should be logged. If not set by the user, this defaults to {@code null }. To disable logging at this level,
	 * set this to {@code null}.
	 * <br>
	 * Should you want to set this to, say, {@link java.util.logging.Logger#fine(String)}, you can
	 * use the syntax <br>
	 * <pre> builder.setErrorLogger(logger::fine) </pre>
	 *
	 * @param debugLogMethod the method which to call to log debug messages
	 * @return this
	 */
	public TwirkBuilder setDebugLogMethod(Consumer<String> debugLogMethod) {
		this.debugLogger = debugLogMethod;
		return this;
	}

	//***********************************************************
	// 				PUBLIC - Getters
	//***********************************************************
	/**Retrieves the assigned {@link ClearChatBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link ClearChatBuilder}
	 */
	public ClearChatBuilder getClearChatBuilder() {
		return clearChatBuilder != null ? clearChatBuilder : ClearChatBuilder.getDefault();
	}
	
	/**Retrieves the assigned {@link ClearMsgBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link ClearMsgBuilder}
	 */
	public ClearMsgBuilder getClearMsgBuilder() {
		return clearMsgBuilder != null ? clearMsgBuilder : ClearMsgBuilder.getDefault();
	}

	/**Retrieves the assigned {@link HostTargetBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link HostTargetBuilder}
	 */
	public HostTargetBuilder getHostTargetBuilder() {
		return hostTargetBuilder != null ? hostTargetBuilder : HostTargetBuilder.getDefault();
	}

	/**Retrieves the assigned {@link ModeBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link ModeBuilder}
	 */
	public ModeBuilder getModeBuilder() {
		return modeBuilder != null ? modeBuilder : ModeBuilder.getDefault();
	}

	/**Retrieves the assigned {@link NoticeBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link NoticeBuilder}
	 */
	public NoticeBuilder getNoticeBuilder() {
		return noticeBuilder != null ? noticeBuilder : NoticeBuilder.getDefault();
	}

	/**Retrieves the assigned {@link RoomstateBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link RoomstateBuilder}
	 */
	public RoomstateBuilder getRoomstateBuilder() {
		return roomstateBuilder != null ? roomstateBuilder : RoomstateBuilder.getDefault();
	}

	/**Retrieves the assigned {@link TwitchMessageBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link TwitchMessageBuilder}
	 */
	public TwitchMessageBuilder getTwitchMessageBuilder() {
		return twitchMessageBuilder != null ? twitchMessageBuilder : TwitchMessageBuilder.getDefault();
	}

	/**Retrieves the assigned {@link TwitchUserBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link TwitchUserBuilder}
	 */
	public TwitchUserBuilder getTwitchUserBuilder() {
		return twitchUserBuilder != null ? twitchUserBuilder : TwitchUserBuilder.getDefault();
	}

	/**Retrieves the assigned {@link UserstateBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link UserstateBuilder}
	 */
	public UserstateBuilder getUserstateBuilder() {
		return userstateBuilder != null ? userstateBuilder : UserstateBuilder.getDefault();
	}

	/**Retrieves the assigned {@link UsernoticeBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link UsernoticeBuilder}
	 */
	public UsernoticeBuilder getUsernoticeBuilder() {
		return usernoticeBuilder != null ? usernoticeBuilder : UsernoticeBuilder.getDefault();
	}

	/**Retrieves the assigned {@link ReconnectBuilder}, or the default one, if none is assigned.
	 *
	 * @return This builders current {@link ReconnectBuilder}
	 * @deprecated ReconnectBuilder was never used by the bot anyway, since there is nothing to
	 * 				process during a reconnect request. This method will be removed in future releases.
	 */
	public ReconnectBuilder getReconnectBuilder() {
		return ReconnectBuilder.getDefault();
	}

    /**Retrieves the assigned {@link SocketFactory}, or the default if none has been assigned. The default factory
	 * depends on whether {@link #setSSL(boolean)} has been set to true or not (defaults to true).
     *
     * @return This builder's current {@link SocketFactory}
     */
    public SocketFactory getSocketFactory() {
        return socketFactory != null ? socketFactory : SocketFactory.getDefault(this.useSSL);
    }

	/**Retrieves the assigned ping interval (in seconds). Defaults to 5:15 is not set by the user
	 *
	 * @return ping interval
	 */
	public int getPingInterval() {
    	return this.pingIntervalSeconds;
	}

	/**Retrieves a built {@link TwirkLogger}
	 *
	 * @return a {@link TwirkLogger}
	 */
	TwirkLogger getLogger() {
		return new TwirkLogger(
				errorLogger, warnLogger,
				infoLogger, debugLogger);
	}

	//***********************************************************
	// 				PUBLIC - Build
	//***********************************************************
	/**Creates a Twirk object, with the parameters assigned to this
	 * builder.
	 *
	 * @return A configured Twirk object
	 */
	public Twirk build() {
		return new Twirk(this);
	}
}
