package com.gikk.twirc.events;

import com.gikk.twirc.types.TwirkHostNotice;
import com.gikk.twirc.types.TwirkMessage;
import com.gikk.twirc.types.TwirkMode;
import com.gikk.twirc.types.TwirkNotice;
import com.gikk.twirc.types.TwirkRoomstate;
import com.gikk.twirc.types.TwirkUser;
import com.gikk.twirc.types.TwirkUserstate;

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
	public void onHost(TwirkHostNotice hostNotice) { }

	@Override
	public void onMode(TwirkMode mode) { }
	
	@Override
	public void onUserstate(TwirkUserstate userstate) { }
	
	@Override
	public void onRoomstate(TwirkRoomstate roomstate) { }

	@Override
	public void onUnknown(String line) { }	
}
