package com.gikk.twirk.types.emote;

import com.gikk.twirk.TestBiConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.types.AbstractEmoteMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 *
 * @author Gikkman
 */
public class TestEmote {
    static final String NO_EMOTES = "@badges=;color=;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";
    static final String ONE_EMOTE = "@badges=;color=#FF69B4;display-name=Gikklol;emotes=86:10-19;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikklol!gikklol@gikklol.tmi.twitch.tv PRIVMSG #gikkman :beefin it BibleThump";
    static final String MULTIPLE_EMOTES = "@badges=broadcaster/1;color=#FF69B4;display-name=Gikkman;emotes=4685:4-9,11-16/15614:18-24;mod=1;room-id=27658385;subscriber=0;turbo=0;user-id=27658385;user-type=mod :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :Yo! tmrHat tmrHat tmrToad";


    public static void test(Consumer<String> twirkInput, TestBiConsumer<TwitchUser, TwitchMessage> test ) throws Exception{
        TestResult noEmotesRes = test.assign(TestEmote::noEmotesTest);
        twirkInput.accept(NO_EMOTES);
        noEmotesRes.await();

        TestResult oneEmoteRes = test.assign(TestEmote::oneEmoteTest);
        twirkInput.accept(ONE_EMOTE);
        oneEmoteRes.await();

        TestResult multiEmotesRes = test.assign(TestEmote::multipleEmotesTest);
        twirkInput.accept(MULTIPLE_EMOTES);
        multiEmotesRes.await();
    }

    private static boolean noEmotesTest(TwitchUser user, TwitchMessage message){
        List<Emote> emotes = new ArrayList<>();
        checkEmotes(emotes, message);
        System.out.println("--- --- No Emtoes OK");
        return true;
    }

    private static boolean oneEmoteTest(TwitchUser user, TwitchMessage message){
        Emote e = new EmoteImpl().setPattern("BibleThump").setEmoteID(86).addIndices(10, 20);
        List<Emote> emotes = Arrays.asList(e);
        checkEmotes(emotes, message);
        System.out.println("--- --- One Emote OK");
        return true;
    }

    private static boolean multipleEmotesTest(TwitchUser user, TwitchMessage message){
        Emote e1 = new EmoteImpl().setPattern("tmrHat").setEmoteID(4685).addIndices(4, 10).addIndices(11, 17);
        Emote e2 = new EmoteImpl().setPattern("tmrToad").setEmoteID(15614).addIndices(18, 25);
        List<Emote> emotes = Arrays.asList(e1, e2);
        checkEmotes(emotes, message);
        System.out.println("--- --- Multiple Emotes OK");
        return true;
    }

    private static void checkEmotes(List<Emote> emotes, AbstractEmoteMessage message){
        //Assert emote properties
		for( int i = 0; i < emotes.size(); i++){
			Emote argEmote = emotes.get(i);
			Emote msgEmote = message.getEmotes().get(i);

			assertEquals( argEmote.getEmoteID(), msgEmote.getEmoteID() );
			assertEquals( argEmote.getPattern(), msgEmote.getPattern() );
			assertEquals( argEmote.getIndices().size(), msgEmote.getIndices().size() );

			for( int j = 0; j < argEmote.getIndices().size(); j++){
				Emote.EmoteIndices argIndex = argEmote.getIndices().get(j);
				Emote.EmoteIndices msgIndex = msgEmote.getIndices().get(j);

				assertEquals(argIndex.beingIndex, msgIndex.beingIndex);
				assertEquals(argIndex.endIndex, msgIndex.endIndex);
			}
		}
    }
}
