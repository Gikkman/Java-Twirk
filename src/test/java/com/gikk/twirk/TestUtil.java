package com.gikk.twirk;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TestUtil {

    @Test
    public void testPrintError() {
        AtomicInteger callCounter = new AtomicInteger(0);
        AtomicBoolean messageReceived = new AtomicBoolean(false);
        Exception e = new Exception("Hello Moto!");

        TwirkLogger logger = new TwirkLogger((s) -> {
            if(s.contains("Hello Moto!")) messageReceived.set(true);
            callCounter.incrementAndGet();
        }, this::assertFail, this::assertFail, this::assertFail);

        Util.printError(logger, e);
        Assert.assertTrue(messageReceived.get());
        Assert.assertTrue(callCounter.get() > 2);
        /* I don't know how many lines of stacktrace there should be, but there should definitely be more than 2 */
    }

    private void assertFail(String s) {
        Assert.fail("This method should not be called");
    }
}
