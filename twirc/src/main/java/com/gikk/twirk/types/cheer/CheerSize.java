package com.gikk.twirk.types.cheer;

/**
 *
 * @author Gikkman
 */
public enum CheerSize {
    TINY(1), SMALL(2), MEDIUM(3), LARGE(4);

    private final int value;
    CheerSize(int i){
        this.value = i;
    }

    int getValue(){
        return value;
    }
}
