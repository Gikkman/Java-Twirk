package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.types.hostTarget.HostTarget.HOSTTARGET_MODE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

public class HostTargetBuilderDefault implements HostTargetBuilder {

	HOSTTARGET_MODE mode;
	String hoster;
	int viwerAmount;
	
	@Override
	public HostTarget build(TwitchMessage message) {
		this.hoster = message.getTarget().substring(1); //Remove the #
		this.mode = message.getContent().startsWith("-") ? HOSTTARGET_MODE.STOP : HOSTTARGET_MODE.START;
		
		int viewerAmountIndex = message.getContent().indexOf(" ");
		if( viewerAmountIndex == -1 )
			this.viwerAmount = 0;
		else
			this.viwerAmount = Integer.parseInt( message.getContent().substring(viewerAmountIndex).trim() );
		
		return new HostTargetImpl(this);
	}
	
}
