package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;

/**
 *
 * @author Gikkman
 */
class SubscriptionGiftImpl implements SubscriptionGift{
    private final String recipiantUserName;
    private final String recipiantDisplayName;
    private final long RecipiantUserID;

    public SubscriptionGiftImpl(String recipientUserName, String recipiantDisplayName, long RecipiantUserID) {
        this.recipiantUserName = recipientUserName;
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

    @Override
    public String getRecipiantUserName() {
        return recipiantUserName;
    }

}
