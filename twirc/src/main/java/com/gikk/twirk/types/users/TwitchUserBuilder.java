package com.gikk.twirk.types.users;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link TwitchUser} object. To create a {@link TwitchUser} object, call the <br>{@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface TwitchUserBuilder {
	
	/**Constructs a new {@link TwitchUser} object, from a {@link TwitchMessage}<br><br>
	 * 
	 * @param message The {@link TwitchMessage} which this user sent
	 * @return A {@link TwitchUser}, or <code>null</code> if no TwitchUser could be built
	 */
	public TwitchUser build(TwitchMessage message);
}
