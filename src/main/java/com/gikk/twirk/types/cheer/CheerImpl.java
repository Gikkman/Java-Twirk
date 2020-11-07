package com.gikk.twirk.types.cheer;

import java.util.Objects;

/**
 *
 * @author Gikkman
 */
public class CheerImpl implements Cheer{
    // Full URL should be "static-cdn.jtvnw.net/bits/<theme>/<type>/<color>/<size>"
    private final static String ROOT_URL = "static-cdn.jtvnw.net/bits";
    private final int bits;
    private final String message;

    public CheerImpl(int bits, String message) {
        this.bits = bits;
        this.message = message;
    }

    @Override
    public int getBits() {
        return bits;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cheer)) {
            return false;
        }
        Cheer other = (Cheer) obj;
        return bits == other.getBits() && message.equals(other.getMessage());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.bits;
        hash = 17 * hash + Objects.hashCode(this.message);
        return hash;
    }

    @Override
    public String getImageURL(CheerTheme theme, CheerType type, CheerSize size) {
        String color;
        if(bits < 100){
            color = "gray";
        }
        else if( bits < 1000) {
            color = "purple";
        } else if( bits < 5000) {
            color = "green";
        } else if( bits < 10000) {
            color = "blue";
        } else {
            color = "red";
        }
        return ROOT_URL +
                "/" + theme.getValue() +
                "/" + type.getValue() +
                "/" + color +
                "/" + size.getValue();
    }
}
