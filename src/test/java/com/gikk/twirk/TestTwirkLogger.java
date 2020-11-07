package com.gikk.twirk;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestTwirkLogger {

    @Test
    public void testErrorLogger() {
        AtomicInteger callCount = new AtomicInteger(0);
        TwirkLogger logger = new TwirkLogger(TwirkLogLevel.ERROR,
                (s) -> callCount.set(1),    // Error log method
                (s) -> callCount.set(5),    // Warn log method
                (s) -> callCount.set(10),   // Info log method
                (s) -> callCount.set(15));  // Debug log method

        logger.debug("Test call");
        Assert.assertEquals("Debug method should not have been called", 0, callCount.get());

        logger.info("Test call");
        Assert.assertEquals("Info method should not have been called", 0, callCount.get());

        logger.warn("Test call");
        Assert.assertEquals("Warn method should not have been called",0 , callCount.get());

        logger.error("Test call");
        Assert.assertEquals(1, callCount.get());
    }

    @Test
    public void testWarnLogger() {
        AtomicInteger callCount = new AtomicInteger(0);
        TwirkLogger logger = new TwirkLogger(TwirkLogLevel.WARN,
                (s) -> callCount.set(1),    // Error log method
                (s) -> callCount.set(5),    // Warn log method
                (s) -> callCount.set(10),   // Info log method
                (s) -> callCount.set(15));  // Debug log method

        logger.debug("Test call");
        Assert.assertEquals("Debug method should not have been called", 0, callCount.get());

        logger.info("Test call");
        Assert.assertEquals("Info method should not have been called", 0, callCount.get());

        logger.warn("Test call");
        Assert.assertEquals(5, callCount.get());

        logger.error("Test call");
        Assert.assertEquals(1, callCount.get());
    }

    @Test
    public void testInfoLogger() {
        AtomicInteger callCount = new AtomicInteger(0);
        TwirkLogger logger = new TwirkLogger(TwirkLogLevel.INFO,
                (s) -> callCount.set(1),    // Error log method
                (s) -> callCount.set(5),    // Warn log method
                (s) -> callCount.set(10),   // Info log method
                (s) -> callCount.set(15));  // Debug log method

        logger.debug("Test call");
        Assert.assertEquals("Debug method should not have been called", 0, callCount.get());

        logger.info("Test call");
        Assert.assertEquals(10, callCount.get());

        logger.warn("Test call");
        Assert.assertEquals(5, callCount.get());

        logger.error("Test call");
        Assert.assertEquals(1, callCount.get());
    }

    @Test
    public void testDebugLogger() {
        AtomicInteger callCount = new AtomicInteger(0);
        TwirkLogger logger = new TwirkLogger(TwirkLogLevel.DEBUG,
                (s) -> callCount.set(1),    // Error log method
                (s) -> callCount.set(5),    // Warn log method
                (s) -> callCount.set(10),   // Info log method
                (s) -> callCount.set(15));  // Debug log method

        logger.debug("Test call");
        Assert.assertEquals(15, callCount.get());

        logger.info("Test call");
        Assert.assertEquals(10, callCount.get());

        logger.warn("Test call");
        Assert.assertEquals(5, callCount.get());

        logger.error("Test call");
        Assert.assertEquals(1, callCount.get());
    }
}
