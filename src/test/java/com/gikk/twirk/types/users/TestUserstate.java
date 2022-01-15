package com.gikk.twirk.types.users;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUserstate {
    /* *****************************************************************************************************************
     * Test display names
     **************************************************************************************************************** */
    @Test
    public void displayNameTest_givenCustomDisplayName_thenDisplayNameIsParsedCorrect() {
        // Given
        String input = "@color=0x1234;display-name=GikkBot :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals("GikkBot", state.getDisplayName());
    }
    @Test
    public void displayNameTest_givenDefaultDisplayName_thenDisplayNameIsParsedCorrect() {
        // Given
        String input = "@color=0x1234;display-name=gikkman :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals("gikkman", state.getDisplayName());
    }

    /* *****************************************************************************************************************
     * Test colors
     **************************************************************************************************************** */
    @Test
    public void colorTest_givenColor_thenColorIsParsed() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(0xFF69B4, state.getColor());
    }

    /* *****************************************************************************************************************
     * Test emote sets
     **************************************************************************************************************** */
    @Test
    public void emoteSetTest_givenNoEmoteSets_thenEmptyEmoteSetIsReturned() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertArrayEquals(new String[0], state.getEmoteSets());
    }

    @Test
    public void emoteSetTest_givenEmoteSets_thenCorrectSetsAreReturned() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;emote-sets=0,564265402,f302a315-4fac-4e36-a9f3-3c7915933eb1 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertArrayEquals(new String[]{"0","564265402","f302a315-4fac-4e36-a9f3-3c7915933eb1"}, state.getEmoteSets());
    }

    /* *****************************************************************************************************************
     * Test isSub
     **************************************************************************************************************** */
    @Test
    public void isSubTest_givenUserIsSub_thenIsSubIsTrue() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;subscriber=1 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertTrue(state.isSub());
    }

    @Test
    public void isSubTest_givenUserIsNotSub_thenIsSubIsFalse() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;subscriber=0 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertFalse(state.isSub());
    }

    @Test
    public void isSubTest_givenSubIsNotPresent_thenIsSubIsFalse() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertFalse(state.isSub());
    }

    /* *****************************************************************************************************************
     * Test isMod
     **************************************************************************************************************** */
    @Test
    public void isModTest_givenUserIsMod_thenIsModIsTrue() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;mod=1 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertTrue(state.isMod());
    }

    @Test
    public void isModTest_givenUserIsNotMod_thenIsModIsFalse() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;mod=0 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertFalse(state.isMod());
    }

    @Test
    public void isModTest_givenModIsNotPresent_thenIsModIsFalse() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertFalse(state.isMod());
    }

    /* *****************************************************************************************************************
     * Test isOwner
     **************************************************************************************************************** */
    @Test
    public void isOwnerTest_givenDisplayNameMatchesChannel_thenUserIsOwner() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.OWNER, state.getUserType());
    }

    @Test
    public void isOwnerTest_givenDisplayNameDoesNotMatchesChannel_thenUserIsNotOwner() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkbot :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertNotEquals(USER_TYPE.OWNER, state.getUserType());
    }

    /* *****************************************************************************************************************
     * Test user type
     **************************************************************************************************************** */
    @Test
    public void userTypeTest_givenUserIsOwner_thenIrregardlessOfUserTypeField_UserTypeIsOwner() {
        // Given
        String input = "@color=#FF69B4;display-name=Gikkman;subscriber=1;mod=1;user-type=mod :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.OWNER, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsMod_thenUserTypeIsMod() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot;subscriber=1;mod=1;user-type=mod :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.MOD, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsAdmin_thenUserTypeIsAdmin() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot;subscriber=1;mod=1;user-type=global_mod :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.GLOBAL_MOD, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsGloblaMod_thenUserTypeIsGlobalMod() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot;subscriber=1;user-type=admin :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.ADMIN, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsStaff_thenUserTypeIsStaff() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot;user-type=staff :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.STAFF, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsSubscriber_thenUserTypeIsSub() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot;subscriber=1 :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.SUBSCRIBER, state.getUserType());
    }

    @Test
    public void userTypeTest_givenUserIsNeitherOwnerModGlobalAdminOrSub_thenUserTypeIsDefault() {
        // Given
        String input = "@color=#FF69B4;display-name=GikkBot :tmi.twitch.tv USERSTATE #gikkman";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertEquals(USER_TYPE.DEFAULT, state.getUserType());
    }

    /* *****************************************************************************************************************
     * Test badges
     **************************************************************************************************************** */
    @Test
    public void badgeTest_givenUserHasNoBadges_thenBadgeArrayIsEmpty() {
        // Given
        String input = "@badge-info=;badges=;color=#0D4200;display-name=ronni :tmi.twitch.tv USERSTATE #dallas";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertArrayEquals(new String[0], state.getBadges());
    }

    @Test
    public void badgeTest_givenBadgesIsMissing_thenBadgeArrayIsEmpty() {
        // Given
        String input = "@color=#0D4200;display-name=ronni :tmi.twitch.tv USERSTATE #dallas";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertArrayEquals(new String[0], state.getBadges());
    }

    @Test
    public void badgeTest_givenUserHasBadges_thenBadgeArrayIsFilled() {
        // Given
        String input = "@badge-info=;badges=staff/1,subscriber/1;color=#0D4200;display-name=ronni :tmi.twitch.tv USERSTATE #dallas";
        // When
        TwitchMessage message = TwitchMessageBuilder.getDefault().build(input);
        Userstate state = UserstateBuilder.getDefault().build(message);
        // Then
        assertArrayEquals(new String[]{"staff/1","subscriber/1"}, state.getBadges());
    }
}
