package com.gikk.twirk.types.subscriberEvent;

import com.gikk.twirk.types.SUB_EVENT;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class SubscriberEventBuilderDefault implements SubscriberEventBuilder {
	private final String HOST_IDENTIFIER = "subscribed to";
	private final String RESUB_IDENTIFIER = "months in a row";
	private final String AWAY_IDENTIFIER = "you were away!";
	
	String subscriber = "";
	int value = -1;
	SUB_EVENT type = SUB_EVENT.UNKNOWN;
	
	@Override
	public SubscriberEvent build(TwitchMessage message) {
		if( !message.getPrefix().substring(1).startsWith("twitchnotify") ){
			System.err.println("Error. Invalid TwitchMessage.\nCannot construct SubscriberEvent from " + message);
			return null;
		}
		
		/*Subscriber notices comes in two groups: Subscribes to my channel and subscribes to the channel I host.
		 * Messages are formated as seen bellow
		 * 
		 * [message in MY channel]
			PREFIX PRIVMSG #myChannel :USER just subscribed!
			PREFIX PRIVMSG #myChannel :USER subscribed for 13 months in a row!
			PREFIX PRIVMSG #myChannel :13 viewers resubscribed while you were away!
			
			[message in MY channel when hosting TARGET]
			PREFIX PRIVMSG #targetChannel :USER just subscribed to TARGET!
			PREFIX PRIVMSG #targetChannel :USER subscribed to TARGET for 13 months in a row!
		 */
		
		String content = message.getContent();
		String[] parts = content.split(" ", 2);
		
		if( parts[1].indexOf(HOST_IDENTIFIER) >= 0 ){
			//Sub event to host target 
			if( parts[1].indexOf(RESUB_IDENTIFIER) >= 0 ) {
				type = SUB_EVENT.HOST_RESUB;
				subscriber = parts[0];
				value = parseMonths( content );
			} 
			else {
				type = SUB_EVENT.HOST_NEW_SUB;
				subscriber = parts[0];
			}
		} else {
			//Sub event to my channel
			if( parts[1].indexOf(AWAY_IDENTIFIER) >= 0 ){
				type = SUB_EVENT.RESUB_AWAY;
				value = parseAmount( content );				
			}
			else if( parts[1].indexOf(RESUB_IDENTIFIER) >= 0 ){
				type = SUB_EVENT.RESUB;
				subscriber = parts[0];
				value = parseMonths( content );
			}
			else {
				type = SUB_EVENT.NEW_SUB;
				subscriber = parts[0];
			}
		}
		
		return new SubscriberEventImpl(this);
	}

	private int parseMonths(String content) {
		int userNameEndsIndex = content.indexOf(" ");
		int monthsBeginsIndex = content.indexOf("for ", userNameEndsIndex) + "for ".length();
		int montsEndIndex = content.indexOf(" ", monthsBeginsIndex);
		return Integer.parseInt( content.substring( monthsBeginsIndex, montsEndIndex) );
	}

	private int parseAmount(String content) {
		return Integer.parseInt( content.substring(0, content.indexOf(" ")));
	}	
}
