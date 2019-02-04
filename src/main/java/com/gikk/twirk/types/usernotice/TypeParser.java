package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;
import com.gikk.twirk.types.usernotice.subtype.Raid;
import com.gikk.twirk.types.usernotice.subtype.Ritual;
import com.gikk.twirk.types.usernotice.subtype.Subscription;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionPlan;

/**Class for parsing the different types of Usernotices
 *
 * @author Gikkman
 */
class TypeParser {
    static Raid parseRaid(TagMap map){
        String displayName = map.getAsString(TwitchTags.PARAM_DISPLAY_NAME);
        String loginName = map.getAsString(TwitchTags.PARAM_LOGIN_NAME);
        int count = map.getAsInt(TwitchTags.PARAM_VIEWER_COUNT);
        return new RaidImpl(displayName, loginName, count);
    }

    static Subscription parseSubscription(TagMap map){
        // New subs doesn't send the number of consecutive months, so I though
        // 1 month made sense (since the first re-sub begins at 2).
        int months = map.getAsInt(TwitchTags.PARAM_MONTHS);
        int streak = 0;
        boolean shareStreak = map.getAsBoolean(TwitchTags.PARAM_SHARE_STREAK);
        if(shareStreak) {
            streak = map.getAsInt(TwitchTags.PARAM_STREAK);
        }
        if(months <= 0) {
            months = 1;
        }

        String plan = map.getAsString(TwitchTags.PARAM_SUB_PLAN);
        SubscriptionPlan subPlan = SubscriptionPlan.of(plan);
        String planName = map.getAsString(TwitchTags.PARAM_SUB_PLAN_NAME);

        SubscriptionGift sg = parseSubscriptionGift(map);

        return new SubscriptionImpl(subPlan, months, streak, shareStreak, planName, sg);
    }

    static Ritual parseRitual(TagMap map){
        String ritualName = map.getAsString(TwitchTags.PARAM_RITUAL_NAME);
        return new RitualImpl(ritualName);
    }

    private static SubscriptionGift parseSubscriptionGift(TagMap map) {
        if(!map.containsKey(TwitchTags.PARAM_RECIPIANT_ID)) {
            return null;
        }
        long receiverID = map.getAsLong(TwitchTags.PARAM_RECIPIANT_ID);
        String recipientDisplayName = map.getAsString(TwitchTags.PARAM_RECIPIANT_DISPLAY_NAME);
        String recipientUserName = map.getAsString(TwitchTags.PARAM_RECIPIANT_NAME);
        return new SubscriptionGiftImpl(recipientUserName, recipientDisplayName, receiverID);
    }
}
