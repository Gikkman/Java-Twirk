package com.gikk.twirk.types.emote;

import java.util.LinkedList;

import com.gikk.twirk.enums.EMOTE_SIZE;


public class EmoteImpl implements Emote{
	private final static String EMOTE_URL_BASE 	= "http://static-cdn.jtvnw.net/emoticons/v1/";	
	
	private int emoteID;
	private LinkedList<EmoteIndices> indices = new LinkedList<EmoteIndices>();
	private String pattern;
	
	public EmoteImpl setEmoteID(int emoteID){
		this.emoteID = emoteID;
		return this;
	}
	
	public EmoteImpl setPattern(String pattern){
		this.pattern = pattern;
		return this;
	}
	
	public EmoteImpl addIndices(int begin, int end){
		this.indices.add( new EmoteIndices(begin, end) );
		return this;
	}
	
	@Override
	public int getEmoteID() {
		return emoteID;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public LinkedList<EmoteIndices> getIndices() {
		return indices;
	}
	
	public String getEmoteImageUrl(EMOTE_SIZE imageSize){
		return EMOTE_URL_BASE + emoteID + imageSize.value;
	}
	
	public String toString(){
		String out = emoteID + " " + ( pattern == null ? "NULL" : pattern) + "[ ";
		
		for( EmoteIndices index : indices )
			out += index.toString();
		
		out += " ]";
		
		return out;
	}
}
