package com.gikk.twirk.types.cheer;

import org.junit.Assert;
import org.junit.Test;

public class TestCheerImpl {

    @Test
    public void testGetImageUrl_whenCheerIs1() {
        Cheer cheer = new CheerImpl(1, "");
        String url = cheer.getImageURL(CheerTheme.DARK, CheerType.ANIMATED, CheerSize.SMALL);
        Assert.assertEquals("static-cdn.jtvnw.net/bits/dark/animated/gray/2", url);
    }

    @Test
    public void testGetImageUrl_whenCheerIs100() {
        Cheer cheer = new CheerImpl(100, "");
        String url = cheer.getImageURL(CheerTheme.LIGHT, CheerType.STATIC, CheerSize.TINY);
        Assert.assertEquals("static-cdn.jtvnw.net/bits/light/static/purple/1", url);
    }

    @Test
    public void testGetImageUrl_whenCheerIs1000() {
        Cheer cheer = new CheerImpl(1000, "");
        String url = cheer.getImageURL(CheerTheme.DARK, CheerType.ANIMATED, CheerSize.SMALL);
        Assert.assertEquals("static-cdn.jtvnw.net/bits/dark/animated/green/2", url);
    }

    @Test
    public void testGetImageUrl_whenCheerIs5000() {
        Cheer cheer = new CheerImpl(5000, "");
        String url = cheer.getImageURL(CheerTheme.LIGHT, CheerType.STATIC, CheerSize.MEDIUM);
        Assert.assertEquals("static-cdn.jtvnw.net/bits/light/static/blue/3", url);
    }

    @Test
    public void testGetImageUrl_whenCheerIs10000() {
        Cheer cheer = new CheerImpl(10000, "");
        String url = cheer.getImageURL(CheerTheme.DARK, CheerType.STATIC, CheerSize.LARGE);
        Assert.assertEquals("static-cdn.jtvnw.net/bits/dark/static/red/4", url);
    }

    @Test
    public void testGetBits() {
        Cheer cheer1 = new CheerImpl(1, "");
        Assert.assertEquals(1, cheer1.getBits());

        Cheer cheer100 = new CheerImpl(100, "");
        Assert.assertEquals(100, cheer100.getBits());

        Cheer cheer123456789 = new CheerImpl(123456789, "");
        Assert.assertEquals(123456789, cheer123456789.getBits());
    }

    @Test
    public void testGetMessage() {
        Cheer cheer1 = new CheerImpl(1, "");
        Assert.assertEquals("", cheer1.getMessage());

        Cheer cheer2 = new CheerImpl(100, "Hello World!");
        Assert.assertEquals("Hello World!", cheer2.getMessage());

        Cheer cheer3 = new CheerImpl(123456789,"여보세요");
        Assert.assertEquals("여보세요", cheer3.getMessage());
    }
}
