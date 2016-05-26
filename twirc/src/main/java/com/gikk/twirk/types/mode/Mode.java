package com.gikk.twirk.types.mode;

import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.users.TwitchUser;

/**Class for representing a MODE from Twitch. A MODE means that a user gained or lost mod status. <br><br>
 * 
 * <b>IMPORTANT</b> Twitch's MODE messages are not reliable. Sometimes users lose mod status for a few moments without explanation. It might
 * also take up to 1 minute to fire a MODE event after you grant someone mod status. Therefore, I recommend relying on the {@link TwitchUser} object
 * which is accompanied each PRIVMSG and WHISPER sent from Twitch, and check from mod status in that instead.<br><br>
 * 
 * If you still use MODE messages, this is what you need to know:<br>
 * MODE messages come it two forms:<ul>
 * <li>GAINED_MOD - Someone was granted mod status. The {@code user} field tells you whom gained mod.
 * <li>LOST_MOD - - Someone lost mod status. The {@code user} field tells you whom lost mod.
 * <br><br>
 * @author Gikkman
 *
 */
public interface Mode extends AbstractType{
	public static enum MODE_EVENT{ GAINED_MOD, LOST_MOD }

	/** {@link MODE_EVENT#GAINED_MOD} if {@code user} gained mod, {@link MODE_EVENT#LOST_MOD} if {@code user} lost mod
	 * 
	 * @return {@link MODE_EVENT} of this MODE
	 */
	public MODE_EVENT getEvent();
	
	/** Which user was affected by this MODE event
	 * 
	 * @return The user's name, in lower case letters
	 */
	public String getUser();
	
}
