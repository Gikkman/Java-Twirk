package com.gikk.twirk.types.mode;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.types.mode.Mode.MODE_EVENT;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TestMode {
	public static String GAIN_MOD = ":jtv MODE #gikkman +o gikkbot";
	public static String LOST_MOD = ":jtv MODE #gikkman -o gikkbot";


	public static void test(Consumer<String> twirkInput, TestConsumer<Mode> test ) throws Exception{
        TestResult resGain = test.assign(TestMode::testGainMod);
        twirkInput.accept(GAIN_MOD);
        resGain.await();

        TestResult resLost = test.assign(TestMode::testLostMod);
        twirkInput.accept(LOST_MOD);
        resLost.await();
	}

    private static boolean testGainMod(Mode mode){
        doTest(mode, GAIN_MOD, MODE_EVENT.GAINED_MOD, "gikkbot");
        System.out.println("--- --- Gained Mod OK");
        return true;
    }

    private static boolean testLostMod(Mode mode){
        doTest(mode, LOST_MOD, MODE_EVENT.LOST_MOD, "gikkbot");
        System.out.println("--- --- Lost Mod OK");
        return true;
    }

	private static void doTest(Mode mode, String raw, MODE_EVENT event, String user) {
		assertEquals(event, mode.getEvent());
		assertEquals(user, mode.getUser());
		assertEquals(raw, mode.getRaw());

	}
}
