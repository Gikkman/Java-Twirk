package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;

/**
 *
 * @author Gikkman
 */
class SubscriptionGiftImpl implements SubscriptionGift{
    private final String recipiantDisplayName;
    private final long RecipiantUserID;

    public SubscriptionGiftImpl(String recipiantDisplayName, long RecipiantUserID) {
        this.recipiantDisplayName = recipiantDisplayName;
        this.RecipiantUserID = RecipiantUserID;
    }

    @Override
    public String getRecipiantDisplayName() {
        return recipiantDisplayName;
    }

    @Override
    public long getRecipiantUserID() {
        return RecipiantUserID;
    }

}
