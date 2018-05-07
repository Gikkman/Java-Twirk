package com.gikk.twirk.types.cheer;

/**
 *
 * @author Gikkman
 */
public enum CheerType {
    ANIMATED("animated"), STATIC("static");

    private final String value;
    CheerType(String s){
        this.value = s;
    }

    String getValue(){
        return value;
    }
}
