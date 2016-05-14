package com.gikk.twirc.events;

import com.gikk.twirc.types.TwirkHostNotice;
import com.gikk.twirc.types.TwirkMessage;
import com.gikk.twirc.types.TwirkMode;
import com.gikk.twirc.types.TwirkNotice;
import com.gikk.twirc.types.TwirkRoomstate;
import com.gikk.twirc.types.TwirkUser;
import com.gikk.twirc.types.TwirkUserstate;

public interface TwirkListener {	
	
	public void onAnything( String line );
	
	public void onPrivMsg( TwirkUser sender, TwirkMessage message );
	
	public void onWhisper( TwirkUser sender, TwirkMessage message );
	
	public void onJoin( String joinedNick );
	
	public void onPart( String partedNick );

	public void onConnect();
	
	public void onDisconnect();
	
	public void onNotice( TwirkNotice notice );
	
	public void onHost( TwirkHostNotice hostNotice );
	
	public void onMode( TwirkMode mode );

	public void onUserstate( TwirkUserstate userstate );

	public void onRoomstate( TwirkRoomstate roomstate );

	public void onUnknown( String line );
}
