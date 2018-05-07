package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.TestBiConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.usernotice.subtype.Raid;
import com.gikk.twirk.types.usernotice.subtype.Ritual;
import com.gikk.twirk.types.usernotice.subtype.Subscription;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionGift;
import com.gikk.twirk.types.usernotice.subtype.SubscriptionPlan;
import com.gikk.twirk.types.users.TwitchUser;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestUsernotice {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private static final String SUB   = "@badges=subscriber/0;color=#FF69B4;display-name=Gikkman;emotes=;id=f387e568-9e37-479e-aa35-7ed178b0a973;login=gikkman;mod=1;msg-id=sub;msg-param-sub-plan-name=Channel\\sSubscription\\s(test);msg-param-sub-plan=2000;room-id=1337;subscriber=1;system-msg=Gikkman\\sjust\\ssubscribed\\swith\\sa\\s$9.99\\ssub!;tmi-sent-ts=1511990167177;turbo=0;user-id=123;user-type= :tmi.twitch.tv USERNOTICE #test :Oh shit!";
	private static final String RESUB = "@badges=subscriber/0,premium/1;color=#FF69B4;display-name=Gikkman;emotes=;id=f387e568-9e37-479e-aa35-7ed178b0a973;login=gikkman;mod=0;msg-id=resub;msg-param-months=6;msg-param-sub-plan=Prime;msg-param-sub-plan-name=Prime;room-id=1337;subscriber=1;system-msg=Gikkman\\sjust\\ssubscribed\\swith\\sa\\s$4.99\\ssub.\\sGikkman\\ssubscribed\\sfor\\s6\\smonths\\sin\\sa\\srow!;tmi-sent-ts=1511990167178;turbo=0;user-id=123;user-type=staff :tmi.twitch.tv USERNOTICE #test :Oh shit 6 super months with no emote";
    private static final String SUB_GIFT = "@badges=moderator/1,subscriber/12;color=#0044CC;display-name=;emotes=;id=3863d53d-371b-4cab-8054-b4b0f0484f90;login=salt;mod=1;msg-id=subgift;msg-param-months=1;msg-param-recipient-display-name=xxworlddragonxx;msg-param-recipient-id=2;msg-param-recipient-user-name=xxworlddragonxx;msg-param-sub-plan-name=Low\\sTier\\sMemes\\sSquad;msg-param-sub-plan=1000;room-id=12928129;subscriber=1;system-msg=Salt\\sgifted\\sa\\s$4.99\\ssub\\sto\\sxxworlddragonxx!;tmi-sent-ts=1510777884798;turbo=0;user-id=1;user-type=mod :tmi.twitch.tv USERNOTICE #test";

    private static final String RAID = "@badges=turbo/1;color=#9ACD32;display-name=TestChannel;emotes=;id=3d830f12-795c-447d-af3c-ea05e40fbddb;login=testchannel;mod=0;msg-id=raid;msg-param-displayName=TestChannel;msg-param-login=testchannel;msg-param-viewerCount=15;room-id=56379257;subscriber=0;system-msg=15\\sraiders\\sfrom\\sTestChannel\\shave\\sjoined!;tmi-sent-ts=1507246572675;turbo=1;user-id=123456;user-type= :tmi.twitch.tv USERNOTICE #othertestchannel";
    private static final String RITUAL = "@badges=;color=;display-name=SevenTest1;emotes=30259:0-6;id=37feed0f-b9c7-4c3a-b475-21c6c6d21c3d;login=seventest1;mod=0;msg-id=ritual;msg-param-ritual-name=new_chatter;room-id=6316121;subscriber=0;system-msg=Seventoes\\sis\\snew\\shere!;tmi-sent-ts=1508363903826;turbo=0;user-id=131260580;user-type= :tmi.twitch.tv USERNOTICE #seventoes :HeyGuys";

    private static final String RONNI_PRIME_SUB = "@badges=staff/1,broadcaster/1,turbo/1;color=#008000;display-name=ronni;emotes=;id=db25007f-7a18-43eb-9379-80131e44d633;login=ronni;mod=0;msg-id=resub;msg-param-months=6;msg-param-sub-plan=Prime;msg-param-sub-plan-name=Prime;room-id=1337;subscriber=1;system-msg=ronni\\shas\\ssubscribed\\sfor\\s6\\smonths!;tmi-sent-ts=1507246572675;turbo=1;user-id=1337;user-type=staff :tmi.twitch.tv USERNOTICE #dallas :Great stream -- keep it up!";
    private static final String GIFT_SUB_COMPLETE = "@badges=staff/1,premium/1;color=#0000FF;display-name=TWW2;emotes=;id=e9176cd8-5e22-4684-ad40-ce53c2561c5e;login=tww2;mod=0;msg-id=subgift;msg-param-months=1;msg-param-recipient-display-name=Mr_Woodchuck;msg-param-recipient-id=89614178;msg-param-recipient-name=mr_woodchuck;msg-param-sub-plan-name=House\\sof\\sNyoro~n;msg-param-sub-plan=1000;room-id=19571752;subscriber=0;system-msg=TWW2\\sgifted\\sa\\sTier\\s1\\ssub\\sto\\sMr_Woodchuck!;tmi-sent-ts=1521159445153;turbo=0;user-id=13405587;user-type=staff :tmi.twitch.tv USERNOTICE #forstycup";

    public static void test(Consumer<String> twirkInput, TestBiConsumer<TwitchUser, Usernotice> test ) throws Exception{
        TestResult resSub = test.assign(TestUsernotice::subTest);
        twirkInput.accept(SUB);
        resSub.await();

        TestResult resResub = test.assign(TestUsernotice::resubTest);
        twirkInput.accept(RESUB);
        resResub.await();

        TestResult resSubGift = test.assign(TestUsernotice::subGiftTest);
        twirkInput.accept(SUB_GIFT);
        resSubGift.await();

        TestResult raidTest = test.assign(TestUsernotice::raidTest);
        twirkInput.accept(RAID);
        raidTest.await();

        TestResult ritualTest = test.assign(TestUsernotice::ritualTest);
        twirkInput.accept(RITUAL);
        ritualTest.await();

        TestResult primeTest = test.assign(TestUsernotice::primeTest);
        twirkInput.accept(RONNI_PRIME_SUB);
        primeTest.await();

        TestResult subGiftCompleteTest = test.assign(TestUsernotice::subGiftCompleteTest);
        twirkInput.accept(GIFT_SUB_COMPLETE);
        subGiftCompleteTest.await();
    }

    private static boolean subTest(TwitchUser user, Usernotice notice){
        assertEquals(SUB, notice.getRaw());
        assertEquals("Oh shit!", notice.getMessage());
        assertEquals("f387e568-9e37-479e-aa35-7ed178b0a973", notice.getMessageID());
        assertEquals(1337, notice.getRoomID());
        assertEquals(1511990167177L, notice.getSentTimestamp());
        assertEquals("Gikkman just subscribed with a $9.99 sub!", notice.getSystemMessage());

        assertEquals(Optional.empty(), notice.getRitual());
        assertEquals(Optional.empty(), notice.getRaid());

        Subscription s = notice.getSubscription().get();
        assertEquals(1, s.getMonths());
        assertEquals(SubscriptionPlan.SUB_MID, s.getSubscriptionPlan());
        assertEquals("Channel Subscription (test)", s.getSubscriptionPlanName());
        assertFalse(s.isGift());
        assertFalse(s.isResub());

        System.err.println("--- --- Sub test OK");
        return true;
    }

    private static boolean resubTest(TwitchUser user, Usernotice notice){
        assertEquals(RESUB, notice.getRaw());
        assertEquals("Oh shit 6 super months with no emote", notice.getMessage());
        assertEquals("f387e568-9e37-479e-aa35-7ed178b0a973", notice.getMessageID());
        assertEquals(1337, notice.getRoomID());
        assertEquals(1511990167178L, notice.getSentTimestamp());
        assertEquals("Gikkman just subscribed with a $4.99 sub. Gikkman subscribed for 6 months in a row!", notice.getSystemMessage());

        assertEquals(Optional.empty(), notice.getRitual());
        assertEquals(Optional.empty(), notice.getRaid());

        Subscription s = notice.getSubscription().get();
        assertEquals(6, s.getMonths());
        assertEquals(SubscriptionPlan.SUB_PRIME, s.getSubscriptionPlan());
        assertEquals("Prime", s.getSubscriptionPlanName());
        assertFalse(s.isGift());
        assertTrue(s.isResub());
        assertEquals(s.getSubscriptionGift(), Optional.empty());

        System.err.println("--- --- Resub test OK");
        return true;
    }

    private static boolean subGiftTest(TwitchUser user, Usernotice notice){
        assertEquals(SUB_GIFT, notice.getRaw());
        assertEquals("", notice.getMessage());
        assertEquals("3863d53d-371b-4cab-8054-b4b0f0484f90", notice.getMessageID());
        assertEquals(12928129, notice.getRoomID());
        assertEquals(1510777884798L, notice.getSentTimestamp());
        assertEquals("Salt gifted a $4.99 sub to xxworlddragonxx!", notice.getSystemMessage());

        assertEquals(Optional.empty(), notice.getRitual());
        assertEquals(Optional.empty(), notice.getRaid());

        Subscription s = notice.getSubscription().get();
        assertEquals(1, s.getMonths());
        assertEquals(SubscriptionPlan.SUB_LOW, s.getSubscriptionPlan());
        assertEquals("Low Tier Memes Squad", s.getSubscriptionPlanName());
        assertTrue(s.isGift());
        assertFalse(s.isResub());

        SubscriptionGift sg = s.getSubscriptionGift().get();
        assertEquals("xxworlddragonxx", sg.getRecipiantDisplayName());
        assertEquals(2, sg.getRecipiantUserID());

        System.err.println("--- --- Sub gift test OK");
        return true;
    }

    private static boolean raidTest(TwitchUser user, Usernotice notice){
        assertEquals(RAID, notice.getRaw());
        assertEquals("", notice.getMessage());
        assertEquals("3d830f12-795c-447d-af3c-ea05e40fbddb", notice.getMessageID());
        assertEquals(56379257, notice.getRoomID());
        assertEquals(1507246572675L, notice.getSentTimestamp());
        assertEquals("15 raiders from TestChannel have joined!", notice.getSystemMessage());

        assertEquals(Optional.empty(), notice.getRitual());
        assertEquals(Optional.empty(), notice.getSubscription());

        Raid r = notice.getRaid().get();
        assertEquals(15, r.getRaidCount());
        assertEquals("TestChannel", r.getSourceDisplayName());
        assertEquals("testchannel", r.getSourceLoginName());

        System.err.println("--- --- Raid test OK");

        return true;
    }

    private static boolean ritualTest(TwitchUser user, Usernotice notice){
        assertEquals(RITUAL, notice.getRaw());
        assertEquals("HeyGuys", notice.getMessage());
        assertEquals("37feed0f-b9c7-4c3a-b475-21c6c6d21c3d", notice.getMessageID());
        assertEquals(6316121, notice.getRoomID());
        assertEquals(1508363903826L, notice.getSentTimestamp());
        assertEquals("Seventoes is new here!", notice.getSystemMessage());

        assertEquals(Optional.empty(), notice.getRaid());
        assertEquals(Optional.empty(), notice.getSubscription());

        Ritual r = notice.getRitual().get();
        assertEquals("new_chatter", r.getRitualName());

        System.err.println("--- --- Ritual test OK");

        return true;
    }

    private static boolean primeTest(TwitchUser user, Usernotice notice){
        assertEquals("Great stream -- keep it up!", notice.getMessage());
        assertEquals(new ArrayList<>(), notice.getEmotes());
        assertEquals("db25007f-7a18-43eb-9379-80131e44d633", notice.getMessageID());
        assertEquals(1337, notice.getRoomID());
        assertEquals(1507246572675L, notice.getSentTimestamp());
        assertEquals("ronni has subscribed for 6 months!", notice.getSystemMessage());

        assertTrue(notice.isSubscription());
        assertFalse(notice.isRaid());
        assertFalse(notice.isRitual());

        assertEquals(Optional.empty(), notice.getRaid());
        assertEquals(Optional.empty(), notice.getRitual());

        Subscription s = notice.getSubscription().orElseThrow(() -> new RuntimeException("Subscription missing"));
        assertEquals(6, s.getMonths());
        assertEquals(SubscriptionPlan.SUB_PRIME, s.getSubscriptionPlan());
        assertEquals("Prime", s.getSubscriptionPlanName());

        assertTrue(s.isResub());
        assertFalse(s.isGift());

        System.err.println("--- --- Prime Sub test OK");

        return true;
    }

    private static boolean subGiftCompleteTest(TwitchUser user, Usernotice notice){
        assertArrayEquals(new String[] {"staff/1", "premium/1"}, user.getBadges());
        assertEquals("tww2", user.getUserName());
        assertEquals("TWW2", user.getDisplayName());
        assertEquals(13405587, user.getUserID());
        assertEquals(USER_TYPE.STAFF, user.getUserType());
        assertFalse(user.isMod());
        assertFalse(user.isSub());
        assertFalse(user.isTurbo());

        assertEquals("", notice.getMessage());
        assertEquals(new ArrayList<>(), notice.getEmotes());
        assertEquals("e9176cd8-5e22-4684-ad40-ce53c2561c5e", notice.getMessageID());
        assertEquals(19571752, notice.getRoomID());
        assertEquals(1521159445153L, notice.getSentTimestamp());
        assertEquals("TWW2 gifted a Tier 1 sub to Mr_Woodchuck!", notice.getSystemMessage());

        assertTrue(notice.isSubscription());
        assertFalse(notice.isRaid());
        assertFalse(notice.isRitual());

        assertEquals(Optional.empty(), notice.getRaid());
        assertEquals(Optional.empty(), notice.getRitual());

        Subscription s = notice.getSubscription().orElseThrow(() -> new RuntimeException("Subscription missing"));
        assertEquals(1, s.getMonths());
        assertFalse(s.isResub());

        assertEquals(SubscriptionPlan.SUB_LOW, s.getSubscriptionPlan());
        assertEquals("House of Nyoro~n", s.getSubscriptionPlanName());

        assertTrue(s.isGift());
        SubscriptionGift sg = s.getSubscriptionGift().orElseThrow(() -> new RuntimeException("Sub Gift Missing"));
        assertEquals("Mr_Woodchuck", sg.getRecipiantDisplayName());
        assertEquals("mr_woodchuck", sg.getRecipiantUserName());
        assertEquals(89614178, sg.getRecipiantUserID());


        System.err.println("--- --- Prime Sub test OK");

        return true;
    }
}
