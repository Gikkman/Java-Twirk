package com.gikk.twirc;

import com.gikk.twirc.events.TwirkListenerBaseImpl;
import com.gikk.twirc.types.TwirkMode;

class TwirkMaintainanceListener extends TwirkListenerBaseImpl{
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
		if( !instance.online.add( joinedNick ) )
			System.out.println("\tUser " + joinedNick + " was already listed as online....");
	}

	@Override
	public void onPart(String partedNick) {
		if( !instance.online.remove( partedNick ) )
			System.out.println("\tUser " + partedNick + " was not listed as online....");
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
