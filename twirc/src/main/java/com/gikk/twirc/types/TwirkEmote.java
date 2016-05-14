package com.gikk.twirc.types;

import java.util.LinkedList;

/**Class for handling TwitchEmotes, which can be embedded
 * into chat messages.
 * 
 * @author Gikkman
 *
 */
public class TwirkEmote {
	public int emoteID;
	public LinkedList<EmoteIndices> indices = new LinkedList<EmoteIndices>();
	public String pattern;
	
	public TwirkEmote(){
	}
	
	public void setEmoteID(int emoteID){
		this.emoteID = emoteID;
	}
	
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
	
	public void addIndices(int begin, int end){
		this.indices.add( new EmoteIndices(begin, end) );
	}
	
	public String toString(){
		String out = emoteID + " " + ( pattern == null ? "NULL" : pattern) + "[ ";
		
		for( EmoteIndices index : indices )
			out += index.toString();
		
		out += " ]";
		
		return out;
	}
	
	public class EmoteIndices{
		public final int beingIndex;
		public final int endIndex;
		
		private EmoteIndices(int begin, int end){
			this.beingIndex = begin;
			this.endIndex = end;
		}
		
		public String toString(){
			return "(" +beingIndex + "," + endIndex + ")";
		}
	}
}
