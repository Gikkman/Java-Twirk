package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.enums.HOSTTARGET_MODE;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestHostTarget {
	private final static String START_HOST = ":tmi.twitch.tv HOSTTARGET #gikkman :gikkbot 1";   //Gikkman host Gikkbot for 1 viewer
	private final static String STOP_HOST_NO_VIEWERS = ":tmi.twitch.tv HOSTTARGET #gikkman :-";	//Gikkbot stopped hosting for 0 viewers
	private final static String STOP_HOST_VIEWERS = ":tmi.twitch.tv HOSTTARGET #gikkman :- 5";  //Gikkbot stopped hosting for 0 viewers

	public static void test(Consumer<String> twirkInput, TestConsumer<HostTarget> test ) throws Exception{
        TestResult resStart = test.assign(TestHostTarget::testStart);
        twirkInput.accept(START_HOST);
        resStart.await();

        TestResult resStopNoView = test.assign(TestHostTarget::testStopNoView);
        twirkInput.accept(STOP_HOST_NO_VIEWERS);
        resStopNoView.await();

        TestResult resStopView = test.assign(TestHostTarget::testStopView);
        twirkInput.accept(STOP_HOST_VIEWERS);
        resStopView.await();
	}

    static boolean testStart(HostTarget host){
        doTest(host, START_HOST, HOSTTARGET_MODE.START, 1, "gikkbot" );
        System.out.println("--- --- Host Start OK");
        return true;
    }

    static boolean testStopNoView(HostTarget host){
        doTest(host, STOP_HOST_NO_VIEWERS, HOSTTARGET_MODE.STOP, 0, "" );
        System.out.println("--- --- Stop Host No Viewers OK");
        return true;
    }

    static boolean testStopView(HostTarget host){
        doTest(host, STOP_HOST_VIEWERS, HOSTTARGET_MODE.STOP, 5, "" );
        System.out.println("--- --- Stop Host With Viewers OK");
        return true;
    }

	private static void doTest(HostTarget host, String raw, HOSTTARGET_MODE MODE, int viewers, String target) {
		assertEquals(target, host.getTarget());
		assertEquals(viewers, host.getViewerCount());
		assertEquals(MODE, host.getMode());
		assertEquals(raw, host.getRaw());

	}
}
