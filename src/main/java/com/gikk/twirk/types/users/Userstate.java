package com.gikk.twirk.types.users;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.AbstractType;

/**Class for representing a CLEARCHAT from Twitch.<br><br>
 * 
 * Twitch sends us USERSTATE as a response whenever we send them a message. The USERSTATE message will tell
 * us certain properties related to ourselves, such as our display name, our color, if we're mod and so on. Objects
 * of this class can thus be used to inspect what properties we have on Twitch's side.<br><br>
 * 
 * For documentation about how Twitch communicates via IRC, 
 * see <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">
 * 				https://github.com/justintv/Twitch-API/blob/master/IRC.md </a>
 * 
 * @author Gikkman
 *
 */
public interface Userstate extends AbstractType{
	
	/**Retrieves our display color, as a hex-number. <br>
	 * If we have no color set on Twitch's side, a semi-random color will be generated
	 * 
	 * @return Our display color
	 */
	public int getColor();
	
	/**Retrieves our display name, as seen in chat on Twitch
	 * 
	 * @return Our display name
	 */
	public String getDisplayName();
	
	/**Retrieves information on whether we are a mod in this channel or not.
	 *  
	 * @return <code>true</code> if we are mod
	 */
	public boolean isMod();
	
	/**Retrieves information on whether we are a sub in this channel or not.
	 *  
	 * @return <code>true</code> if we are sub
	 */
	public boolean isSub();
	
	/**Retrieves information on whether we have Turbo, or not
	 * 
	 * @return <code>true</code> if we have turbo
	 */
	public boolean isTurbo();
	
	/**Retrieves information on what type of user we are in this channel. See {@link USER_TYPE}
	 * 
	 * @return Our {@link USER_TYPE}
	 */
	public USER_TYPE getUserType();
	
	/**Retrieves the emote sets that are available to this account. You can uses the emote set's names to request information
	 * about those emotes from Twitch.<br><br>
	 * 
	 * For example, if we have emote set 0, we can request information about the emotes in that set by visiting:<br>
	 * <a href="https://api.twitch.tv/kraken/chat/emoticon_images?emotesets=0">https://api.twitch.tv/kraken/chat/emoticon_images?emotesets=0</a>
	 *  A request to that adress will return a JSON file with all emotes in that set.<br><br>
	 *  
	 * You can request information about several sets at once, by listing emote sets after one another separated by a comma ( , )<br>
	 * For example: <a href="https://api.twitch.tv/kraken/chat/emoticon_images?emotesets=0,12">https://api.twitch.tv/kraken/chat/emoticon_images?emotesets=0,12</a> 
	 * 
	 * @return Our emote sets
	 */
	public int[] getEmoteSets();

}
