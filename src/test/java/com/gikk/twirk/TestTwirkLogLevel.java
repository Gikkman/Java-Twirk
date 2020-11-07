package com.gikk.twirk;

import org.junit.Assert;
import org.junit.Test;

public class TestTwirkLogLevel {

    @Test
    public void testErrorLevel() {
        TwirkLogLevel thisLevel = TwirkLogLevel.ERROR;
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.ERROR) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.WARN) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.INFO) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.DEBUG) );
    }

    @Test
    public void testWarnLevel() {
        TwirkLogLevel thisLevel = TwirkLogLevel.WARN;
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.ERROR) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.WARN) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.INFO) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.DEBUG) );
    }

    @Test
    public void testInfoLevel() {
        TwirkLogLevel thisLevel = TwirkLogLevel.INFO;
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.ERROR) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.WARN) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.INFO) );
        Assert.assertFalse( thisLevel.isAtLeast(TwirkLogLevel.DEBUG) );
    }

    @Test
    public void testDebugLevel() {
        TwirkLogLevel thisLevel = TwirkLogLevel.DEBUG;
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.ERROR) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.WARN) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.INFO) );
        Assert.assertTrue( thisLevel.isAtLeast(TwirkLogLevel.DEBUG) );
    }
}
