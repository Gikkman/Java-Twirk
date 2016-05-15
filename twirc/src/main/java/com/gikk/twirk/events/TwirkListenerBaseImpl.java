package com.gikk.twirk.events;

import com.gikk.twirk.types.TwirkClearChat;
import com.gikk.twirk.types.TwirkHostTarget;
import com.gikk.twirk.types.TwirkMessage;
import com.gikk.twirk.types.TwirkMode;
import com.gikk.twirk.types.TwirkNotice;
import com.gikk.twirk.types.TwirkRoomstate;
import com.gikk.twirk.types.TwirkUser;
import com.gikk.twirk.types.TwirkUserstate;

/**Convenience class.<br>
 * Instead if extending the TwirkListener interface, you can instead extend this class. That
 * way, you only need to implement those methods that you want to use. This class implements
 * all required methods, but leaves the method body blank. Thus, you don't need to call the
 * {@code .super()} methods at all.
 * 
 * @author Gikkman
 *
 */
public abstract class TwirkListenerBaseImpl implements TwirkListener{

	@Override
	public void onAnything(String line) { }

	@Override
	public void onPrivMsg(TwirkUser sender, TwirkMessage message) {	}

	@Override
	public void onWhisper(TwirkUser sender, TwirkMessage message) {	}

	@Override
	public void onJoin(String joinedNick) { }

	@Override
	public void onPart(String partedNick) { }

	@Override
	public void onConnect() { }

	@Override
	public void onDisconnect() { }

	@Override
	public void onNotice(TwirkNotice notice) { }
	
	@Override
	public void onHost(TwirkHostTarget hostNotice) { }

	@Override
	public void onMode(TwirkMode mode) { }
	
	@Override
	public void onUserstate(TwirkUserstate userstate) { }
	
	@Override
	public void onRoomstate(TwirkRoomstate roomstate) { }
	
	@Override
	public void onClearChat(TwirkClearChat clearChat) { }

	@Override
	public void onUnknown(String line) { }	
}
