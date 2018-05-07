package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.Raid;

/**
 *
 * @author Gikkman
 */
class RaidImpl implements Raid{

    private final String displayName;
    private final String loginName;
    private final int raidCount;

    RaidImpl(String displayName, String loginName, int raidCount){
        this.displayName = displayName;
        this.loginName = loginName;
        this.raidCount = raidCount;
    }

    @Override
    public String getSourceDisplayName() {
        return displayName;
    }

    @Override
    public String getSourceLoginName() {
        return loginName;
    }

    @Override
    public int getRaidCount() {
        return raidCount;
    }
}
