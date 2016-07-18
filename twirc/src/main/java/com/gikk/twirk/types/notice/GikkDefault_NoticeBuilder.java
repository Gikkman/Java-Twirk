package com.gikk.twirk.types.notice;



import com.gikk.twirk.enums.NOTICE_EVENT;
import com.gikk.twirk.types._IDENTIFIERS;
import com.gikk.twirk.types._TagReader;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_NoticeBuilder implements NoticeBuilder {
	NOTICE_EVENT event;
	String message;
	String rawLine;
	String rawEvent;
	
	@Override
	public Notice build(TwitchMessage message) {
		_TagReader r = new _TagReader(message.getTag());
		this.rawEvent = r.getAsString(_IDENTIFIERS.MESSAGE_ID);
		this.event = parseEvent( rawEvent );
		this.message = message.getContent();
		this.rawLine = message.getRaw();
		
		return new NoticeImpl(this);
	}

	private NOTICE_EVENT parseEvent(String event) {
		if( event.equals( "subs_on" ) )
			return NOTICE_EVENT.SUB_MODE_ON;
		else if( event.equals( "subs_off" ) )
			return NOTICE_EVENT.SUB_MODE_OFF;
		else if( event.equals( "slow_on" ) )
			return NOTICE_EVENT.SLOW_MODE_ON;
		else if( event.equals( "slow_off" ) )
			return NOTICE_EVENT.SLOW_MODE_OFF;
		else if( event.equals( "r9k_on" ) )
			return NOTICE_EVENT.R9K_MODE_ON;
		else if( event.equals( "r9k_off" ) )
			return NOTICE_EVENT.R9K_MODE_OFF;
		else if( event.equals( "host_on" ) )
			return NOTICE_EVENT.HOST_MODE_ON;
		else if( event.equals( "host_off" ) )
			return NOTICE_EVENT.HOST_MODE_OFF;
		else 
			return NOTICE_EVENT.OTHER;	
	}
	
}
