package com.gikk.twirk;

import com.gikk.twirk.types.TestNamelist;
import com.gikk.twirk.types.clearChat.TestClearChat;
import com.gikk.twirk.types.emote.TestEmote;
import com.gikk.twirk.types.hostTarget.TestHostTarget;
import com.gikk.twirk.types.mode.TestMode;
import com.gikk.twirk.types.notice.TestNotice;
import com.gikk.twirk.types.roomstate.TestRoomstate;
import com.gikk.twirk.types.twitchMessage.TestCheer;
import com.gikk.twirk.types.twitchMessage.TestMessage;
import com.gikk.twirk.types.usernotice.TestUsernotice;
import com.gikk.twirk.types.users.TestTwitchUser;
import com.gikk.twirk.types.users.TestUserstate;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSuit {
    static Twirk testTwirk;
    static TestTwirkListener tl;
    static Consumer<String> twirkIn;
    static TestSocket socket;

    public static int WAIT_TIME = 2;
    public static TimeUnit UNIT = TimeUnit.SECONDS;

    @BeforeClass
    public static void create() throws IOException, InterruptedException {
        System.out.println("!!! Setup stared");
        socket = new TestSocket();

        testTwirk = new TwirkBuilder("#testchan", "testbot", "password")
            .setSocket(socket)
            .setSSL(false)
            .setVerboseMode(false)
            .build();

        tl = new TestTwirkListener();
        testTwirk.addIrcListener(tl);

        // This allows us to send strings to Twirk, and twirk will interperate
        // it as a message from an IRC server
        twirkIn = socket::putMessage;

        System.out.println("!!! Setup completed");
   }

    @AfterClass
    public static void tearDown() {
        System.out.println("!!! Teardown started");
        socket.close();
        System.out.println("!!! Teardown completed");
    }

    @Test
    public void a_testConnect() throws Exception{
        System.out.println("--- Connect test started");
        // Make sure that the onConnect method was called
        TestResult res = tl.connectTest.assign((v) -> {
            return true;
        });

        // The responses from the server should arrive a few moments after
        // twirk.connect() has been called, hence the Thread that sleeps
        Thread t = new Thread( () -> {
            try {
                //Sleep until we suspect Twirk is awaiting input from the server
                Thread.sleep(100);
                twirkIn.accept(":tmi.twitch.tv 001 testbot :Welcome, GLHF!");
                twirkIn.accept(":tmi.twitch.tv 002 testbot :Your host is tmi.twitch.tv");
                twirkIn.accept(":tmi.twitch.tv 003 testbot :This server is rather new");
                twirkIn.accept(":tmi.twitch.tv 004 testbot :-");
            }
            catch (InterruptedException ex) {
               throw new RuntimeException(ex);
            }
        });
        t.start();

        Assert.assertTrue("Connection test", testTwirk.connect() && res.await());
        System.out.println("--- Connect test completed");
    }

    @Test
    public void testReconnect() throws Exception{
        System.out.println("--- Reconnect test started");
        //Make sure the onReconnect method was called
        TestResult res = tl.reconnectTest.assign((v) -> {
            return true;
        });
        // We want to make sure the RECONNECT command does not throw an exception
        Thread t = new Thread( () -> {
            try {
                Thread.sleep(100); //So we have the time to reach the res.await() bellow
                twirkIn.accept(":tmi.twitch.tv RECONNECT");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();

        Assert.assertTrue("Reconnect test", res.await());
        System.out.println("--- Reconnect test completed");
    }

	@Test
	public void testClearChat() throws Exception{
        System.out.println("--- Clearchat test started");
		TestClearChat.test(twirkIn, tl.clearchatTest);
        System.out.println("--- Clearchat test completed");
	}

     @Test
	public void testEmote() throws Exception{
        System.out.println("--- Emote test started");
		TestEmote.test(twirkIn, tl.privMsgTest);
        System.out.println("--- Emote test completed");
	}

	@Test
	public void testHost() throws Exception{
        System.out.println("--- Hosttarget test started");
		TestHostTarget.test(twirkIn, tl.hostTest);
        System.out.println("--- Hosttarget test completed");
	}

	@Test
	public void testMode() throws Exception{
        System.out.println("--- Mode test started");
		TestMode.test(twirkIn, tl.modeTest);
        System.out.println("--- Mode test completed");
	}

    @Test
    public void testNamelist() throws Exception{
        System.out.println("--- Namelist test started");
        TestNamelist.test(twirkIn, tl.namelistTest);
        System.out.println("--- Namelist test completed");
    }

	@Test
	public void testNotice() throws Exception{
        System.out.println("--- Notice test started");
		TestNotice.test(twirkIn, tl.noticeTest);
        System.out.println("--- Notice test completed");
	}

	@Test
	public void testPrivMsg() throws Exception{
        System.out.println("--- Privmsg test started");
		TestMessage.testPrivMsg(twirkIn, tl.privMsgTest);
        System.out.println("--- Privmsg test completed");
	}

    @Test
	public void testCheerMsg() throws Exception{
        System.out.println("--- Privmsg (cheer) test started");
		TestCheer.testPrivMsg(twirkIn, tl.privMsgTest);
        System.out.println("--- Privmsg (cheer) test completed");
	}

	@Test
	public void testRoomstate() throws Exception{
        System.out.println("--- Roomstate test started");
		TestRoomstate.test(twirkIn, tl.roomstateTest);
        System.out.println("--- Roomstate test completed");
	}

    @Test
	public void testTwitchUser() throws Exception{
        System.out.println("--- TwitchUser test started");
		TestTwitchUser.test(twirkIn, tl.privMsgTest);
        System.out.println("--- TwitchUser test completed");
	}

	@Test
	public void testUsernotice() throws Exception{
        System.out.println("--- Usernotice test started");
		TestUsernotice.test(twirkIn, tl.usernoticeTest);
        System.out.println("--- Usernotice test completed");
	}

    @Test
	public void testUserstate() throws Exception{
        System.out.println("--- Usernstate test started");
		TestUserstate.test(twirkIn, tl.userstateTest);
        System.out.println("--- Usernstate test completed");
	}

    @Test
    public void z_testDisconnect() throws Exception{
        System.out.println("--- Disconnect test started");
        // Make sure that the onDisconnect method was called
        TestResult res = tl.disconnectTest.assign( (v) -> {
            return true;
        });
        testTwirk.disconnect();
        Assert.assertTrue("Disconnect test",  res.await());
        System.out.println("--- Disconnect test completed");
    }
}
