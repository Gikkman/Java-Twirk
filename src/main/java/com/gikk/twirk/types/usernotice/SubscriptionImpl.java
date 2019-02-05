package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.Subscription;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionPlan;
import java.util.Optional;

/**
 *
 * @author Gikkman
 */
class SubscriptionImpl implements Subscription {

    private final SubscriptionPlan plan;
    private final int months;
    private final int streak;
    private final boolean shareStreak;
    private final String planName;
    private final Optional<SubscriptionGift> subGift;

    SubscriptionImpl(SubscriptionPlan plan, int months, int streak, boolean sharedStreak, String planName, SubscriptionGift subGift) {
        this.plan = plan;
        this.months = months;
        this.streak = streak;
        this.shareStreak = sharedStreak;
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
    public int getStreak() { return streak; }

    @Override
    public boolean isSharedStreak() { return shareStreak; }

    @Override
    public String getSubscriptionPlanName() {
        return planName;
    }

    @Override
    public boolean isResub() {
        return months > 1;
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
