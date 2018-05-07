package com.gikk.twirk.types;

import com.gikk.twirk.types.emote.Emote;
import java.util.List;

/**Interface for chat messages that might contain emotes
 *
 * @author Gikkman
 */
public interface AbstractEmoteMessage extends AbstractType{

    /**Checks whether this message contains emotes or not
     *
     * @return {@code true} if the message contains emotes
     */
    public boolean hasEmotes();

	/**Fetches a list of all all emotes in this message. Each emote is encapsulated in a {@link Emote}.
	 * The list might be empty, if the message contained no emotes.
	 *
	 * @return List of emotes (might be empty)
	 */
	public List<Emote> getEmotes();

    /**Fetches the timestamp of when the message reached the Twitch message server.
     *
     * @return UNIX timestap, or -1 (if none was present)
     */
    public long getSentTimestamp();

    /**Fetches the chat room's unique ID.
     *
     * @return the room ID.
     */
    public long getRoomID();

    /**Fetches the message's unique ID.
     *
     * @return the message ID.
     */
    public String getMessageID();
}
