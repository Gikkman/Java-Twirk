package com.gikk.twirk.types.twitchMessage;

import com.gikk.twirk.TestBiConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.types.users.TwitchUser;
import java.util.function.Consumer;
import static org.junit.Assert.*;

public class TestMessage {
    final static String USER_MESSAGE_NO_EMOTE = "@badges=;color=;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage 조선말";
    final static String USER_MESSAGE_WITH_EMOTE = "@badges=;color=#FF69B4;display-name=Gikklol;emotes=86:10-19;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikklol!gikklol@gikklol.tmi.twitch.tv PRIVMSG #gikkman :beefin it BibleThump";

    public static void testPrivMsg(Consumer<String> twirkInput, TestBiConsumer<TwitchUser, TwitchMessage> test ) throws Exception{
        TestResult t1 = test.assign(TestMessage::testUserMessageNoEmote);
        twirkInput.accept(USER_MESSAGE_NO_EMOTE);
        t1.await();

        TestResult t2 = test.assign(TestMessage::testUserMessageWithEmote);
        twirkInput.accept(USER_MESSAGE_WITH_EMOTE);
        t2.await();
	}

    private static boolean testUserMessageNoEmote(TwitchUser user, TwitchMessage msg){
        runPrivMsgTest(msg, "userMessage 조선말", false, ":gikkman!gikkman@gikkman.tmi.twitch.tv");
        System.out.println("--- --- Regular Message No Emotes OK");
        return true;
    }

    private static boolean testUserMessageWithEmote(TwitchUser user, TwitchMessage msg){
        runPrivMsgTest(msg, "beefin it BibleThump", true, ":gikklol!gikklol@gikklol.tmi.twitch.tv");
        System.out.println("--- --- Regular Message With Emotes OK");
        return true;
    }

	private static void runPrivMsgTest(TwitchMessage message, String content, boolean hasEmotes, String prefix) {
		//Assert message properties
		assertTrue( !message.getTag().isEmpty() );
		assertEquals( prefix, message.getPrefix() );
		assertEquals( "PRIVMSG", message.getCommand());
		assertEquals( "#gikkman", message.getTarget());
		assertEquals( content, message.getContent() );
		assertEquals( hasEmotes, message.hasEmotes() );
	}
}
