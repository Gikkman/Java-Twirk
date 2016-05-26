package com.gikk.twirk.types.users;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Userstate} object. To create a {@link Userstate} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface UserstateBuilder {
	
	/**Constructs a new {@link Userstate} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link Userstate} object.
	 * Make sure that the COMMAND equals "USERSTATE"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link Userstate}, or <code>null</code> if a {@link Userstate} could not be created
	 */
	public Userstate build(TwitchMessage message);
}
