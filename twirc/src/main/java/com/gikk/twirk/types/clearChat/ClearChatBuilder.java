package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link ClearChat} object. To create a {@link ClearChat} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface ClearChatBuilder {	
	
	/**Constructs a new {@link ClearChat} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link ClearChat} object.
	 * Make sure that the COMMAND of the message equals "CLEARCHAT"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link ClearChat}, or <code>null</code> if a {@link ClearChat} could not be created
	 */
	public ClearChat build(TwitchMessage message);
}
