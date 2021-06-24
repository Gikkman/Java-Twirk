package com.gikk.twirk.types.roomstate;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestRoomstate {

  private final static String JOIN = "@broadcaster-lang=;emote-only=0;r9k=0;slow=0;subs-only=0;followers-only=-1 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String SUB_MODE_ON_MESSAGE = "@subs-only=1 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String SUB_MODE_OFF_MESSAGE = "@subs-only=0 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String R9K_MODE_ON_MESSAGE = "@r9k=1 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String R9K_MODE_OFF_MESSAGE = "@r9k=0 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String SLOW_MODE_120_MESSAGE = "@slow=120 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String SLOW_MODE_OFF_MESSAGE = "@slow=0 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String FOLLOWERS_MODE_ON_MESSAGE = "@followers-only=0 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String FOLLOWERS_MODE_OFF_MESSAGE = "@followers-only=-1 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String EMOTE_ONLY_MODE_ON_MESSAGE = "@emote-only=1 :tmi.twitch.tv ROOMSTATE #gikkman";
  private final static String EMOTE_ONLY_MODE_OFF_MESSAGE = "@emote-only=0 :tmi.twitch.tv ROOMSTATE #gikkman";

  public static void test(Consumer<String> twirkIn, TestConsumer<Roomstate> roomstateTest) throws Exception {
    TestResult t1 = roomstateTest.assign(TestRoomstate::testJoin);
    twirkIn.accept(JOIN);
    t1.await();

    TestResult t2 = roomstateTest.assign(TestRoomstate::testSubModeOn);
    twirkIn.accept(SUB_MODE_ON_MESSAGE);
    t2.await();

    TestResult t3 = roomstateTest.assign(TestRoomstate::testSubModeOff);
    twirkIn.accept(SUB_MODE_OFF_MESSAGE);
    t3.await();

    TestResult t4 = roomstateTest.assign(TestRoomstate::testR9Kon);
    twirkIn.accept(R9K_MODE_ON_MESSAGE);
    t4.await();

    TestResult t5 = roomstateTest.assign(TestRoomstate::testR9Koff);
    twirkIn.accept(R9K_MODE_OFF_MESSAGE);
    t5.await();

    TestResult t6 = roomstateTest.assign(TestRoomstate::testSlow120);
    twirkIn.accept(SLOW_MODE_120_MESSAGE);
    t6.await();

    TestResult t7 = roomstateTest.assign(TestRoomstate::testSlowOff);
    twirkIn.accept(SLOW_MODE_OFF_MESSAGE);
    t7.await();

    TestResult t8 = roomstateTest.assign(TestRoomstate::testFollowersModeOn);
    twirkIn.accept(FOLLOWERS_MODE_ON_MESSAGE);
    t8.await();

    TestResult t9 = roomstateTest.assign(TestRoomstate::testFollowersModeOff);
    twirkIn.accept(FOLLOWERS_MODE_OFF_MESSAGE);
    t9.await();

    TestResult t10 = roomstateTest.assign(TestRoomstate::testEmoteOnlyModeOn);
    twirkIn.accept(EMOTE_ONLY_MODE_ON_MESSAGE);
    t10.await();

    TestResult t11 = roomstateTest.assign(TestRoomstate::testEmoteOnlyModeOff);
    twirkIn.accept(EMOTE_ONLY_MODE_OFF_MESSAGE);
    t11.await();
  }

  private static boolean testJoin(Roomstate state) {
    boolean res = doTest(state, "", 0, 0, 0, -1, 0);
    System.out.println("--- --- Join OK");
    return res;
  }

  private static boolean testSubModeOn(Roomstate state) {
    boolean res = doTest(state, "", -1, 1, -1, -1, -1);
    System.out.println("--- --- Sub Mode ON OK");
    return res;
  }

  private static boolean testSubModeOff(Roomstate state) {
    boolean res = doTest(state, "", -1, 0, -1, -1, -1);
    System.out.println("--- --- Sub Mode OFF OK");
    return res;
  }

  private static boolean testR9Kon(Roomstate state) {
    boolean res = doTest(state, "", 1, -1, -1, -1, -1);
    System.out.println("--- --- R9K Mode ON OK");
    return res;
  }

  private static boolean testR9Koff(Roomstate state) {
    boolean res = doTest(state, "", 0, -1, -1, -1, -1);
    System.out.println("--- --- R9K Mode OFF OK");
    return res;
  }

  private static boolean testSlow120(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, 120, -1, -1);
    System.out.println("--- --- Slow Mode ON OK");
    return res;
  }

  private static boolean testSlowOff(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, 0, -1, -1);
    System.out.println("--- --- Slow Mode OFF OK");
    return res;
  }

  private static boolean testFollowersModeOn(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, -1, 0, -1);
    System.out.println("--- --- Followers Mode ON OK");
    return res;
  }

  private static boolean testFollowersModeOff(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, -1, -1, -1);
    System.out.println("--- --- Followers Mode OFF OK");
    return res;
  }


  private static boolean testEmoteOnlyModeOn(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, -1, -1, 1);
    System.out.println("--- --- Emote-only Mode ON OK");
    return res;
  }

  private static boolean testEmoteOnlyModeOff(Roomstate state) {
    boolean res = doTest(state, "", -1, -1, -1, -1, 0);
    System.out.println("--- --- Emote-only Mode OFF OK");
    return res;
  }

  private static boolean doTest(Roomstate room, String lang, int r9k, int sub, int slow, int followers, int emote) {
    assertTrue("Got: " + room.getBroadcasterLanguage() + " Expected: " + lang, room.getBroadcasterLanguage().equals(lang));
    assertTrue("Got: " + room.get9kMode() + " Expected: " + r9k, room.get9kMode() == r9k);
    assertTrue("Got: " + room.getSubMode() + " Expected: " + sub, room.getSubMode() == sub);
    assertTrue("Got: " + room.getSlowModeTimer() + " Expected: " + slow, room.getSlowModeTimer() == slow);
    assertTrue("Got: " + room.getFollowersMode() + " Expected: " + followers, room.getFollowersMode() == followers);
    assertTrue("Got: " + room.getEmoteOnlyMode() + " Expected: " + emote, room.getEmoteOnlyMode() == emote);
    return true;
  }
}
