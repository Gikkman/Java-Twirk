package com.gikk.twirk.types;

import com.gikk.twirk.TestConsumer;
import com.gikk.twirk.TestResult;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import org.junit.Assert;

/**
 *
 * @author Gikkman
 */
public class TestNamelist {

    public static void test(Consumer<String> twirkIn,
                            TestConsumer<Collection<String>> test) throws Exception{
        TestResult res = test.assign( (list) -> {
            boolean result = list.containsAll(Arrays.asList("testacc1", "testacc2", "testacc3"));
            System.out.println("--- --- All names accounted for");
            return result;
        });
        twirkIn.accept(":tmi.twitch.tv 375 testbot :-");
        twirkIn.accept(":tmi.twitch.tv 372 testbot :You are in a maze of twisty passages, all alike.");
        twirkIn.accept(":testbot.tmi.twitch.tv 353 testbot = #testchan :testacc1 testacc2");
        twirkIn.accept(":testbot.tmi.twitch.tv 353 testbot = #testchan :testacc3");
        twirkIn.accept(":testbot.tmi.twitch.tv 366 testbot #testchan :End of /NAMES list");

        Assert.assertTrue(res.await());
    }
}
