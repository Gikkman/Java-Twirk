package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.enums.CLEARCHAT_MODE;
import com.gikk.twirk.types._IDENTIFIERS;
import com.gikk.twirk.types._TagReader;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_ClearChatBuilder implements ClearChatBuilder{	
	CLEARCHAT_MODE mode;
	String target = "";
	int duration = -1;
	String reason = "";
	String rawLine;
	
	@Override
	public ClearChat build(TwitchMessage twitchMessage) {
		this.rawLine = twitchMessage.getRaw();
		
		if( twitchMessage.getContent().isEmpty() ){
			this.mode = CLEARCHAT_MODE.COMPLETE;
			this.target = "";
		}
		else{
			this.mode = CLEARCHAT_MODE.USER;
			this.target = twitchMessage.getContent();

			_TagReader r = new _TagReader(twitchMessage.getTag());		
			this.duration = r.getAsInt(_IDENTIFIERS.BAN_DURATION);			
			this.reason = r.getAsString(_IDENTIFIERS.BAN_REASON);
		}
		
		return new ClearChatImpl(this);
	}	
}
