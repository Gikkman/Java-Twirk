package com.gikk.twirk.types.emote;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilder;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Gikkman
 */
public class TestEmote {

    @Test
    public void noEmotesTest_whenNoEmotesNodeInTag_thenParsesNoEmotes(){
        // Given
        String input = "@badges=;color=;display-name=Gikkman;mod=0;room-id=1;subscriber=0;turbo=0;user-id=1;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        Assert.assertTrue("Expected empty emotes array", message.getEmotes().isEmpty());
    }

    @Test
    public void noEmotesTest_whenFullTagIsPresent_thenParsesNoEmotes(){
        // Given
        String input = "@badges=;color=;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        Assert.assertTrue("Expected empty emotes array", message.getEmotes().isEmpty());
    }

    @Test
    public void noEmotesTest_whenShortTagIsPresent_thenGivesNoEmotes(){
        // Given
        String input = "@emotes= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        Assert.assertTrue("Expected empty emotes array", message.getEmotes().isEmpty());
    }

    @Test
    public void oneEmoteTest_whenFullTagIsPresent_thenParsesTheEmote(){
        //Given
        String input = "@badges=;color=#FF69B4;display-name=Gikklol;emotes=86:10-19;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :nn!nn@nn.tmi.twitch.tv PRIVMSG #tv :beefin it BibleThump";
        Emote e = new EmoteImpl().setPattern("BibleThump").setEmoteIDString("86").addIndices(10, 20);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oneEmoteTest_whenShortTagIsPresent_thenParsesTheEmote(){
        //Given
        String input = "@emotes=86:10-19 :nn!nn@nn.tmi.twitch.tv PRIVMSG #tv :beefin it BibleThump";
        Emote e = new EmoteImpl().setPattern("BibleThump").setEmoteIDString("86").addIndices(10, 20);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void multipleEmotesTest(){
        // Given
        String input = "@emotes=4685:4-9,11-16/15614:18-24; :anom!anon@anon.tmi.twitch.tv PRIVMSG #tv :Yo! tmrHat tmrHat tmrToad";
        Emote e1 = new EmoteImpl().setPattern("tmrHat").setEmoteIDString("4685").addIndices(4, 10).addIndices(11, 17);
        Emote e2 = new EmoteImpl().setPattern("tmrToad").setEmoteIDString("15614").addIndices(18, 25);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        //Then
        checkEmotes(message, e1, e2);
    }

    @Test
    public void modifiedEmoteTest() {
        // Given
        String input = "@emotes=123_BW:0-12 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink_BW what is that?";
        Emote e = new EmoteImpl().setPattern("doggoThink_BW").setEmoteIDString("123_BW").addIndices(0, 13);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenStartsAndEndsWithLetter() {
        // Given
        String input = "@emotes=A123A:0-11 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :1oggoThink2 what is that?";
        Emote e = new EmoteImpl().setPattern("1oggoThink2").setEmoteIDString("A123A").addIndices(0, 12);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenEndsWithLetter() {
        // Given
        String input = "@emotes=123BW:0-12 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink_BW what is that?";
        Emote e = new EmoteImpl().setPattern("doggoThink_BW").setEmoteIDString("123BW").addIndices(0, 13);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenEndsWithUnderscore() {
        // Given
        String input = "@emotes=123_BW:0-10 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink2 what is that?";
        Emote e = new EmoteImpl().setPattern("doggoThink2").setEmoteIDString("123_BW").addIndices(0, 11);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenNoNumbers() {
        // Given
        String input = "@emotes=ABC_BW:0-10 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink2 what is that?";
        Emote e = new EmoteImpl().setPattern("doggoThink2").setEmoteIDString("ABC_BW").addIndices(0, 11);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenMissingCompletely() {
        // Given
        String input = "@emotes=:0-10 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink2 what is that?";
        Emote e = new EmoteImpl().setPattern("doggoThink2").setEmoteIDString("").addIndices(0, 11);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e);
    }

    @Test
    public void oddEmoteIdTest_whenMultipleOccurrences() {
        // Given
        String input = "@emotes=X123X:0-10,25-35/A1_B:12-15 :anon!anon@anon.tmi.twitch.tv PRIVMSG #tv :doggoThink2 what is that doggoThink2";
        Emote e1 = new EmoteImpl().setPattern("doggoThink2").setEmoteIDString("X123X").addIndices(0, 11).addIndices(25,36);
        Emote e2 = new EmoteImpl().setPattern("what").setEmoteIDString("A1_B").addIndices(12, 16);

        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);

        // Then
        checkEmotes(message, e1, e2);
    }

    private static void checkEmotes(TwitchMessage message, Emote... emotes){
        //Assert emote properties
		for( int i = 0; i < emotes.length; i++){
			Emote argEmote = emotes[i];
			Emote msgEmote = message.getEmotes().get(i);

			assertEquals( argEmote.getEmoteIDString(), msgEmote.getEmoteIDString() );
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
