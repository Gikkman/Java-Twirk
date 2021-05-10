package com.gikk.twirk.types.clearMsg;

class ClearMsgImpl implements ClearMsg{

	public final String sender;
	public final String msgId;
	public final String msgContents;
	private final String rawLine;
	
	ClearMsgImpl(DefaultClearMsgBuilder builder){
		this.sender = builder.sender;
		this.msgId = builder.msgId;
		this.msgContents = builder.msgContents;
		this.rawLine = builder.rawLine;
	}

	@Override
	public String getSender() {
		return this.sender;
	}

	@Override
	public String getTargetMsgId() {
		return this.msgId;
	}

	@Override
	public String getTargetMessageContent() {
		return this.msgContents;
	}
	
	@Override
	public String getRaw() {
		return this.rawLine;
	}

}
