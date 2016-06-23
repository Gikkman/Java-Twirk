package com.gikk.twirk.enums;

/**Enum for representing different types of subscriber events.
 * 
 * @author Gikkman
 *
 */
public enum SUB_EVENT { 
	/***A notice that we received a brand new subscriber*/
	NEW_SUB, 
	/***A notice that we received a re-subscription from a user that was subscribed the previous month too*/
	RESUB, 
	/**A notice that an amount of users re-subscribed while your channel was offline */
	RESUB_AWAY, 
	/**A notice that the channel we are currently hosting received a new subscriber */
	HOST_NEW, 
	/**A notice that the channel we are currently hosting received a re-subscription from a user that was 
	 * subscribed the previous month too*/
	HOST_RESUB,
	/**Safety valve. A notice is given the UNKNOWN property if we could not parse the incoming message. 
	 * (Darn Twitch changing stuff all the time...) */
	UNKNOWN };
