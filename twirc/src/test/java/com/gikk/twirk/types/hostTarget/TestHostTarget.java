package com.gikk.twirk.types.hostTarget;

import static org.junit.Assert.assertTrue;

import com.gikk.twirk.types.hostTarget.HostTarget.HOSTTARGET_MODE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.twitchMessage.TwitchMessageBuilderDefault;

public class TestHostTarget {
	private final static String START_HOST = ":tmi.twitch.tv HOSTTARGET #gikkman :gikkbot 1";	//Gikkman host Gikkbot for 1 viewer
	private final static String STOP_HOST = ":tmi.twitch.tv HOSTTARGET #gikkman :- 0";			//Gikkbot stopped hosting for 0 viewers
	
	public static void test(){
		doTest(START_HOST, HOSTTARGET_MODE.START, 1, "gikkbot" );
		doTest(STOP_HOST, HOSTTARGET_MODE.STOP, 0, "");
	}

	private static void doTest(String STRING, HOSTTARGET_MODE MODE, int viewers, String target) {
		TwitchMessage message = new TwitchMessageBuilderDefault().build(STRING);
		HostTarget host = new HostTargetBuilderDefault().build(message);
		
		assertTrue("Got: "+ host.getTarget() 	  + " Expected: " + target,  host.getTarget().contentEquals(target));
		assertTrue("Got: "+ host.getViewerCount() + " Expected: " + viewers, host.getViewerCount() == viewers);
		assertTrue("Got: "+ host.getMode()		  + " Expected: " + MODE , 	 host.getMode() == MODE);
		assertTrue("Got: "+ host.getRaw() 		  + " Expected: " + STRING , host.getRaw().contentEquals(STRING));
		
	}
}
