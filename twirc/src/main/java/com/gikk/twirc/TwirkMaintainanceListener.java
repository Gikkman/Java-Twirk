package com.gikk.twirc;

import com.gikk.twirc.events.AbstractTwirkListener;
import com.gikk.twirc.messages.TwirkMode;

class TwirkMaintainanceListener extends AbstractTwirkListener{
	private final Twirk instance;
	
	TwirkMaintainanceListener(Twirk twirk) {
		this.instance = twirk;
	}
	
	@Override
	public void onAnything(String line) {
		if( instance.verboseMode )
			System.out.println("IN  "+line );
	}

	@Override
	public void onJoin(String joinedNick) {
		instance.online.add( joinedNick );
	}

	@Override
	public void onPart(String partedNick) {
		instance.online.remove( partedNick );
	}

	@Override
	public void onMode(TwirkMode mode) {			
		if( mode.event == TwirkMode.Event.GainedMod ){
			//Moderator added
			instance.moderators.add( mode.user );
		}
		else {
			//Moderator removed
			instance.moderators.remove( mode.user );
		}
	}

	@Override
	public void onDisconnect() {
		instance.online.clear();
		instance.moderators.clear();
	}
	
}
