package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Usernotice} object. To create a {@link Usernotice} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface UsernoticeBuilder {

	/**Constructs a new {@link Usernotice} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link Usernotice} object.
	 * Make sure that the COMMAND of the message equals "USERNOTICE"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link Usernotice}, or <code>null</code> if a {@link Usernotice} could not be created
	 */
	public Usernotice build(TwitchMessage message);
}
