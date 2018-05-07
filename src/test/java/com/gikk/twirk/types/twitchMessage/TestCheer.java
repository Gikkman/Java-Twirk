package com.gikk.twirk.types.twitchMessage;

import com.gikk.twirk.TestBiConsumer;
import com.gikk.twirk.TestResult;
import com.gikk.twirk.types.cheer.Cheer;
import com.gikk.twirk.types.cheer.CheerImpl;
import com.gikk.twirk.types.users.TwitchUser;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Assert;

/**
 *
 * @author Gikkman
 */
public class TestCheer {
    final static String USER_MESSAGE_NO_EMOTE = "@badges=;color=;display-name=Gikkman;emotes=;mod=0;room-id=31974228;subscriber=0;turbo=0;user-id=27658385;user-type= :gikkman!gikkman@gikkman.tmi.twitch.tv PRIVMSG #gikkman :userMessage";

    final static String CHEER_MESSAGE1 = "@badges=staff/1,bits/1000;bits=100;color=#FF69B4;display-name=dallas;emotes=;id=b34ccfc7-4977-403a-8a94-33c6bac34fb8;mod=0;room-id=1337;subscriber=0;tmi-sent-ts=1507246572675;turbo=1;user-id=1337;user-type=staff :ronni!ronni@ronni.tmi.twitch.tv PRIVMSG #gikkman :cheer100";
    final static String CHEER_MESSAGE2 = "@badges=staff/1,bits/1000;bits=400;color=#FF69B4;display-name=dallas;emotes=;id=b34ccfc7-4977-403a-8a94-33c6bac34fb8;mod=0;room-id=1337;subscriber=0;tmi-sent-ts=1507246572675;turbo=1;user-id=1337;user-type=staff :ronni!ronni@ronni.tmi.twitch.tv PRIVMSG #gikkman :cheer100 cheer100 cheer100 cheer100 Funny, ain't it?";

     public static void testPrivMsg(Consumer<String> twirkInput, TestBiConsumer<TwitchUser, TwitchMessage> test ) throws Exception{
        TestResult t1 = test.assign(TestCheer::testSingleCheer);
        twirkInput.accept(CHEER_MESSAGE1);
        t1.await();

        TestResult t2 = test.assign(TestCheer::testMultipleCheer);
        twirkInput.accept(CHEER_MESSAGE2);
        t2.await();

        TestResult t3 = test.assign(TestCheer::testNoCheer);
        twirkInput.accept(USER_MESSAGE_NO_EMOTE);
        t3.await();
     }

     private static boolean testSingleCheer(TwitchUser user, TwitchMessage message) {
         List<Cheer> cheers = new ArrayList<>();
         Cheer c = new CheerImpl(100, "cheer100");
         cheers.add(c);

         boolean res = test(message, true, 100, cheers);
         System.out.println("--- --- Single Cheer OK");
         return res;
     }

     private static boolean testMultipleCheer(TwitchUser user, TwitchMessage message) {
         List<Cheer> cheers = new ArrayList<>();
         Cheer c = new CheerImpl(100, "cheer100");
         cheers.add(c);
         cheers.add(c);
         cheers.add(c);
         cheers.add(c);

         boolean res =  test(message, true, 400, cheers);
         System.out.println("--- --- Multiple Cheer OK");
         return res;
     }

     private static boolean testNoCheer(TwitchUser user, TwitchMessage message) {
         List<Cheer> cheers = new ArrayList<>();

         boolean res =  test(message, false, 0, cheers);
         System.out.println("--- --- No Cheer OK");
         return res;
     }

     private static boolean test(TwitchMessage msg, boolean isCheer, int bits, List<Cheer> cheers) {
         Assert.assertEquals( "isCheer", isCheer, msg.isCheer() );
         Assert.assertEquals( "bits", bits, msg.getBits());
         Assert.assertEquals( "Cheers", cheers, msg.getCheers());
         return true;
     }
}
