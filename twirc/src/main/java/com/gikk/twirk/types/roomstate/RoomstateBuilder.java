package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Roomstate} object. To create a {@link Roomstate} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface RoomstateBuilder {
	
	/**Constructs a new {@link Roomstate} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link Roomstate} object.
	 * Make sure that the COMMAND of the message equals "ROOMSTATE"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link Roomstate}, or <code>null</code> if a {@link Roomstate} could not be created
	 */
	public Roomstate build(TwitchMessage message);
}
