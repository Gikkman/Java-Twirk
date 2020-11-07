package com.gikk.twirk;

import org.junit.Assert;
import org.junit.Test;

public class TestTwirkBuilder
{
	@Test
	public void testSetPingInterval() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		Assert.assertEquals(15 + 5 * 60, builder.getPingInterval());

		builder.setPingInterval(5);
		Assert.assertEquals(5, builder.getPingInterval());

		builder.setPingInterval(589);
		Assert.assertEquals(589, builder.getPingInterval());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPingInterval_ifIntervalZero_thenThrowsException() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		builder.setPingInterval(0);
		Assert.fail("builder.setPingInterval(0) should have thrown exception");
	}

	@Test
	public void testLogLevel_whenVerboseModeIsTrue() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		builder.setVerboseMode(true);
		Assert.assertEquals(TwirkLogLevel.DEBUG, builder.getLogger().getLogLevel());
	}

	@Test
	public void testLogLevel_whenVerboseModeIsFalse() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		builder.setVerboseMode(false);
		Assert.assertEquals(TwirkLogLevel.INFO, builder.getLogger().getLogLevel());
	}

	@Test
	public void testLogLevel_whenVerboseModeIsTrue_AndLevelIsSetManually() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		builder.setVerboseMode(true);
		builder.setLogLevel(TwirkLogLevel.ERROR);
		Assert.assertEquals(TwirkLogLevel.ERROR, builder.getLogger().getLogLevel());
	}

	@Test
	public void testLogLevel_whenVerboseModeIsFalse_AndLevelIsSetManually() {
		TwirkBuilder builder = new TwirkBuilder("","","");
		builder.setVerboseMode(false);
		builder.setLogLevel(TwirkLogLevel.ERROR);
		Assert.assertEquals(TwirkLogLevel.ERROR, builder.getLogger().getLogLevel());
	}
}
