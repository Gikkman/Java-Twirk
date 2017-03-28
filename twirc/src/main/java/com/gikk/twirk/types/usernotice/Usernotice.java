package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.emote.EmoteImpl;
import java.util.List;

public interface Usernotice extends AbstractType {
	
	/**Retrieves this users name, as displayed in Twitch chat
	 * 
	 * @return The user name
	 */
	public String getName();
	
	/**Retrieves info whether this user is a mod in this channel or not
	 * 
	 * @return {@code true} if the user is mod, {@code false} if not
	 */
	public boolean isMod();
	
	/**Retrieves info whether this user has turbo or not
	 * 
	 * @return {@code true} if the user has turbo, {@code false} if not
	 */
	public boolean isTurbo();
	
	/**Retrieves info whether this user is a sub to this channel or not
	 * 
	 * @return {@code true} if the user is a sub, {@code false} if not
	 */
	public boolean isSub();
	
	/**Retrieves this users {@link USER_TYPE} <br>
	 * There are six USER_TYPEs: OWNER, MOD, GLOBAL_MOD, ADMIN, STAFF, DEFAULT.
	 * 
	 * @return The user's USER_TYPE
	 */
	public USER_TYPE getUserType();
	
	/**Retrieves this users display color, as seen in Twitch chat.<br>
	 * The color is a hexadecimal number.
	 * 
	 * @return The users display color, as a hex number
	 */
	public int getColor();
	
	/**Retrieves the users set of badges in Twitch chat. A badge looks like this: <br>
	 * {@code broadcaster/1} <br><br>
	 * 
	 * There are several different badges, such as {@code broadcaster/1}, {@code turbo/1} and so on. I do
	 * not know all of them explicitly, or what to do with them.
	 * 
	 * TODO: Find out more about badges
	 * 
	 * @return Arrays of strings, representing this users badges. Might be empty if user has none.
	 */
	public String[] getBadges();
	
	/**Retrieves this user's unique user ID. This ID is decided by Twitch, and will 
	 * always be the same for the same user
	 * 
	 * @return The users unique user ID
	 */
	public long getUserID();
	
	/**Retrieves the re-subscribing users login name. At the moment, this is the users display name in lower cases. It might
	 * change in the future, if Twitch implements name changes.
	 * 
	 * @return The users login name
	 */
	public String getLogin();
	
	/**Retrieves the users re-subscription message. This message might contain emotes (see {@link #hasEmotes()} and {@link #getEmotes()}. 
	 * It might also be empty, in which case, the message is {@code ""}
	 * 
	 * @return The re-subscription message. Might be {@code ""}
	 */
	public String getMessage();
	
	/**Retrieves the number of months this user has subscribed for
	 * 
	 * @return The number of months
	 */
	public int getMonths();
	
	/**The system message contains the text which'll be printed in the Twitch Chat on Twitch's side. It might look like this: <br>
	 * {@code system-msg=TWITCH_UserName\shas\ssubscribed\sfor\s6\smonths!;}.<br><br>
	 * 
	 * This method returns the system message like it looks from server side. It will, however, change all {@code \s} to spaces.
	 * 
	 * @return The system message
	 */
	public String getSystemMessage();
	
	/**Tells us if this message contained emotes or not. Only PRIVMSG and WHISPER can contain emotes.
	 * 
	 * @return {@code true} if the message contained emotes, <code>false</code> otherwise
	 */
	public boolean hasEmotes();
	
	/**Fetches a list of all all emotes in this message. Each emote is encapsulated in a {@link EmoteImpl}.
	 * The list might be empty, if the message contained no emotes.
	 * 
	 * @return List of emotes (might be empty)
	 */
	public List<Emote> getEmotes();

}
