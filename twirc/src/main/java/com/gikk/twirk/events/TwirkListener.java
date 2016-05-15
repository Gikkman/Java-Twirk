package com.gikk.twirk.events;

import com.gikk.twirk.types.TwirkClearChat;
import com.gikk.twirk.types.TwirkHostTarget;
import com.gikk.twirk.types.TwirkMessage;
import com.gikk.twirk.types.TwirkMode;
import com.gikk.twirk.types.TwirkNotice;
import com.gikk.twirk.types.TwirkRoomstate;
import com.gikk.twirk.types.TwirkUser;
import com.gikk.twirk.types.TwirkUserstate;

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
	public void onPrivMsg( TwirkUser sender, TwirkMessage message );
	
	/**Fires for incoming WHISPERS directed at the bot
	 * 
	 * @param sender The user who sent the whisper. Parsed from the incoming message's tag
	 * @param message The whisper that was sent, with the tag removed
	 */
	public void onWhisper( TwirkUser sender, TwirkMessage message );
	
	/**Fires when the bot receives a JOIN from Twitch. Note that Twitch sometimes drops
	 * PART messages, so we might receive a JOIN from a user who we never saw PART.<br><br>
	 * 
	 * Also worth noting is that we don't see any properties for the joining user, we only see his/her
	 * Twitch user name in lower case
	 * 
	 * @param joinedNick The joining users Twitch user name, in lower case
	 */
	public void onJoin( String joinedNick );
	
	/**Fires when the bot receives a PART from Twitch. Note that Twitch sometimes drops
	 * JOIN messages, so we might receive a PART from a user who we never saw JOIN.<br><br>
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
	
	/**Fires whenever we receive a NOTICE from Twitch. See {@link TwirkNotice}<br>
	 * NOTICE tells us about certain events, such as being Timed Out, 
	 * 
	 * @param notice The notice we received.
	 */
	public void onNotice( TwirkNotice notice );
	
	/**Fires whenever we receive a HOST from Twitch. See {@link TwirkHostTarget}
	 * 
	 * @param hostNotice The host notice we received.
	 */
	public void onHost( TwirkHostTarget hostNotice );
	
	/**Fires whenever we receive a MODE from Twitch. See {@link TwirkMode}.<br>
	 * A mode means that a user gained or lost moderator status. However, this
	 * is unreliable, and you should consider looking at the {@link TwirkUser} you
	 * receive in the {@link #onPrivMsg(TwirkUser, TwirkMessage)} instead. Twitch sends
	 * mode notices every now and then, and does not reliably reflect a users current status
	 * 
	 * @param mode The mode notice
	 */
	public void onMode( TwirkMode mode );

	/**Fires whenever we receive a USERSTATE from Twitch. See {@link TwirkUserstate}<br>
	 * USERSTATE is sent whenever the bot sends a message to Twirk. You should <b>never</b> respond
	 * to a USERSTATE, as that will create a cycle that will get your bot banned for spamming Twitch's
	 * server
	 * 
	 * @param userstate The user state we received
	 */
	public void onUserstate( TwirkUserstate userstate );

	/**Fires whenever we receive a ROOMSTATE from Twitch. See {@link TwirkRoomstate}<br>
	 * ROOMSTATE is sent when joining a channel and every time one of the chat room settings, 
	 * like slow mode, change. <br>
	 * The message on join contains all room settings. <br>
	 * Changes only contain the relevant tag.
	 * 
	 * @param roomstate The room state we received
	 */
	public void onRoomstate( TwirkRoomstate roomstate );
	
	/**Fires when we receive a CLEARCHAT from Twitch. See {@link TwirkClearChat}<br>
	 * CLEARCHAT comes in two modes: <ul>
	 * <li>USER - This clears everything a certain user has written in chat
	 * <li>TOTAL - This clear everything in chat
	 * </ul>
	 * 
	 * @param clearChat The clear chat notice we received
	 */
	public void onClearChat( TwirkClearChat clearChat );

	/**Fires when we received a message we could not categorize. This might happen
	 * if Twitch changes something suddenly, so we cannot parse the incomming message.
	 * 
	 * @param unformatedMessage The incoming message exactly as it looks from Twitch
	 */
	public void onUnknown( String unformatedMessage );
}
