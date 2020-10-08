package com.gikk.twirk;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.mode.Mode;
import com.gikk.twirk.types.mode.Mode.MODE_EVENT;
import com.gikk.twirk.types.users.Userstate;

/**Class for taking care of basic tasks that our bot should do. However, writing all 
 * of these methods directly in the {@link Twirk} class would get messy. Instead, these simple
 * methods are moved to this separate class.
 * 
 * @author Gikkman
 *
 */
class TwirkMaintainanceListener implements TwirkListener{
	private final Twirk instance;
	
	TwirkMaintainanceListener(Twirk twirk) {
		this.instance = twirk;
	}
	
	@Override
	public void onAnything(String line) {
        instance.logger.fine("IN  "+line );
	}

	@Override
	public void onJoin(String joinedNick) {
		if( !instance.online.add( joinedNick ) ) {
			instance.logger.fine("\tUser " + joinedNick + " was already listed as online....");
        }
	}

	@Override
	public void onPart(String partedNick) {
		if( !instance.online.remove( partedNick ) ) {
			instance.logger.fine("\tUser " + partedNick + " was not listed as online....");
		}
	}

	@Override
	public void onMode(Mode mode) {			
		if( mode.getEvent() == MODE_EVENT.GAINED_MOD ){
			instance.moderators.add( mode.getUser() );
		}
		else {
			instance.moderators.remove( mode.getUser() );
		}
	}

    @Override
    public void onUserstate(Userstate userstate) {
        //If the bot is a Mod, it may send 100 messages per 30 seconds
        //None-Mods may send 20 messages per 30 seconds
        if(userstate.isMod()) {
            instance.setOutputMessageDelay(30000/100);
        } else {
            instance.setOutputMessageDelay(30000/20);
        }
    }

	@Override
	public void onDisconnect() {
		instance.online.clear();
		instance.moderators.clear();
	}
	
}
