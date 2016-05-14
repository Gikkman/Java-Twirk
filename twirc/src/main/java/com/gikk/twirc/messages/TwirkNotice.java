package com.gikk.twirc.messages;

public class TwirkNotice {
	public static enum Event{ SUB_MODE_ON, SUB_MODE_OFF,
							  SLOW_MODE_ON, SLOW_MODE_OFF,
							  R9K_MODE_ON, R9K_MODE_OFF,
							  HOST_MODE_ON, HOST_MODE_OFF,
							  UNKNOWN_EVENT };
	
	public final Event event;
	public final String message;
	
	public TwirkNotice(TwirkMessage message) {
		//The event is posted after the '@msg-id=' part of the tag
		String event = message.getTag().substring( message.getTag().indexOf('=') );
		this.event = parseEvent( event );
		this.message = message.getContent();
	}

	private Event parseEvent(String event) {
		if( event.equalsIgnoreCase( "subs_on" ) )
			return Event.SUB_MODE_ON;
		else if( event.equalsIgnoreCase( "subs_off" ) )
			return Event.SUB_MODE_OFF;
		else if( event.equalsIgnoreCase( "slow_on" ) )
			return Event.SLOW_MODE_ON;
		else if( event.equalsIgnoreCase( "slow_off" ) )
			return Event.SLOW_MODE_OFF;
		else if( event.equalsIgnoreCase( "r9k_on" ) )
			return Event.R9K_MODE_ON;
		else if( event.equalsIgnoreCase( "r9k_off" ) )
			return Event.R9K_MODE_OFF;
		else if( event.equalsIgnoreCase( "host_on" ) )
			return Event.HOST_MODE_ON;
		else if( event.equalsIgnoreCase( "host_off" ) )
			return Event.HOST_MODE_OFF;
		else 
			return Event.UNKNOWN_EVENT;	
	}
}
