package com.gikk.twirk.types.notice;

import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.NOTICE_EVENT;

/**Class for representing a NOTICE from Twitch.<br><br>
 * 
 * A NOTICE means that there were some event occured on Twitch's side that they want to inform us of. There are
 * a lot of different reasons for receiving a notice, and it is impossible to explicitly cover them all. Thus, I've
 * chosen to cover those that Twitch have stated in their documentation: 
 * <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">https://github.com/justintv/Twitch-API/blob/master/IRC.md</a> <br>
 * The implemented notices are: <ul>
 * <li><code>SUB_MODE_ON / SUB_MODE_OFF</code>
 * <li><code>SLOW_MODE_ON / SLOW_MODE_OFF</code>
 * <li><code>R9K_MODE_ON / R9K_MODE_OFF</code>
 * <li><code>HOST_MODE_ON / HOST_MODE_OFF</code> - We started / stopped hosting someone
 * <li><code>OTHER</code>- All notices which does not fall into any of the above notice types fall into the MISC type
 * </ul>
 * @author Gikkman
 *
 */
public interface Notice extends AbstractType{

	/** The {@link NOTICE_EVENT} type of this NOTICE 
	 * 
	 * @return The {@link NOTICE_EVENT} of this NOTICE
	 */
	public NOTICE_EVENT getEvent();
	
	/** The message that came with this NOTICE. Useful when we get a {@link NOTICE_EVENT} of type MISC 
	 * 
	 * @return The message
	 */
	public String getMessage();
	
	/** Fetches the raw Notice ID exactly as Twitch formats it. In case the {@link NOTICE_EVENT} is of type {@link NOTICE_EVENT#OTHER}, this
	 * field might be useful for checking what id this EVENT had.
	 * 
	 * @return The raw Notice ID
	 */
	public String getRawNoticeID();
}
