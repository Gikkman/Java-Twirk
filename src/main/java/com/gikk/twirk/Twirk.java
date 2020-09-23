package com.gikk.twirk;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.clearChat.ClearChat;
import com.gikk.twirk.types.clearChat.ClearChatBuilder;
import com.gikk.twirk.types.hostTarget.HostTarget;
import com.gikk.twirk.types.hostTarget.HostTargetBuilder;
import com.gikk.twirk.types.mode.Mode;
import com.gikk.twirk.types.mode.ModeBuilder;
import com.gikk.twirk.types.notice.Notice;
import com.gikk.twirk.types.notice.NoticeBuilder;
import com.gikk.twirk.types.roomstate.Roomstate;
import com.gikk.twirk.types.roomstate.RoomstateBuilder;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilder;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.usernotice.UsernoticeBuilder;
import com.gikk.twirk.types.users.TwitchUser;
import com.gikk.twirk.types.users.TwitchUserBuilder;
import com.gikk.twirk.types.users.Userstate;
import com.gikk.twirk.types.users.UserstateBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**Class for communicating with the TwitchIrc chat.<br>
 * To create instances of Twirk, see {@link TwirkBuilder}.<br><br>
 *
 * TwitchIrc behaves a bit different from regular IRC, in that it uses only a few types of message types
 * and it has a few message types of its own. This class is intended to cover all of Twitch's special
 * features, and thus making it easier to communicate with Twitch chat programmatically. <br><br>
 *
 * To connect to Twitch chat, you need to generate a Twitch account and you need to generate an IRC oAuth
 * token for that account. To generate a oAuth token, visit <a href="https://twitchapps.com/tmi/">https://twitchapps.com/tmi/</a><br><br>
 *
 * This library is very basic, and only intended for simplistic bots. I would not recommend using this
 * in a advanced, long term or commercial product. For those kinds of applications, consider looking
 * into a more robust and well tested library, such as IrcAPI or PircBotX.<br><br>
 *
 * For documentation about how Twitch communicates via IRC,
 * see <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">
 * 				https://github.com/justintv/Twitch-API/blob/master/IRC.md </a>
 *
 * Code inspired by <a href="http://archive.oreilly.com/pub/h/1966#code">http://archive.oreilly.com/pub/h/1966#code</a>
 *
 * @author Gikkman
 *
 */
public final class Twirk {
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private final String nick;
	private final String pass;
	private final String channel;
	final boolean verboseMode;

	private OutputThread outThread;
	private InputThread inThread;
	private final OutputQueue queue;

	private boolean resourcesCreated = false;
	private boolean isConnected = false;
	private boolean isDisposed  = false;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;

	private final ArrayList<TwirkListener> listeners = new ArrayList<>();
	final Set<String> moderators = Collections.newSetFromMap( new ConcurrentHashMap<>() );
	final Set<String> online	 = Collections.newSetFromMap( new ConcurrentHashMap<>() );

	private final ClearChatBuilder 		clearChatBuilder;
	private final HostTargetBuilder 	hostTargetBuilder;
	private final ModeBuilder 			modeBuilder;
	private final NoticeBuilder 		noticeBuilder;
	private final RoomstateBuilder 		roomstateBuilder;
	private final TwitchMessageBuilder 	twitchMessageBuilder;
	private final TwitchUserBuilder 	twitchUserBuilder;
	private final UserstateBuilder 		userstateBuilder;
	private final UsernoticeBuilder		usernoticeBuilder;
	private final Socket socket;

	//***********************************************************************************************
	//											CONSTRUCTOR
	//***********************************************************************************************
	Twirk(TwirkBuilder builder) {
		this.nick = builder.nick;
		this.pass = builder.oauth;
		this.channel = builder.channel;
		this.verboseMode = builder.verboseMode;

		this.clearChatBuilder = builder.getClearChatBuilder();
		this.hostTargetBuilder= builder.getHostTargetBuilder();
		this.modeBuilder	  = builder.getModeBuilder();
		this.noticeBuilder	  = builder.getNoticeBuilder();
		this.roomstateBuilder = builder.getRoomstateBuilder();
		this.twitchUserBuilder= builder.getTwitchUserBuilder();
		this.userstateBuilder = builder.getUserstateBuilder();
		this.twitchMessageBuilder = builder.getTwitchMessageBuilder();
		this.usernoticeBuilder= builder.getUsernoticeBuilder();

        this.socket = builder.getSocket();

		this.queue = new OutputQueue();

		addIrcListener( new TwirkMaintainanceListener(this) );
	}

	//***********************************************************************************************
	//											PUBLIC
	//***********************************************************************************************
	/**Sends a message directly to the server. The message will not be formatted in
	 * any way. <br>
	 * This method should be used very sparsely, as it sidesteps the messaging
	 * delay and can get your bot Irc-banned on Twitch's side (might happen if the bot sends
	 * more than 20 messages in 30 seconds).
	 *
	 * @param message The message that should be sent
	 */
    public void serverMessage(String message) {
		outThread.quickSend(message);
	}
    
        /**Enqueues a whisper at the end of the message queue. The whisper will
         * be sent when all messages enqueued before it has been sent.
         * <br>
         * Be aware that too frequent use of whispers might get your bot rate 
         * limited.
         *
         * @param receiver The user that should receive the message
         * @param message The message to whisper
         */
       public void whisper(TwitchUser receiver, String message){
               whisper(receiver.getUserName(), message);
       }
       
       /**Enqueues a whisper at the end of the message queue. The whisper will
         * be sent when all messages enqueued before it has been sent.
         * <br>
         * Be aware that too frequent use of whispers might get your bot rate 
         * limited.
         *
         * @param userName The name of the user that should receive the message.
         * @param message The message to whisper
         */
       public void whisper(String userName, String message){
               queue.add("PRIVMSG " + channel + " :/w " + userName + " " + message);
       }

	/**Enqueues a message at the end of the message queue. The message will be
	 * sent to the channel when all messages enqueued before it has been sent.
	 *
	 * @param message The message that should be sent
	 */
	public void channelMessage(String message){
		queue.add("PRIVMSG " + channel + " :" + message);
	}

	/**Enqueues a message at the front of the message queue. The message will be sent as soon as possible.
	 *
	 * @param message The message that should be sent
	 */
	public void priorityChannelMessage(String message){
		queue.addFirst("PRIVMSG " + channel + " :" + message);
	}

	/**Check if this Twirk instance is currently connected to Twitch. If we are not, and we are not
	 * {@link #isDisposed()}, then we may try to reconnect. See {@link #connect()}
	 *
	 * @return <code>True</code> if we are connected
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**Check if this Twirk instance has been disposed. If it has, no further
	 * connect attempts will succeed.
	 *
	 * @return <code>True</code> if it is disposed
	 */
	public boolean isDisposed() {
		return isDisposed;
	}

	/**Fetches a set of all the users that are <b>currently</b> online in the joined channel. Note that this set is
	 * <b>copy</b> of the underlying set of online users. Thus, changes to the original set will not be visible
	 * in the returned set, and changes to the returned set will not affect the original set.<br><br>
	 *
	 * For getting all users online as soon as we connect, and the server has told us who are online,
	 * see {@link TwirkListener#onNamesList(java.util.Collection)}
	 *
	 * Also worth noting is that the set only contains the users names in lower case letters.
	 *
	 * @return A copy of the Set of online users.
	 */
	public Set<String> getUsersOnline(){
		synchronized (online) {
			return new HashSet<>(online);
		}
	}

	/**Fetches a set of all the moderators that are <b>currently</b> online in the joined channel. Note that this set is
	 * <b>copy</b> of the underlying set of online moderators. Thus, changes to the original set will not be visible
	 * in the returned set, and changes to the returned set will not affect the original set.<br><br>
	 *
	 * Also worth noting is that the set only contains the moderators names in lower case letters.
	 *
	 * @return A copy of the Set of online moderators.
	 */
	public Set<String> getModsOnline(){
		synchronized (moderators) {
			return new HashSet<>(moderators);
		}
	}

	/**Fetches the nick of the bot, which it will use to connect to an IRC server
	 *
	 * @return The bot nick
	 */
	public String getNick() {
		return nick;
	}

	/**Adds a specific listener to the list of active listeners
	 *
	 * @param listener Listener to be added
	 */
	public void addIrcListener(TwirkListener listener){
		synchronized (listeners) {
			this.listeners.add(listener);
		}
	}

	/**Removes a specific listener from the list of active listeners
	 *
	 * @param listener Listener to be removed
	 * @return <code>true</code> if the listener was removed
	 */
	public boolean removeIrcListener(TwirkListener listener){
		synchronized (listeners) {
			return this.listeners.remove(listener);
		}
	}

    /**Connects to the Twitch server and joins the channel which was designated in the TwirkBuilder.
     *
     * @return {@code true} if connection was successful
     * @throws IOException In case the BufferedReader or BufferedWriter throws an error during connection. Might be due to timeout, socket closing or something else
     * @throws InterruptedException In case the Twirk process is interrupted while connecting
     */
    public synchronized boolean connect() throws IOException, InterruptedException{
    	if( isDisposed ){
    		System.err.println("\tError. Cannot connect. This Twirk instance has been disposed.");
	    	return false;
    	}
    	if( isConnected ){
	    	System.err.println("\tError. Cannot connect. Already connected to Twitch server");
	    	return false;
    	}

    	if( !resourcesCreated ) {
            createResources(); //Creates a socket and our input/output threads
        }

        int oldTimeout = socket.getSoTimeout();
    	socket.setSoTimeout(10 * 1000); //Set a timeout for connection to 10 seconds, during connection
    	isConnected = doConnect();
    	socket.setSoTimeout(oldTimeout); //Return timeout to what it was before connecting

    	if( isConnected ){
    		//Start the output thread
    		outThread.start();

    		//Add capacities to the bot and wait for them to take effect
    		addCapacities();
    		Thread.sleep(1000);

    		//Start the input thread
    		inThread.start();

    		//Join the channel
    		serverMessage("JOIN " + channel);

    		for( TwirkListener listener: listeners ) {
                listener.onConnect();
            }

    		return true;
    	}
    	return false;
    }

	/**Closes the connection to the IrcServer, leaves all channels, terminates the input- and output thread and
     * frees all resources. <br><br>
     *
     * It is safe to call this method even if connections are already closed.<br><br>
     *
     * This method is different from {@link #close()} in that it calls the {@link TwirkListener#onDisconnect()} method
     * of all the listeners. A listener may thus attempt to reconnect
     */
	public synchronized void disconnect() {
		//Since several sources can call this method on program shutdown, we avoid entering it again if
		//we've already disconnected
		if( !isConnected || isDisposed ) {
            return;
        }

		isConnected = false;

		System.out.println("\n\tDisconnecting from Twitch chat...");
		releaseResources();
		System.out.println("\tDisconnected from Twitch chat\n");

		for( TwirkListener l : listeners ) {
            l.onDisconnect();
        }
	}

	/**Closes the connection to the IrcServer, leaves all channels, terminates the input- and output thread and
     * frees all resources. <br><br>
     *
     * It is safe to call this method even if connections are already closed.<br><br>
     *
     * This method is different from {@link Twirk#disconnect()} in that it <b>does not</b> call the {@link TwirkListener#onDisconnect()} method
     * of any of the listeners. Thus, this method is intended to be called if you want to make sure no reconnect attempts are performed.
     */
	public synchronized void close(){
		if( isDisposed ) {
            return;
        }

		isConnected = false;
		isDisposed = true;

		System.out.println("\n\tDisposing of IRC...");
		releaseResources();
		System.out.println("\tDisposing of IRC completed\n");
	}


	//***********************************************************************************************
	//										PRIVATE and PACKAGE
	//***********************************************************************************************
	private void createResources() throws IOException{
    	socket.setSoTimeout(6 * 60 * 1000); //Set a timeout for connection to 6 minutes. Twitch's default timeout is 5 minutes

		writer = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		reader = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

		this.outThread = new OutputThread(this, queue, reader, writer);
		this.inThread  = new InputThread(this, reader, writer);

		resourcesCreated  = true;
	}

	private void releaseResources(){
		resourcesCreated = false;

		outThread.end();
		inThread.end();

		try { socket.close(); }
		catch (IOException ignored) {  }

		try { reader.close(); }
		catch (IOException ignored) {  }

		try { writer.close(); }
		catch (IOException ignored) {  }
	}


	private boolean doConnect() throws IOException{
		// Log on to the server.
		writer.write("PASS " + pass + "\r\n");
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + nick + " 8 * : GikkBot\r\n");
        writer.flush( );


        // Read lines from the server until it tells us we have connected.
        String line;
        while ((line = reader.readLine()) != null) {
            if(verboseMode) {
                System.out.println("IN  " + line);
            }
            //When we get a message containing 004, we have successfully logged in
            if (line.contains("004")) {
                return true;
            }
            else if (line.contains("Error logging in")) {
                return false;
            }
        }

		//We hit this return if the reader timed out, or we reached the end of stream
        return false;
	}

	/**Gives us the appropriate Twitch capacities, such as seeing JOIN/PART messages,
	 * to send Twitch commands (such as .timeout, .mod and so on) and to see
	 * users tags (such as display color)
	 */
	private void addCapacities(){
		serverMessage("CAP REQ :twitch.tv/membership");
		serverMessage("CAP REQ :twitch.tv/commands");
		serverMessage("CAP REQ :twitch.tv/tags");
	}

    /**Tells the OutputThread how long we should wait, at minimum, in between
     * each message sent to Twitch.
     *
     * @param millis the delay
     */
    void setOutputMessageDelay(int millis){
        this.outThread.setMessageDelay(millis);
    }

	void incommingMessage(String line){
		//PING is a bit strange, so we need to handle it separately. And also, we want to respond to a ping
		//before we do anything else.
		if ( line.contains("PING") ) {

    		// A PING contains the message "PING MESSAGE", and we want to reply with MESSAGE as well
    		// Hence, we reply "PONG MESSAGE" . That's where the substring(5) comes from bellow, we strip
    		//out everything but the message
			if (verboseMode) {
				System.out.println("IN  " + line);
			}
    		serverMessage("PONG " + line.substring(5) ); //Remove the "PING " part, and send the rest back
    		return;
		}

		//Call all the appropriate listeners for the given message.
		synchronized (listeners) {
			//First, we call all onAnything messages
			for(TwirkListener l : listeners ) {
                l.onAnything( line );
            }

			TwitchMessage message = twitchMessageBuilder.build(line);

            //This message is a reply for a capacity request. Just ignore it
            String s = message.getCommand();
            switch (s) {
                case "JOIN":
                {
                    String userName = parseUsername( message.getPrefix() );
                    for(TwirkListener l : listeners ) {
                        l.onJoin( userName );
                    }
                    break;
                }
                case "MODE":
                {
                    Mode mode = modeBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onMode( mode );
                    }
                    break;
                }
                case "PART":
                {
                    String userName = parseUsername( message.getPrefix() );
                    for(TwirkListener l : listeners ) {
                        l.onPart( userName );
                    }
                    break;
                }
                case "CLEARCHAT":
                {
                    ClearChat clearChat = clearChatBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onClearChat(clearChat);
                    }
                    break;
                }
                case "PRIVMSG":
                {
                    TwitchUser user = twitchUserBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onPrivMsg(user, message);
                    }
                    break;
                }
				case "WHISPER":
				{
					TwitchUser user = twitchUserBuilder.build(message);
					for(TwirkListener l : listeners ) {
						l.onWhisper(user, message);
					}
					break;
				}
                case "NOTICE":
                {
                    Notice notice = noticeBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onNotice( notice );
                    }
                    break;
                }
                case "USERSTATE":
                {
                    Userstate userstate = userstateBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onUserstate( userstate );
                    }
                    break;
                }
                case "USERNOTICE":
                {
                    TwitchUser user = twitchUserBuilder.build(message);
                    Usernotice usernotice = usernoticeBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onUsernotice(user, usernotice );
                    }
                    break;
                }
                case "ROOMSTATE":
                {
                    Roomstate roomstate = roomstateBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onRoomstate(roomstate);
                    }
                    break;
                }
                case "HOSTTARGET":
                {
                    HostTarget hostTarget = hostTargetBuilder.build(message);
                    for(TwirkListener l : listeners ) {
                        l.onHost(hostTarget);
                    }
                    break;
                }
				case "RECONNECT":
				{
					for(TwirkListener l : listeners ) {
						l.onReconnect();
					}
					break;
				}
                case "353":
                {
                    //Code 353 is USER LIST messages, which lists users online separated by a space
                    List<String> users = Arrays.asList( message.getContent().split(" ") );
                    online.addAll( users );
                    break;
                }
                case "366":
                {
                	//Code 366 means the USER LIST has ended
                    Set<String> users = Collections.unmodifiableSet(online);
                    for(TwirkListener l : listeners ) {
                        l.onNamesList(users);
                    }
                    break;
                }
                default:
                {
                    //If we've gotten all the way down here, we don't know this message's type
                    for(TwirkListener l : listeners ) {
                        l.onUnknown( line );
                    }
                }
            }
		}
	}

	private String parseUsername(String prefix) {
		/* The user name is extracted from the message's prefix.
		 * JOIN or PART messages are formatted like this:
		 *
		 * :twitch_username!twitch_username@twitch_username.tmi.twitch.tv JOIN #channel
		 */
		return prefix.substring( prefix.charAt(0) == ':' ? 1 : 0, prefix.indexOf('!'));
	}
}
