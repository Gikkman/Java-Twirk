package com.gikk.twirk.types.twitchMessage;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import com.gikk.twirk.types.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.Emote.EmoteIndices;
import com.gikk.twirk.types.users.TwitchUser;
import com.gikk.twirk.types.users.GikkDefault_TwitchUserBuilder;

public class TestMessage {
	final static String USERNAME = "gikkman";
	final static String DISPLAY_NAME = "Gikkman";
	final static int userID = 27658385;
	final static int color = 0xFF69B4;
	
	public static void testPrivMsg(){		
		final String USER_MESSAGE_NO_EMOTE = "@badges=;color=;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";
		final String USER_MESSAGE_WITH_EMOTE = "@badges=;color=#FF69B4;display-name=Gikkman;emotes=86:10-19;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :beefin it BibleThump";
		final String MOD_MESSAGE = "@badges=moderator/1;color=#FF69B4;display-name=GikkBot;emotes=;mod=1;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type=mod :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :modMessage";
		final String SUB_MESSAGE = "@badges=subscriber/1;color=#FF69B4;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=1;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :subMessage";
		final String BROADCASTER_MESSAGE = "@badges=broadcaster/1;color=#FF69B4;display-name=Gikkman;emotes=4685:4-9,11-16/15614:18-24;mod=1;room-id=27658385;subscriber=0;turbo=0;user-id=27658385;user-type=mod :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :Yo! tmrHat tmrHat tmrToad";
		final String TURBO_MESSAGE = "@badges=moderator/1,turbo/1;color=#1E90FF;display-name=Test;emotes=;mod=1;room-id=27787567;subscriber=0;turbo=1;user-id=27658385;user-type=mod :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :turboMessage";
		
		runPrivMsgTest(USER_MESSAGE_NO_EMOTE, false, false, false, "userMessage", 255, DISPLAY_NAME, false, new LinkedList<Emote>(), new String[0], USER_TYPE.DEFAULT );
		runPrivMsgTest(MOD_MESSAGE, true, false, false, "modMessage", color, "GikkBot", false, new LinkedList<Emote>(), new String[] {"moderator/1"}, USER_TYPE.MOD );
		runPrivMsgTest(SUB_MESSAGE, false, true, false, "subMessage", color, DISPLAY_NAME, false, new LinkedList<Emote>(), new String[] {"subscriber/1"}, USER_TYPE.DEFAULT);
		runPrivMsgTest(TURBO_MESSAGE, true, false, true, "turboMessage", 0x1E90FF, "Test", false, new LinkedList<Emote>(), new String[] {"moderator/1", "turbo/1"}, USER_TYPE.MOD);
		
		LinkedList<Emote> emotes = new LinkedList<Emote>(); 
						  emotes.add( new EmoteImpl().setEmoteID(86).setPattern("BibleThump").addIndices(10, 20) );
		runPrivMsgTest(USER_MESSAGE_WITH_EMOTE, false, false, false, "beefin it BibleThump", color, DISPLAY_NAME, true, emotes, new String[0], USER_TYPE.DEFAULT);
		
		emotes.clear();
		emotes.add( new EmoteImpl().setEmoteID(4685).setPattern("tmrHat").addIndices(4, 10).addIndices(11, 17));
		emotes.add( new EmoteImpl().setEmoteID(15614).setPattern("tmrToad").addIndices(18, 25));
		runPrivMsgTest(BROADCASTER_MESSAGE, true, false, false, "Yo! tmrHat tmrHat tmrToad", color, DISPLAY_NAME, true, emotes, new String[] {"broadcaster/1"}, USER_TYPE.OWNER);
	}

	private static void runPrivMsgTest(String line, 
									   boolean isMod, boolean isSub, boolean isTurbo, 
									   String content, int color,
									   String displayName, boolean hasEmotes, 
									   LinkedList<Emote> emotes, String[] badges, USER_TYPE userType ) {
		TwitchMessage message = new GikkDefault_TwitchMessageBuilder().build(line);
		TwitchUser user = new GikkDefault_TwitchUserBuilder().build(message);
		
		//Assert message properties
		assertTrue( !message.getTag().isEmpty() );
		assertTrue( message.getPrefix().equals(":gikkman!gikkman@gikkman.tmi.twitch.tv") );
		assertTrue( message.getCommand().equals( "PRIVMSG" ));
		assertTrue( message.getTarget().equals( "#gikkman" ));
		assertTrue( message.getContent() + " != " +content, message.getContent().equals( content ) );
		assertTrue( message.hasEmotes() == hasEmotes );
		assertTrue( message.getEmotes().size() == emotes.size() );
		
		//Assert emote properties
		for( int i = 0; i < emotes.size(); i++){
			Emote argEmote = emotes.get(i);
			Emote msgEmote = message.getEmotes().get(i);
			
			assertTrue( argEmote.getEmoteID() == msgEmote.getEmoteID() );
			assertTrue( argEmote.getPattern().equals( msgEmote.getPattern() ) );
			assertTrue( argEmote.getIndices().size() == msgEmote.getIndices().size() );
			
			for( int j = 0; j < argEmote.getIndices().size(); j++){
				EmoteIndices argIndex = argEmote.getIndices().get(j);
				EmoteIndices msgIndex = msgEmote.getIndices().get(j);
				
				assertTrue(argIndex.beingIndex == msgIndex.beingIndex);
				assertTrue(argIndex.endIndex == msgIndex.endIndex);
			}
		}		
		
		//Assert user properties
		assertTrue( user.getName().equals(displayName) );
		assertTrue( user.getColor() == color);
		assertTrue( user.getUserID() == userID );
		assertTrue( user.isMod() == isMod );
		assertTrue( user.isSub() == isSub );
		assertTrue( user.isTurbo() == isTurbo );
		assertTrue( user.getUserType() == userType );
		assertTrue( user.getBadges().length == badges.length);
		for( int i = 0; i < badges.length; i++){
			String argBadge = badges[i];
			String usrBadge = user.getBadges()[i];
			assertTrue( argBadge.equals( usrBadge ));
		}		
	}
}
