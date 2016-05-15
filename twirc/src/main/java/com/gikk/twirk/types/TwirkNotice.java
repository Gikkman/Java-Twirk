package com.gikk.twirk.types;

public class TwirkNotice {
	public static enum EVENT{ SUB_MODE_ON, SUB_MODE_OFF,
							  SLOW_MODE_ON, SLOW_MODE_OFF,
							  R9K_MODE_ON, R9K_MODE_OFF,
							  HOST_MODE_ON, HOST_MODE_OFF,
							  UNKNOWN };
	
	public final EVENT event;
	public final String message;
	
	public TwirkNotice(TwirkMessage message) {
		//The event is posted after the '@msg-id=' part of the tag
		String event = message.getTag().substring( message.getTag().indexOf('=') + 1 );
		this.event = parseEvent( event );
		this.message = message.getContent();
	}

	private EVENT parseEvent(String event) {
		if( event.equalsIgnoreCase( "subs_on" ) )
			return EVENT.SUB_MODE_ON;
		else if( event.equalsIgnoreCase( "subs_off" ) )
			return EVENT.SUB_MODE_OFF;
		else if( event.equalsIgnoreCase( "slow_on" ) )
			return EVENT.SLOW_MODE_ON;
		else if( event.equalsIgnoreCase( "slow_off" ) )
			return EVENT.SLOW_MODE_OFF;
		else if( event.equalsIgnoreCase( "r9k_on" ) )
			return EVENT.R9K_MODE_ON;
		else if( event.equalsIgnoreCase( "r9k_off" ) )
			return EVENT.R9K_MODE_OFF;
		else if( event.equalsIgnoreCase( "host_on" ) )
			return EVENT.HOST_MODE_ON;
		else if( event.equalsIgnoreCase( "host_off" ) )
			return EVENT.HOST_MODE_OFF;
		else 
			return EVENT.UNKNOWN;	
	}
}
