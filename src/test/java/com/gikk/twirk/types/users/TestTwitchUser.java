package com.gikk.twirk.types.users;

import com.gikk.twirk.TestBiConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 *
 * @author Gikkman
 */
public class TestTwitchUser {

    final static String USER_MESSAGE = "@badges=;color=#000000;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikktest :userMessage";
    final static String MOD_MESSAGE = "@badges=moderator/1;color=#FF69B4;display-name=GikkBot;emotes=;mod=1;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type=mod :gikkbot!gikkbot@gikkbot.tmi.twitch.tv PRIVMSG #gikkman :modMessage";
    final static String SUB_MESSAGE = "@badges=subscriber/1;color=#FF69B4;display-name=Gikktest;emotes=;mod=0;room-id=31974228;subscriber=1;turbo=0;user-id=27658385;user-type= :gikktest!gikktest@gikktest.tmi.twitch.tv PRIVMSG #gikkman :subMessage";
    final static String SUB_MOD_MESSAGE = "@badges=subscriber/1,moderator/1;color=#FF69B4;display-name=Gikktest;emotes=;mod=1;room-id=31974228;subscriber=1;turbo=0;user-id=27658385;user-type=mod :gikktest!gikktest@gikktest.tmi.twitch.tv PRIVMSG #gikkman :subMessage";
    final static String BROADCASTER_MESSAGE = "@badges=broadcaster/1;color=#FF69B4;display-name=Gikkman;emotes=4685:4-9,11-16/15614:18-24;mod=1;room-id=27658385;subscriber=0;turbo=0;user-id=27658385;user-type=mod :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :Yo! tmrHat tmrHat tmrToad";
    final static String TURBO_MESSAGE = "@badges=turbo/1;color=#1E90FF;display-name=Test;emotes=;mod=0;room-id=27787567;subscriber=0;turbo=1;user-id=27658386;user-type= :test!test@test.tmi.twitch.tv PRIVMSG #gikkman :turboMessage";

    public static void test(Consumer<String> twirkInput,
                            TestBiConsumer<TwitchUser, TwitchMessage> test)
        throws Exception {
        TestResult t1 = test.assign(TestTwitchUser::testRegularUser);
        twirkInput.accept(USER_MESSAGE);
        t1.await();

        TestResult t2 = test.assign(TestTwitchUser::testModUser);
        twirkInput.accept(MOD_MESSAGE);
        t2.await();

        TestResult t3 = test.assign(TestTwitchUser::testSubUser);
        twirkInput.accept(SUB_MESSAGE);
        t3.await();

        TestResult t4 = test.assign(TestTwitchUser::testSubModUser);
        twirkInput.accept(SUB_MOD_MESSAGE);
        t4.await();

        TestResult t5 = test.assign(TestTwitchUser::testOwnerUser);
        twirkInput.accept(BROADCASTER_MESSAGE);
        t5.await();

        TestResult t6 = test.assign(TestTwitchUser::testTurboUser);
        twirkInput.accept(TURBO_MESSAGE);
        t6.await();
    }

    private static boolean testRegularUser(TwitchUser user,
                                           TwitchMessage message) {
        boolean res = test(user, 27658385, "gikkman", "Gikkman", 0x000000,
                    USER_TYPE.DEFAULT, false, false, false, false);
        System.out.println("--- --- Regular User OK");
        return res;
    }

    private static boolean testModUser(TwitchUser user, TwitchMessage message) {
        boolean res = test(user, 27658385, "gikkbot", "GikkBot", 0xFF69B4,
                    USER_TYPE.MOD, false, true, false, false, "moderator/1");
        System.out.println("--- --- Mod User OK");
        return res;
    }

    private static boolean testSubUser(TwitchUser user, TwitchMessage message) {
        boolean res = test(user, 27658385, "gikktest", "Gikktest", 0xFF69B4,
                    USER_TYPE.SUBSCRIBER, true, false, false, false,
                    "subscriber/1");
        System.out.println("--- --- Sub User OK");
        return res;
    }

    private static boolean testSubModUser(TwitchUser user, TwitchMessage message) {
        boolean res = test(user, 27658385, "gikktest", "Gikktest", 0xFF69B4,
                    USER_TYPE.MOD, true, true, false, false, "subscriber/1",
                    "moderator/1");
        System.out.println("--- --- Sub & Mod User OK");
        return res;
    }

    private static boolean testOwnerUser(TwitchUser user, TwitchMessage message) {
        boolean res = test(user, 27658385, "gikkman", "Gikkman", 0xFF69B4,
                    USER_TYPE.OWNER, false, true, false, true, "broadcaster/1");
        System.out.println("--- --- Owner User OK");
        return res;
    }

    private static boolean testTurboUser(TwitchUser user, TwitchMessage message) {
        boolean res = test(user, 27658386, "test", "Test", 0x1E90FF,
                    USER_TYPE.SUBSCRIBER, false, false, true, false, "turbo/1");
        System.out.println("--- --- Turbo User OK");
        return res;
    }

    private static boolean test(TwitchUser user, int userID, String userName,
                                String displayName, int color,
                                USER_TYPE userType,
                                boolean isSub, boolean isMod, boolean isTurbo,
                                boolean isOwner,
                                String... badges) {

        assertArrayEquals("Badges", badges, user.getBadges());
        assertEquals("User ID", userID, user.getUserID());
        assertEquals("UserName", userName, user.getUserName());
        assertEquals("Display Name", displayName, user.getDisplayName());
        assertEquals("Color", color, user.getColor());

        assertEquals("User type", userType, user.getUserType());

        assertEquals("Is sub", isSub, user.isSub());
        assertEquals("Is mod", isMod, user.isMod());
        assertEquals("Is tubo", isTurbo, user.isTurbo());
        assertEquals("Is owner", isOwner, user.isOwner());

        assertEquals("Badge size", badges.length, user.getBadges().length);
        for (int i = 0; i < badges.length; i++) {
            String argBadge = badges[i];
            String usrBadge = user.getBadges()[i];
            assertEquals("Same badge", argBadge, usrBadge);
        }

        return true;
    }
}
