package com.gikk.twirk.types.notice;

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
 * 
 * @author Gikkman
 *
 */
public interface Notice {
	/** Represents different types of NOTICE events.<br> See <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">https://github.com/justintv/Twitch-API/blob/master/IRC.md</a> */
	public static enum NOTICE_EVENT{ SUB_MODE_ON, SUB_MODE_OFF,
							  		 SLOW_MODE_ON, SLOW_MODE_OFF,
							  		 R9K_MODE_ON, R9K_MODE_OFF,
							  		 HOST_MODE_ON, HOST_MODE_OFF,
							  		 TIMED_OUT, BANNED,
							  		 OTHER };

	/** The {@link NOTICE_EVENT} type of this NOTICE */
	public NOTICE_EVENT getEvent();
	/** The message that came with this NOTICE. Useful when we get a {@link NOTICE_EVENT} of type MISC */
	public String getMessage();
}
