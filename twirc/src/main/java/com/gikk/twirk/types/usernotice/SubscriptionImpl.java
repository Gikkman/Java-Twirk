package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.Subscription;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionPlan;
import java.util.Optional;

/**
 *
 * @author Gikkman
 */
class SubscriptionImpl implements Subscription{

    private final SubscriptionPlan plan;
    private final int months;
    private final String planName;
    private final Optional<SubscriptionGift> subGift;

    SubscriptionImpl(SubscriptionPlan plan, int months, String planName, SubscriptionGift subGift) {
        this.plan = plan;
        this.months = months;
        this.planName = planName;
        this.subGift = Optional.ofNullable(subGift);
    }

    @Override
    public SubscriptionPlan getSubscriptionPlan() {
        return plan;
    }

    @Override
    public int getMonths() {
        return months;
    }

    @Override
    public String getSubscriptionPlanName() {
        return planName;
    }

    @Override
    public boolean isResub() {
        return months > 0;
    }

    @Override
    public boolean isGift() {
        return subGift.isPresent();
    }

    @Override
    public Optional<SubscriptionGift> getSubscriptionGift() {
        return subGift;
    }
}
