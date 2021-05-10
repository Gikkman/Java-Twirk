package com.gikk.twirk.types.clearMsg;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link ClearMsg} object. To create a {@link ClearMsg} object, call the {@link #build(TwitchMessage)} method
 *
 * @author Casterlabs
 *
 */
public interface ClearMsgBuilder {

	/**Constructs a new {@link ClearMsg} object from a {@link TwitchMessage}, received from Twitch. The user
	 * is responsible for making sure that this message can actually be made into a {@link ClearMsg} object.
	 * Make sure that the COMMAND of the message equals "CLEARMSG"
	 *
	 * @param message The message we received from Twitch
	 * @return A {@link ClearMsg}, or <code>null</code> if a {@link ClearMsg} could not be created
	 */
	public ClearMsg build(TwitchMessage message);

    static ClearMsgBuilder getDefault(){
        return new DefaultClearMsgBuilder();
    }
}
