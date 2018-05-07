package com.gikk.twirk.types.usernotice.subtype;

/** Interface for representing a Gifted Subscription event.
 *
 * @author Gikkman
 */
public interface SubscriptionGift {
    /**
     * Fetches the recipients display name (as configured by the user).
     * @return the display name
     */
    public String getRecipiantDisplayName();

    /**
     * Fetches the recipients user name (i.e. their login name).
     * @return the user name
     */
    public String getRecipiantUserName();

    /**
     *Fetches the recipients user ID
     * @return the user ID
     */
    public long getRecipiantUserID();
}
