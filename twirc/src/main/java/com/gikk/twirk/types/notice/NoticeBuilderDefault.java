package com.gikk.twirk.types.notice;

import com.gikk.twirk.types.NOTICE_EVENT;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class NoticeBuilderDefault implements NoticeBuilder {
	NOTICE_EVENT event;
	String message;
	
	@Override
	public Notice build(TwitchMessage message) {
		//The event is posted after the '@msg-id=' part of the tag
		String event = message.getTag().substring( message.getTag().indexOf('=') + 1 );
		this.event = parseEvent( event );
		this.message = message.getContent();
		
		return new NoticeImpl(this);
	}

	private NOTICE_EVENT parseEvent(String event) {
		if( event.equalsIgnoreCase( "subs_on" ) )
			return NOTICE_EVENT.SUB_MODE_ON;
		else if( event.equalsIgnoreCase( "subs_off" ) )
			return NOTICE_EVENT.SUB_MODE_OFF;
		else if( event.equalsIgnoreCase( "slow_on" ) )
			return NOTICE_EVENT.SLOW_MODE_ON;
		else if( event.equalsIgnoreCase( "slow_off" ) )
			return NOTICE_EVENT.SLOW_MODE_OFF;
		else if( event.equalsIgnoreCase( "r9k_on" ) )
			return NOTICE_EVENT.R9K_MODE_ON;
		else if( event.equalsIgnoreCase( "r9k_off" ) )
			return NOTICE_EVENT.R9K_MODE_OFF;
		else if( event.equalsIgnoreCase( "host_on" ) )
			return NOTICE_EVENT.HOST_MODE_ON;
		else if( event.equalsIgnoreCase( "host_off" ) )
			return NOTICE_EVENT.HOST_MODE_OFF;
		else 
			return NOTICE_EVENT.OTHER;	
	}
	
}
