package com.gikk.twirc.messages;

public class TwirkMode {
	public static enum Event{ GainedMod, LostMod };
	
	public final String user;
	public final Event  event;
	
	public TwirkMode( TwirkMessage message ){
		/* Mode events can have two different layouts:
		 * 	
		 * 	> :jtv MODE #channel +o operator_user		- Gained Mod
		 *	> :jtv MODE #channel -o operator_user		- Lost Mod
		 * 
		 * So we simply look at the content part to determine which user is affected
		 * and what event occured
		 */
		String content = message.getContent();
		this.event = content.startsWith("+o") ? Event.GainedMod : Event.LostMod;
		this.user =  content.substring( content.indexOf(' ') );
	}
}
