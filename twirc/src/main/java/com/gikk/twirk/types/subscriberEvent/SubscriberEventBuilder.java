package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link SubscriberEvent} object. To create a {@link SubscriberEvent} object, call the {@link #build(TwitchMessage)} method
 * 
 * @author Gikkman
 *
 */
public interface SubscriberEventBuilder {
	
	/**Constructs a new {@link SubscriberEvent} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link SubscriberEvent} object.
	 * Make sure that the PREFIX of the message starts with ":twitchnotify!"
	 * 
	 * @param message The message we received from Twitch
	 * @return A {@link SubscriberEvent}, or <code>null</code> if a {@link SubscriberEvent} could not be created
	 */
	public SubscriberEvent build(TwitchMessage message);
}	
