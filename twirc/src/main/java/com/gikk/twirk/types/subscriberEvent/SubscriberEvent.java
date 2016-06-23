package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.enums.SUB_EVENT;
import com.gikk.twirk.types.AbstractType;
import com.gikk.twirk.types.usernotice.Usernotice;

/**
 * Class for representing a Subscription Event from Twitch.<br><br>
 * 
 * These events are caught by scraping chat for certain messages from the user 'twitchnotify'.
 * There are 5 types of subscriber events: <ul>
 * <li>NEW - A new subscriber, whom have not subscribed before
 * <li>RESUB - A subscriber whom have subscribed before. 
 * <li>RESUB_AWAY - A notice that an amount of re-subscriptions occurred while you were away
 * <li>HOST_NEW - The channel you are hosting had a new subscriber
 * <li>HOST_RESUB - The channel you are hosting had a re-subscriber
 * <li>UNKNOWN - Safety valve. Only returned if we received a notice we could not parse
 * <br><br>
 * 
 * The {@link #getValue()} method will return different information depending on what type of notification
 * we received. If it is a re-sub event (host or local) it will contain how many months the subscriber has subscribed
 * for thus far. If it is a resub-away event, it will tell us how many subscribers re-subbed when we were away. In any
 * other case, it will return 0.
 * 
 * @version 0.2
 * All SubscriberEvents, except for NEW, will now be delivered via a {@link Usernotice}. <b> This type will probably be depricated
 * in the future, when Twitch extends their Subscriber system.</b>
 * 
 * @author Gikkman
 */
public interface SubscriberEvent extends AbstractType{
	
	/**Fetches the name of the user that subscribed. Might be empty if the {@link SUB_EVENT} is of type
	 * UNKNOWN or RESUB_AWAY. See {@link #getEventType()}.<br>
	 * If a name is returned, it will be formated as the users display name on Twitch.
	 * 
	 * @return The name of the subscriber (might be empty)
	 */
	public String getSubscriber();
	
	/**Fetches what type of {@link SUB_EVENT} this event is. 
	 * There are 5 types of subscriber events: <ul>
	 * <li>NEW - A new subscriber, whom have not subscribed before
	 * <li>RESUB - A subscriber whom have subscribed before. 
	 * <li>RESUB_AWAY - A notice that an amount of re-subscriptions occurred while you were away
	 * <li>HOST_NEW - The channel you are hosting had a new subscriber
	 * <li>HOST_RESUB - The channel you are hosting had a re-subscriber
	 * <li>UNKNOWN - Safety valve. Only returned if we received a notice we could not parse
	 * <br><br>
	 * @return {@link SUB_EVENT} for this subscriber event
	 */
	public SUB_EVENT getEventType();
	
	/**This method will return different information depending on what type of notification
	 * we received. If it is a re-sub event (host or local) it will contain how many months the subscriber has subscribed
	 * for thus far. If it is a resub-away event, it will tell us how many subscribers re-subbed when we were away. In any
	 * other case, it will return 0.
	 * 
	 * @return The value associated with this event
	 */
	public int getValue();
}
