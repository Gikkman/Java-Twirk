package com.gikk.twirk.types;

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
 * <li><code>MISC</code>- All notices which does not fall into any of the above notice types fall into the MISC type
 * </ul>
 * 
 * @author Gikkman
 *
 */
public class TwirkNotice {
	/** Represents different types of NOTICE events.<br> See <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">https://github.com/justintv/Twitch-API/blob/master/IRC.md</a> */
	public static enum EVENT{ SUB_MODE_ON, SUB_MODE_OFF,
							  SLOW_MODE_ON, SLOW_MODE_OFF,
							  R9K_MODE_ON, R9K_MODE_OFF,
							  HOST_MODE_ON, HOST_MODE_OFF,
							  MISC };
	/** The {@link EVENT} type of this NOTICE */
	public final EVENT event;
	/** The message that came with this NOTICE. Useful when we get a {@link EVENT} of type MISC */
	public final String message;
	
	public TwirkNotice(TwirkMessage message) {
		//The event is posted after the '@msg-id=' part of the tag
		String event = message.getTag().substring( message.getTag().indexOf('=') + 1 );
		this.event = parseEvent( event );
		this.message = message.getContent();
	}

	private EVENT parseEvent(String event) {
		if( event.equalsIgnoreCase( "subs_on" ) )
			return EVENT.SUB_MODE_ON;
		else if( event.equalsIgnoreCase( "subs_off" ) )
			return EVENT.SUB_MODE_OFF;
		else if( event.equalsIgnoreCase( "slow_on" ) )
			return EVENT.SLOW_MODE_ON;
		else if( event.equalsIgnoreCase( "slow_off" ) )
			return EVENT.SLOW_MODE_OFF;
		else if( event.equalsIgnoreCase( "r9k_on" ) )
			return EVENT.R9K_MODE_ON;
		else if( event.equalsIgnoreCase( "r9k_off" ) )
			return EVENT.R9K_MODE_OFF;
		else if( event.equalsIgnoreCase( "host_on" ) )
			return EVENT.HOST_MODE_ON;
		else if( event.equalsIgnoreCase( "host_off" ) )
			return EVENT.HOST_MODE_OFF;
		else 
			return EVENT.MISC;	
	}
}
