package com.gikk.twirk.types;

/**Class for representing a MODE from Twitch. A MODE means that a user gained or lost mod status. <br><br>
 * 
 * <b>IMPORTANT</b> Twitch's MODE messages are not reliable. Sometimes users lose mod status for a few moments without explanation. It might
 * also take up to 1 minute to fire a MODE event after you grant someone mod status. Therefore, I recommend relying on the {@link TwirkUser} object
 * which is accompanied each PRIVMSG and WHISPER sent from Twitch, and check from mod status in that instead.<br><br>
 * 
 * If you still use MODE messages, this is what you need to know:<br>
 * MODE messages come it two forms:<ul>
 * <li>GAINED_MOD - Someone was granted mod status. The {@code user} field tells you whom gained mod.
 * <li>LOST_MOD - - Someone lost mod status. The {@code user} field tells you whom lost mod.
 * <br>
 * @author Gikkman
 *
 */
public class TwirkMode {
	public static enum EVENT{ GAINED_MOD, LOST_MOD };
	
	/** Which user was affected by this MODE event */
	public final String user;
	/** GAINED_MOD if {@code user} gained mod, LOST_MOD if {@code user} lost mod */
	public final EVENT  event;
	
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
		this.event = content.startsWith("+o") ? EVENT.GAINED_MOD : EVENT.LOST_MOD;
		this.user =  content.substring( content.indexOf(' ') );
	}
}
