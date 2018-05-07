package com.gikk.twirk.types.notice;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.NOTICE_EVENT;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestNotice {
	private final static String SUB_MODE_ON_MESSAGE = "@msg-id=subs_on :tmi.twitch.tv NOTICE #gikkman :This room is now in subscribers-only mode.";
	private final static String SUB_MODE_OFF_MESSAGE = "@msg-id=subs_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in subscribers-only mode.";
	private final static String R9K_MODE_ON_MESSAGE = "@msg-id=r9k_on :tmi.twitch.tv NOTICE #gikkman :This room is now in r9k mode.";
	private final static String R9K_MODE_OFF_MESSAGE = "@msg-id=r9k_off :tmi.twitch.tv NOTICE #gikkman :This room is no longer in r9k mode.";
	private final static String CUSTOM_MESSAGE = "@msg-id=msg_channel_suspended :tmi.twitch.tv NOTICE #gikkman :This ma' message now.";

	public static void test(Consumer<String> twirkInput, TestConsumer<Notice> test ) throws Exception{
        TestResult res1 = test.assign(TestNotice::testSubModeOn);
        twirkInput.accept(SUB_MODE_ON_MESSAGE);
        res1.await();

        TestResult res2 = test.assign(TestNotice::testSubModeOff);
        twirkInput.accept(SUB_MODE_OFF_MESSAGE);
        res2.await();

        TestResult res3 = test.assign(TestNotice::testR9KOn);
        twirkInput.accept(R9K_MODE_ON_MESSAGE);
        res3.await();

        TestResult res4 = test.assign(TestNotice::testR9KOff);
        twirkInput.accept(R9K_MODE_OFF_MESSAGE);
        res4.await();

        TestResult res5 = test.assign(TestNotice::testCustom);
        twirkInput.accept(CUSTOM_MESSAGE);
        res5.await();
	}

    private static boolean testSubModeOn(Notice n){
        doTest(n, SUB_MODE_ON_MESSAGE,
                  NOTICE_EVENT.SUBSCRIBER_MODE_ON,
                  "This room is now in subscribers-only mode.",
                  "subs_on");
        System.out.println("--- --- Sub only On OK");
        return true;
    }

    private static boolean testSubModeOff(Notice n){
        doTest(n, SUB_MODE_OFF_MESSAGE,
                  NOTICE_EVENT.SUBSCRIBER_MODE_OFF,
                  "This room is no longer in subscribers-only mode.",
                  "subs_off");
        System.out.println("--- --- Sub only Off OK");
        return true;
    }

    private static boolean testR9KOn(Notice n){
        doTest(n, R9K_MODE_ON_MESSAGE,
                  NOTICE_EVENT.R9K_ON,
                  "This room is now in r9k mode.",
                  "r9k_on");
        System.out.println("--- --- R9K On OK");
        return true;
    }

    private static boolean testR9KOff(Notice n){
        doTest(n, R9K_MODE_OFF_MESSAGE,
                  NOTICE_EVENT.R9K_OFF,
                  "This room is no longer in r9k mode.",
                  "r9k_off");
        System.out.println("--- --- R9K Off OK");
        return true;
    }

    private static boolean testCustom(Notice n){
        doTest(n, CUSTOM_MESSAGE,
                  NOTICE_EVENT.MESSAGE_CHANNEL_SUSPENDED,
                  "This ma' message now.",
                  "msg_channel_suspended");
        System.out.println("--- --- Custom test OK");
        return true;
    }

	private static void doTest(Notice notice, String raw, NOTICE_EVENT event, String message, String noticeID) {
		assertEquals( event, notice.getEvent() );
		assertEquals( message, notice.getMessage() );
		assertEquals( noticeID, notice.getRawNoticeID() );
		assertEquals( raw, notice.getRaw());

	}
}
