package com.gikk.twirk.types.cheer;

/**
 *
 * @author Gikkman
 */
public interface Cheer {
    public int getBits();
    public String getMessage();
    public String getImageURL(CheerTheme theme, CheerType type, CheerSize size);
}
