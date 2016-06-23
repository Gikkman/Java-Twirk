package com.gikk.twirk.types.users;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.events.TwirkListener;

/**Class for representing information about a user<br>
 * Userstate's are received in two different ways:
 * <ul>
 * <li> Whenever you send a message to Twitch's IRC server, you will get a response with YOUR UserState. This response will trigger the {@link TwirkListener#onUserstate(TwirkUserstate)} event in the {@link TwirkListener} class.
 * <li> Whenever a user sends a PRIVMSG or a WHISPER that you can see, that message is also accompanied by a UserState for THAT user. That UserState is wrapped in a {@link TwitchUserImpl} object, and will be be one of the arguments for the {@link TwirkListener#onPrivMsg(TwitchUserImpl, TwitchMessageImpl)} or {@link TwirkListener#onWhisper(TwitchUserImpl, TwitchMessageImpl)} event
 * </ul>
 * @author Gikkman
 *
 */
class UserstateImpl implements Userstate {
	
	private final int color;
	private final String displayName;
	private final boolean isMod;
	private final boolean isSub;
	private final boolean isTurbo;
	private final USER_TYPE userType;
	private final int[] emoteSets;
	private final String rawLine;
	
	UserstateImpl( GikkDefault_UserstateBuilder builder ) {
		this.color = builder.color;
		this.displayName = builder.displayName;
		this.isMod = builder.isMod;
		this.isSub = builder.isSub;
		this.isTurbo = builder.isTurbo;
		this.userType = builder.userType;
		this.emoteSets = builder.emoteSets;
		this.rawLine = builder.rawLine;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean isMod() {
		return isMod;
	}

	@Override
	public boolean isSub() {
		return isSub;
	}

	@Override
	public boolean isTurbo() {
		return isTurbo;
	}

	@Override
	public USER_TYPE getUserType() {
		return userType;
	}

	@Override
	public int[] getEmoteSets() {
		return emoteSets;
	}

	@Override
	public String getRaw() {
		return rawLine;
	}
}
