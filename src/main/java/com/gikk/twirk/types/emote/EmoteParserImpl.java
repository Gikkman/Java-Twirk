package com.gikk.twirk.types.emote;

import com.gikk.twirk.types.TagMap;
import com.gikk.twirk.types.TwitchTags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoteParserImpl implements EmoteParser {
	private static Pattern integerIdPattern = Pattern.compile("([0-9]+)");

	@Override
	public List<Emote> parseEmotes(TagMap tagMap, String content) {
		/* Emotes come in sets formatted like this:
		 *
		 * emotes=15614:0-6/4685:8-13,15-21		(Message = tmrToad tmrHat tmrHat)
		 *
		 * The first number indicates the emotes ID. The emote ID is follower by a :
		 * The numbers separated by a - indicates the indices in the message that makes up the emote.
		 *     If an emote appears several times in a message, the different index ranges are separated by a ,
		 *
		 * Thankfully the tagmap does the extraction for us, but this piece of information is left here for reference
		 */

		/* This segment is a head ache, but here is a basic idea of what it is doing:
		 * Iterate through the emotes-section.
		 *    The first part will be the emote ID. it is terminated by a :
		 *    The second part is the emote's start index. It is terminated by a -
		 *    The third part is the emote's end index. It is terminated EITHER by a / or a , or by the segment ending
		 *       If terminated by a , the emote is present more than once in the message. So the next part will be a new begin index
		 *       If terminated by a / there is another emote in the message. The next part is a new emote ID
		 *       If terminated by the segment ending we simply finish the last begin-end pair and we are done
		 */
		List<Emote> emotes = new ArrayList<>();
		String emotesString = tagMap.getAsString(TwitchTags.EMOTES);
		if(emotesString == null || emotesString.isEmpty()) return emotes;

		EmoteImpl emote = new EmoteImpl();
		StringBuilder str = new StringBuilder();
		String emoteID = "", beginIndex = "";
		for( char c : emotesString.toCharArray() ){
			switch(c) {
			case ':':
				emoteID = str.toString();
				str.setLength(0);
				break;
			case '-' :
				beginIndex = str.toString();
				str.setLength(0);
				break;
			case ',':
				addIndices(emote, beginIndex, str.toString() );
				str.setLength(0);
				break;
			case '/':
				finalizeEmote(content, emotes, emote, emoteID, beginIndex, str.toString() );
				emote = new EmoteImpl();
				str.setLength(0);
				break;
			default:
				str.append(c);
			}
		}
		//The emotes segment ended, so we finish the current emote's begin-end pair, then we're done
		finalizeEmote(content, emotes, emote, emoteID, beginIndex, str.toString());

		return emotes;
	}

	private static void finalizeEmote(String content, List<Emote> emotes, EmoteImpl emote, String emoteID, String beginIndex, String endIndex) {
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;	//The end index we receive from Twitch is inclusive, but Java is almost always exclusive
		emote.addIndices(begin, end);

		String emoteIntegerID = emoteID;
		Matcher matcher = integerIdPattern.matcher(emoteID);
		if(matcher.find())
			emoteIntegerID = matcher.group(0);

		emote.setEmoteIDString(emoteID);
		emote.setEmoteID( Integer.parseInt( emoteIntegerID ) );
		emote.setPattern( content.substring(begin, end).trim() );
		emotes.add(emote);
	}

	private static void addIndices(EmoteImpl emote, String beginIndex, String endIndex){
		int begin = Integer.parseInt( beginIndex );
		int end   = Integer.parseInt( endIndex ) + 1;
		emote.addIndices(begin, end);
	}
}
