package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.emote.Emote.EmoteIndices;
import com.gikk.twirk.types.emote.EmoteImpl;
import com.gikk.twirk.types.twitchMessage.GikkDefault_TwitchMessageBuilder;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestUsernotice {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private static final String m1_with_Message = "@badges=staff/1,broadcaster/1,turbo/1;color=#008000;display-name=TWITCH_UserName;emotes=;mod=0;msg-id=resub;msg-param-months=6;room-id=1337;subscriber=1;system-msg=TWITCH_UserName\\shas\\ssubscribed\\sfor\\s6\\smonths!;login=twitch_username;turbo=1;user-id=1337;user-type=staff :tmi.twitch.tv USERNOTICE #channel :Great stream -- keep it up!";
	private static final String m2_wo_Message   = "@badges=staff/1,broadcaster/1,turbo/1;color=#008000;display-name=TWITCH_UserName;emotes=;mod=0;msg-id=resub;msg-param-months=6;room-id=1337;subscriber=1;system-msg=TWITCH_UserName\\shas\\ssubscribed\\sfor\\s6\\smonths!;login=twitch_username;turbo=1;user-id=1337;user-type=staff :tmi.twitch.tv USERNOTICE #channel";
	private static final String m3_with_Emote   = "@badges=;color=#008001;display-name=TWITCH_UserName;emotes=12:0-4;mod=0;msg-id=resub;msg-param-months=4;room-id=1336;subscriber=1;system-msg=TWITCH_UserName\\shas\\ssubscribed\\sfor\\s4\\smonths!;login=twitch_username;turbo=1;user-id=1336;user-type= :tmi.twitch.tv USERNOTICE #channel :Kappa";
	
	public static void test(){
		testMessage(m1_with_Message, "Great stream -- keep it up!", 6, "TWITCH_UserName", "twitch_username", 1337, 0x008000, "TWITCH_UserName has subscribed for 6 months!", USER_TYPE.STAFF, new LinkedList<Emote>(), new String[]{"staff/1","broadcaster/1","turbo/1"} );
		testMessage(m2_wo_Message, "", 								6, "TWITCH_UserName", "twitch_username", 1337, 0x008000, "TWITCH_UserName has subscribed for 6 months!", USER_TYPE.STAFF, new LinkedList<Emote>(), new String[]{"staff/1","broadcaster/1","turbo/1"} );
		
		LinkedList<Emote> emotes = new LinkedList<Emote>();
		emotes.add( new EmoteImpl().setPattern("Kappa").setEmoteID(12).addIndices(0, 5));
		testMessage(m3_with_Emote, "Kappa", 						4, "TWITCH_UserName", "twitch_username", 1336, 0x008001, "TWITCH_UserName has subscribed for 4 months!", USER_TYPE.SUBSCRIBER, emotes, new String[]{});
	}

	private static void testMessage(String line, String subMessage, int months, String displayName, String loginName, 
									int userID, int color, String systemMessage, USER_TYPE UserType, LinkedList<Emote> emotes, String[] badges  ) {
		TwitchMessage message = new GikkDefault_TwitchMessageBuilder().build(line);
		Usernotice notice = new GikkDefault_UsernoticeBuilder().build(message);
		
		assertTrue(notice.getMessage().equals( subMessage ) );
		assertTrue(notice.getMonths() == months );
		assertTrue(notice.getUserID() == userID);
		assertTrue(notice.getName().equals(displayName) );
		assertTrue(notice.getLogin().equals(loginName) );
		assertTrue(!(notice.getName().equals(loginName)) );
		assertTrue(notice.getColor() == color);
		assertTrue(notice.getSystemMessage().equals(systemMessage));
		assertTrue(notice.getUserType() == UserType);
		assertTrue(notice.getRaw().equals(line));
		assertTrue(notice.getEmotes().size() == emotes.size());
		
		for(int i = 0; i < notice.getBadges().length; i++){
			assertTrue("Expected " + notice.getBadges()[i] + " Got " + badges[i], notice.getBadges()[i].equals(badges[i]));
		}
		for(int i = 0; i < notice.getEmotes().size(); i++){
			Emote e1 = notice.getEmotes().get(i);
			Emote e2 = emotes.get(i);
			assertTrue(e1.getPattern().equals(e2.getPattern()));
			assertTrue(e1.getEmoteID() == e2.getEmoteID());
			assertTrue(e1.getIndices().size() == e2.getIndices().size());
			for( int j = 0; j < e1.getIndices().size(); j++){
				EmoteIndices i1 = e1.getIndices().get(j);
				EmoteIndices i2 = e2.getIndices().get(j);
				assertTrue(i1.beingIndex == i2.beingIndex);
				assertTrue(i1.endIndex   == i2.endIndex);
			}
		}
	}
}
