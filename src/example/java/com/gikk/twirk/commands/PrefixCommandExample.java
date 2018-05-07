package com.gikk.twirk.commands;

import java.util.Calendar;
import java.util.Date;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public class PrefixCommandExample extends CommandExampleBase {
	private final static String patternA = "!timezone";
	private final static String patternB = "!time";
	
	private final Twirk twirk;
	
	public PrefixCommandExample(Twirk twirk) {
		super(CommandType.PREFIX_COMMAND);
		this.twirk = twirk;
	}

	@Override
	protected String getCommandWords() {
		return patternA + "|" + patternB;
	}

	@Override
	protected USER_TYPE getMinUserPrevilidge() {
		return USER_TYPE.DEFAULT;
	}

	@Override
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
		if( command.equals(patternA) )
			twirk.channelMessage(sender.getName() +": Local time zone is " + Calendar.getInstance().getTimeZone().getDisplayName());
		else if( command.equals(patternB) )
			twirk.channelMessage(sender.getName() +": Local time is " + new Date().toString() );
			
	}	
	
}
