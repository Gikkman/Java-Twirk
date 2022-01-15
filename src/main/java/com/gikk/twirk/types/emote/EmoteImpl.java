package com.gikk.twirk.types.emote;

import com.gikk.twirk.enums.EMOTE_SIZE;
import java.util.LinkedList;


class EmoteImpl implements Emote{
	private final static String EMOTE_URL_BASE 	= "http://static-cdn.jtvnw.net/emoticons/v1/";

	private String emoteIDString;
	private final LinkedList<EmoteIndices> indices = new LinkedList<>();
	private String pattern;

	public EmoteImpl setEmoteIDString(String emoteIDString){
		this.emoteIDString = emoteIDString;
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
	public String getEmoteIDString() {
		return emoteIDString;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public LinkedList<EmoteIndices> getIndices() {
		return indices;
	}


    @Override
	public String getEmoteImageUrl(EMOTE_SIZE imageSize){
		return EMOTE_URL_BASE + emoteIDString + imageSize.value;
	}


    @Override
	public String toString(){
		StringBuilder out = new StringBuilder(emoteIDString + " " + ( pattern == null ? "NULL" : pattern) + "[ ");

		for( EmoteIndices index : indices ) {
            out.append(index.toString());
        }
		out.append(" ]");


		return out.toString();
	}
}
