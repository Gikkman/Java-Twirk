package com.gikk.twirk.types.mode;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Mode} object. To create a {@link Mode} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface ModeBuilder {

	/**Constructs a new {@link Mode} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link Mode} object.
	 * Make sure that the COMMAND of the message equals "MODE"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link Mode}, or <code>null</code> if a {@link Mode} could not be created
	 */
	public Mode build(TwitchMessage message);
}
