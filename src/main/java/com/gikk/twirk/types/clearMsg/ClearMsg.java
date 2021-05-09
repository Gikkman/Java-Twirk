package com.gikk.twirk.types.clearMsg;

import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Class for representing a CLEARMSG from Twitch.<br><br>
 *
 * A CLEARMSG means that a user's message was individually deleted by a moderator.
 * <br><br>
 * @author Casterlabs
 */
public interface ClearMsg extends AbstractType{
	/** Retrieves the message sender's username
	 *
	 * @return Message sender's username
	 */
	public String getSender();

	/** Retrieves the message id of the removed message. 
	 *  This matches the received message id returned on {@link TwitchMessage#getMessageID()}
	 * 
	 * @return The message id
	 */
	public String getTargetMsgId();

	/** Retrieves the contents of the removed message.
	 *
	 * @return The message contents
	 */
	public String getTargetMessageContent();
}
