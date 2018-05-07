package com.gikk.twirk.types.twitchMessage;

import com.gikk.twirk.types.AbstractEmoteMessage;
import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.cheer.Cheer;
import java.util.List;

/**An encapsulation of a message sent via Twitch chat. <br><br>
 *
 * A message can have many different structures, but since they are based on the
 * IRC protocol, they will have 1 to 5 different segments, each segment separated
 * with a space (the last segment, the actual message, may contain any amount of spaces).
 * <ul>
 * <li>Tag - This segment is not always present. It is Twitch's own specialized segment which contains data relevant
 * to the message in questing. Example of content is UserColor, Emotes and such. The order which elements appear in a Tag
 * is not guaranteed. A Tag always begins with a @
 * <li>Prefix - This segment usually contains knowledge about the sender of the message.
 * <li>Command - This segment usually specifies what command the message issued
 * <li>Target - This segment tells about whom the message is targeted at (a person, a channel)
 * <li>Content - This segment contains the actual text, intended for the receiver to read.
 * </ul>
 * In some cases, the message will contain less segments than 5. In that case that a segments
 * aren't present, their content will be empty.<br><br>
 *
 * @author Gikkman
 *
 */
public interface TwitchMessage extends AbstractEmoteMessage {

	/**Retrieves this message's tag segment. Should always starts with a @. Note that the
	 * Tag segment will look vastly different for different Twitch commands, and some won't
	 * even have a tag.
	 *
	 * @return The tag segment, or {@code ""} if no tag was present
	 */
	public String getTag();

	/**Retrieves the message's prefix.<br><br>
	 *
	 * A typical Twitch message looks something line this:<br>
	 * <code>:name!name@name.tmi.twitch.tv COMMAND #channel :message</code> <br><br>
	 *
	 * The prefix is the part before the first space. It usually only contains data about the sender of
	 * the message, but it might sometimes contain other information (PING messages for example just have PING as
	 * their prefix).<br><br>
	 *
	 * @return The messages prefix
	 */
	public String getPrefix();

	/**Retrieves the message's command.<br>
	 * The command tells us what action triggered this message being sent.<br><br>
	 *
	 * @return The message's command
	 */
	public String getCommand();

	/**Retrieves the message's target.<br>
	 * The target tells us whom the message is intended towards. It can be a user,
     * a channel, or something else (though mostly the channel).
     * <p>
	 * This segment is not always present.
	 *
	 * @return The message's target, or {@code ""} if no target
	 */
	public String getTarget();

	/**Retrieves the message's content.<br>
	 * The content is the commands parameters. Most often, this is the actual chat message, but it may be many
	 * other things for other commands.<br><br>
	 *
	 * This segment is not always present.
	 *
	 * @return The message's content, or {@code ""} if no content
	 */
	public String getContent();

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

    /**Fetches the {@link TagPerser} that has parsed the message's tag into a key-value map.
     * For a list of different identifiers that can occur in a tag, check the {@link TagIdentifiers} class.
     *
     */
    public TagMap getTagMap();
}
