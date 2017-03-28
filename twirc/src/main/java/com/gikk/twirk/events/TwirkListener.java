package com.gikk.twirk.events;

import com.gikk.twirk.types.clearChat.ClearChat;
import com.gikk.twirk.types.hostTarget.HostTarget;
import com.gikk.twirk.types.mode.Mode;
import com.gikk.twirk.types.notice.Notice;
import com.gikk.twirk.types.roomstate.Roomstate;
import com.gikk.twirk.types.subscriberEvent.SubscriberEvent;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.users.TwitchUser;
import com.gikk.twirk.types.users.Userstate;
import java.util.Collection;

public interface TwirkListener {	
	
	/**Fires for ever incoming message <b>except PING</b>. 
	 * 
	 * @param unformatedMessage The incoming message exactly as it looks from Twitch
	 */
	public void onAnything( String unformatedMessage );
	
	/**Fires for incoming PRIVMSG to the channel the bot is joined in
	 * 
	 * @param sender The user who sent the message. Parsed from the incoming message's tag
	 * @param message The message that was sent, with the tag removed
	 */
	public void onPrivMsg( TwitchUser  sender, TwitchMessage  message );
	
	/**@deprecated 
     * Fires for incoming WHISPERS directed at the bot
	 * 
	 * @param sender The user who sent the whisper. Parsed from the incoming message's tag
	 * @param message The whisper that was sent, with the tag removed
	 */
	public void onWhisper( TwitchUser  sender, TwitchMessage  message );
	
	/**Fires when the bot receives a JOIN from Twitch. Note that Twitch sometimes drops
	 * PART messages, so we might receive a JOIN from a user who we never saw PART. Another
	 * important thing to note is that for large channels (1k chatters +), Twitch only sends
	 * JOINS/PARTS for moderators.<br><br>
	 * 
	 * Also worth noting is that we don't see any properties for the joining user, we only see his/her
	 * Twitch user name in lower case
	 * 
	 * @param joinedNick The joining users Twitch user name, in lower case
	 */
	public void onJoin( String joinedNick );
	
	/**Fires when the bot receives a PART from Twitch. Note that Twitch sometimes drops
	 * JOIN messages, so we might receive a PART from a user who we never saw JOIN. Another
	 * important thing to note is that for large channels (1k chatters +), Twitch only sends
	 * JOINS/PARTS for moderators.<br><br>
	 * 
	 * Also worth noting is that we don't see any properties for the parting user, we only see his/her
	 * Twitch user name in lower case
	 * 
	 * @param partedNick The parting users Twitch user name, in lower case
	 */
	public void onPart( String partedNick );

	/**Fires when we've successfully connected to Twitch's server and joined the channel
	 */
	public void onConnect();
	
	/**Fires when we've disconnected from Twitch's server. <br>
	 * We can try to reconnect onDisconnect
	 */
	public void onDisconnect();
	
	/**Fires whenever we receive a NOTICE from Twitch. See {@link Notice }<br>
	 * NOTICE tells us about certain events, such as being Timed Out, 
	 * 
	 * @param notice The notice we received.
	 */
	public void onNotice( Notice  notice );
	
	/**Fires whenever we receive a HOSTTARGET from Twitch. See {@link HostTarget }
	 * 
	 * @param hostNotice The host notice we received.
	 */
	public void onHost( HostTarget  hostNotice );
	
	/**Fires whenever we receive information about a subscriber event from Twitch. See {@link SubscriberEvent}
	 * 
	 * @param subscriberEvent The event we received.
	 */
	public void onSubscriberEvent( SubscriberEvent subscriberEvent );
	
	/**@deprecated 
     * Fires whenever we receive a MODE from Twitch. See {@link Mode}.<br>
	 * A mode means that a user gained or lost moderator status. However, this
	 * is unreliable, and you should consider looking at the {@link TwitchUser } you
	 * receive in the {@link #onPrivMsg(TwitchUser , TwitchMessage )} instead. Twitch sends
	 * mode notices every now and then, and does not reliably reflect a users current status
	 * 
	 * @param mode The mode notice
	 */
	public void onMode( Mode  mode );

	/**Fires whenever we receive a USERSTATE from Twitch. See {@link Userstate }<br>
	 * USERSTATE is sent whenever the bot sends a message to Twirk. You should <b>never</b> respond
	 * to a USERSTATE, as that will create a cycle that will get your bot banned for spamming Twitch's
	 * server
	 * 
	 * @param userstate The user state we received
	 */
	public void onUserstate( Userstate  userstate );

	/**Fires whenever we receive a ROOMSTATE from Twitch. See {@link Roomstate }<br>
	 * ROOMSTATE is sent when joining a channel and every time one of the chat room settings, 
	 * like slow mode, change. <br>
	 * The message on join contains all room settings. <br>
	 * Changes only contain the relevant tag.
	 * 
	 * @param roomstate The room state we received
	 */
	public void onRoomstate( Roomstate  roomstate );
	
	/**Fires when we receive a CLEARCHAT from Twitch. See {@link ClearChat }<br>
	 * CLEARCHAT comes in two modes: <ul>
	 * <li>USER - This clears everything a certain user has written in chat
	 * <li>TOTAL - This clear everything in chat
	 * </ul>
	 * 
	 * @param clearChat The clear chat notice we received
	 */
	public void onClearChat( ClearChat  clearChat );
	
	/**Fires when we've successfully joined a channel and retrieved the list of
	 * all users that were online. <br><br>
	 * The <code>Collection</code> that is passed with this method contains
	 * the names of all the users that we received by the channels names-list. All these names are in lower case.<br>
	 * Note that this list is read-only. Trying to make changes to it will result in a <code>UnsupportedOperationException</code><br><br>
	 * Future changes to whom are online will be noticed via {@link #onJoin(String)} and {@link #onPart(String)} events
	 * 
	 * @param namesList The unmodifiable collection of all users that Twitch told us were online in this channel.
	 */
	public void onNamesList( Collection<String> namesList );
	
	/**Fires when we receive a USERNOTICE from Twitch. See {@link Usernotice }<br>
	 * A Usernotice tells us about a re-subscription event, either to our channel or to the channel
	 * we are hosting.
	 * 
	 * @param usernotice The Usernotice we received
	 */
	public void onUsernotice(Usernotice usernotice);
	

	/**Fires when we received a message we could not categorize. This might happen
	 * if Twitch changes something suddenly, so we cannot parse the incomming message.
	 * 
	 * @param unformatedMessage The incoming message exactly as it looks from Twitch
	 */
	public void onUnknown( String unformatedMessage );
}
