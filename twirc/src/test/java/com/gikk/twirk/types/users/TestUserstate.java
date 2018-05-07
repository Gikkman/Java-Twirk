package com.gikk.twirk.types.users;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.USER_TYPE;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestUserstate {
	private static final String USERSTATE_MOD = "@color=0x255;display-name=GikkBot;emote-sets=0;mod=1;subscriber=0;turbo=0;user-type=mod :tmi.twitch.tv USERSTATE #gikkman";
	private static final String USERSTATE_USER = "@color=0x1234;display-name=GikkBot;emote-sets=0;mod=0;subscriber=0;turbo=0;user-type= :tmi.twitch.tv USERSTATE #gikkman";
	private static final String USERSTATE_OWNER = "@color=#FF69B4;display-name=Gikkman;emote-sets=0,1454;mod=1;subscriber=0;turbo=0;user-type=mod :tmi.twitch.tv USERSTATE #gikkman";

    public static void test(Consumer<String> twirkIn, TestConsumer<Userstate> userstateTest) throws Exception {
        TestResult t1 = userstateTest.assign(TestUserstate::testUserUserstate);
        twirkIn.accept(USERSTATE_USER);
        assertTrue(t1.await());

        TestResult t2 = userstateTest.assign(TestUserstate::testModUserstate);
        twirkIn.accept(USERSTATE_MOD);
        assertTrue(t2.await());

        TestResult t3 = userstateTest.assign(TestUserstate::testOwnerUserstate);
        twirkIn.accept(USERSTATE_OWNER);
        assertTrue(t3.await());
    }

    private static boolean testUserUserstate(Userstate us) {
        doTest(us, "GikkBot", 0x1234, USER_TYPE.DEFAULT, false, false, false, 0);
        System.out.println("--- --- Regular User OK");
        return true;
    }

    private static boolean testModUserstate(Userstate us) {
        doTest(us, "GikkBot", 0x255, USER_TYPE.MOD, false, true, false, 0);
        System.out.println("--- --- Mod User OK");
        return true;
    }

    private static boolean testOwnerUserstate(Userstate us) {
        doTest(us, "Gikkman", 0xFF69B4, USER_TYPE.OWNER, false, true, false, 0, 1454);
        System.out.println("--- --- Owner User OK");
        return true;
    }


	private static void doTest(Userstate state, String displayName, int color,
            USER_TYPE UserType, boolean isSub, boolean isMod, boolean isTubo, int... emoteSets) {
        assertEquals( "Color match", color, state.getColor() );
		assertEquals( "Display name", displayName, state.getDisplayName());
		assertEquals( "User type", UserType, state.getUserType() );
        assertEquals( "Is sub", isSub, state.isSub());
        assertEquals( "Is mod", isMod, state.isMod());
        assertEquals( "Is turbo", isTubo, state.isTurbo());
        assertArrayEquals("Emote sets", emoteSets, state.getEmoteSets());
	}
}
