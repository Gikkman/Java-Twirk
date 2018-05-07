package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.usernotice.subtype.Ritual;

/**
 *
 * @author Gikkman
 */
class RitualImpl implements Ritual{

    private final String ritualName;

    public RitualImpl(String ritualName) {
        this.ritualName = ritualName;
    }

    @Override
    public String getRitualName() {
        return ritualName;
    }
}
