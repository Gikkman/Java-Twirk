package com.gikk.twirk.types.usernotice.subtype;

import java.util.Optional;

/**Interface for representing a Subscription (or re-subscription) event.
 *
 * @author Gikkman
 */
public interface Subscription {
    public SubscriptionPlan getSubscriptionPlan();

    /**Get the number of month's the subscription's been active. New subscriptions
     * return 1, whilst re-subscriptions tells which month was just activated.
     *
     * @return number of months
     */
    public int getMonths();

    /**Returns the name of the purchased subscription plan.  This may be a
     * default name or one created by the channel owner.
     *
     * @return display name of the subscription plan
     */
    public String getSubscriptionPlanName();

    /**Returns whether this was a new sub or a re-sub.
     *
     * @return {@code true} if this was a re-subscription
     */
    public boolean isResub();

    /**Tells whether this is a subscription gift or not.
     *
     * @return {@code true} if this was a gift
     */
    public boolean isGift();

    /**Retrieves the SubscriptionGift item, which holds information about the
     * gift and the recipiant of the gift. The Optional object wraps the
     * SubscriptionGift object (as an alternative to returning {@code null}).
     *
     * @return the subscription gift, wrapped in a {@link Optional}
     */
    public Optional<SubscriptionGift> getSubscriptionGift();
}
