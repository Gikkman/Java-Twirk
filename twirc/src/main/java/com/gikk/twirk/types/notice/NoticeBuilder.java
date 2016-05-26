package com.gikk.twirk.types.notice;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Notice} object. To create a {@link Notice} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface NoticeBuilder {
	
	/**Constructs a new {@link Notice} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link Notice} object.
	 * Make sure that the COMMAND of the message equals "NOTICE"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link Notice}, or <code>null</code> if a {@link Notice} could not be created
	 */
	public Notice build(TwitchMessage message);
}
