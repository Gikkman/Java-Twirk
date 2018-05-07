package com.gikk.twirk.types.privmsg;

import com.gikk.twirk.types.AbstractEmoteMessage;
import com.gikk.twirk.types.cheer.Cheer;
import java.util.List;

/**
 *
 * @author Gikkman
 */
public interface PrivateMessage extends AbstractEmoteMessage{
        /**Tells whether this message was a cheer event or not.
     *
     * @return {@code true} if the message contains bits, <code>false</code> otherwise
     */
    public boolean isCheer();

    /**Fetches a list of all all cheers in this message. Each cheer is encapsulated in a {@link Cheer}.
	 * The list might be empty, if the message contained no cheers.
     *
	 * @return List of emotes (might be empty)
	 */
    public List<Cheer> getCheers();

    /**Fetches the total number of bits that this message contained.
     * This method is short-hand for:
     * <pre>
     * int bits = 0;
     * for(Cheer c : getCheers())
     *      bits += c.getBits();
     * return bits;
     * </pre>
     * @return number of bits
     */
    public int getBits();
}
