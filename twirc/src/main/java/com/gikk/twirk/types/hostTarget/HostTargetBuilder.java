package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link HostTarget} object. To create a {@link HostTarget} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface HostTargetBuilder {
	
	/**Constructs a new {@link HostTarget} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link HostTarget} object.
	 * Make sure that the COMMAND of the message equals "HOSTTARGET"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link HostTarget}, or <code>null</code> if a {@link HostTarget} could not be created
	 */
	public HostTarget build(TwitchMessage message);
}
