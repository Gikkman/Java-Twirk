package com.gikk.twirk.types.cheer;

/**
 *
 * @author Gikkman
 */
public enum CheerTheme {
    LIGHT("light"), DARK("dark");

    private final String value;
    CheerTheme(String s){
        this.value = s;
    }

    String getValue(){
        return value;
    }
}
