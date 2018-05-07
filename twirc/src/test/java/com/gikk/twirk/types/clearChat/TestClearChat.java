package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.CLEARCHAT_MODE;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestClearChat {
	private static final String COMPLETE = ":tmi.twitch.tv CLEARCHAT #gikkman";
	private static final String DURATION_NO_REASON = "@ban-duration=20;ban-reason= :tmi.twitch.tv CLEARCHAT #gikkman :gikkbot";
	private static final String NO_DURATION_NO_REASON = "@ban-reason= :tmi.twitch.tv CLEARCHAT #gikkman :gikkbot";
	private static final String DURATION_REASON = "@ban-duration=10;ban-reason=Bad :tmi.twitch.tv CLEARCHAT #gikkman :gikkbot";
	private static final String NO_DURATION_REASON = "@ban-reason=Bad\\sargument :tmi.twitch.tv CLEARCHAT #gikkman :gikkbot";

	public static void test(Consumer<String> twirkInput, TestConsumer<ClearChat> test ) throws Exception{
        TestResult resComplete = test.assign(TestClearChat::testComplete);
		twirkInput.accept(COMPLETE);
        resComplete.await();

        TestResult resDNR = test.assign(TestClearChat::testDurationNoReason);
		twirkInput.accept(DURATION_NO_REASON);
        resDNR.await();

        TestResult resNDNR = test.assign(TestClearChat::testNoDurationNoReason);
		twirkInput.accept(NO_DURATION_NO_REASON);
        resNDNR.await();

        TestResult resDR = test.assign(TestClearChat::testDurationReason);
		twirkInput.accept(DURATION_REASON);
        resDR.await();

        TestResult resNDR = test.assign(TestClearChat::testNoDurationReason);
		twirkInput.accept(NO_DURATION_REASON);
        resNDR.await();
	}

    static boolean testComplete(ClearChat c){
        test(c, CLEARCHAT_MODE.COMPLETE, "", -1, "");
        System.out.println("--- --- Complete clear OK");
        return true;
    }

    static boolean testDurationNoReason(ClearChat c){
        test(c, CLEARCHAT_MODE.USER, "gikkbot", 20, "");
        System.out.println("--- --- Duration No Reason OK");
        return true;
    }

    static boolean testNoDurationNoReason(ClearChat c){
        test(c, CLEARCHAT_MODE.USER, "gikkbot", -1, "");
        System.out.println("--- --- No Duration No Reason OK");
        return true;
    }

    static boolean testDurationReason(ClearChat c){
        test(c, CLEARCHAT_MODE.USER, "gikkbot", 10, "Bad");
        System.out.println("--- --- Duration w/ Reason OK");
        return true;
    }

    static boolean testNoDurationReason(ClearChat c){
        test(c, CLEARCHAT_MODE.USER, "gikkbot", -1, "Bad argument");
        System.out.println("--- --- No Duration w/ Reason OK");
        return true;
    }

	private static void test(ClearChat clearChat, CLEARCHAT_MODE mode,
                             String target, int duration, String reason) {
		assertEquals(mode, clearChat.getMode());
		assertEquals(duration, clearChat.getDuration());
		assertEquals(target, clearChat.getTarget());
		assertEquals(reason, clearChat.getReason());
	}
}
