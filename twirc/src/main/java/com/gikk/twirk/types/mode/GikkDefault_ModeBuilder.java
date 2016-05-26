package com.gikk.twirk.types.mode;

import com.gikk.twirk.types.mode.Mode.MODE_EVENT;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class GikkDefault_ModeBuilder implements ModeBuilder{
	MODE_EVENT event;
	String user;
	String rawLine;
	
	@Override
	public Mode build(TwitchMessage message) {
		/* Mode events can have two different layouts:
		 * 	
		 * 	> :jtv MODE #channel +o operator_user		- Gained Mod
		 *	> :jtv MODE #channel -o operator_user		- Lost Mod
		 * 
		 * So we simply look at the content part to determine which user is affected
		 * and what event occurred
		 */
		this.rawLine = message.getRaw();
		String content = message.getContent();
		this.event = content.startsWith("+o") ? MODE_EVENT.GAINED_MOD : MODE_EVENT.LOST_MOD;
		this.user =  content.substring( content.indexOf(' ') +1 );
		
		return new ModeImpl(this);
	}		
}
