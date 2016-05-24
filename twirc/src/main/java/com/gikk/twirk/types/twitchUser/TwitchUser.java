package com.gikk.twirk.types.twitchUser;

import com.gikk.twirk.types.USER_TYPE;

public interface TwitchUser {

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
	public int getUserID();
}
