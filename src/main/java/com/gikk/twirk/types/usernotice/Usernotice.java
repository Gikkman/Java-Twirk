package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.AbstractEmoteMessage;
import com.gikk.twirk.types.usernotice.subtype.Raid;
import com.gikk.twirk.types.usernotice.subtype.Ritual;
import com.gikk.twirk.types.usernotice.subtype.Subscription;
import java.util.Optional;

/**Class for representing the Usernotice event. Usernotices can fire under 4
 * different conditions:
 * <ul>
 * <li> New subscription & Resubscription
 * <li> Raid
 * <li> Ritual
 * </ul>
 * Since this class might contain 3 different event types, the getSubscription(),
 * getRaid() and getRitual methods all return {@link Optional} objects.
 *
 * @author Gikkman
 */
public interface Usernotice extends AbstractEmoteMessage {

    /**Retrieves the message, as input by the user. This message might contain
     * emotes (see {@link #hasEmotes()} and {@link #getEmotes()}.
	 * It might also be empty, in which case, the message is {@code ""}
	 *
	 * @return The message. Might be {@code ""}
	 */
	public String getMessage();

	/**The system message contains the text which'll be printed in the Twitch
     * Chat on Twitch's side. It might look like this: <br>
	 * {@code system-msg=TWITCH_UserName\shas\ssubscribed\sfor\s6\smonths!;}.
     * <p>
	 * This is basically the message printed in chat to tell other viewers
     * what just occred (a raid, a subscription and so on).
     * <p>
	 * This method returns the system message like it looks from server side.
     * It will, however, replace all {@code \s} with spaces.
	 *
	 * @return The system message
	 */
	public String getSystemMessage();

    /**Tells whether this Usernotice is a subscription (or re-subscription) alert.
     *
     * @return {@code true} if this is a subscription alert
     */
    public boolean isSubscription();

    /**Retrieves the Subscription object. The Optional object wraps the
     * Subscription object (as an alternative to returning {@code null}).
     *
     * @return the Subscription
     */
    public Optional<Subscription> getSubscription();

    /**Tells whether this Usernotice is a raid alert.
     *
     * @return {@code true} if this is a raid alert
     */
    public boolean isRaid();

    /**Retrieves the Raid object. The Optional object wraps the
     * Raid object (as an alternative to returning {@code null}).
     *
     * @return the Raid
     */
    public Optional<Raid> getRaid();

    /**Tells whether this Usernotice is a ritual alert.
     *
     * @return {@code true} if this is a ritual alert
     */
    public boolean isRitual();

    /**Retrieves the Ritual object. The Optional object wraps the
     * Ritual object (as an alternative to returning {@code null}).
     *
     * @return the Ritual
     */
    public Optional<Ritual> getRitual();
}
