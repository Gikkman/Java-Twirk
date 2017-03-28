package com.gikk.twirk.types;

/**This is a comprehensive list of all <code>TAG</code>  identifiers that a message from Twitch can have. 
 * 
 * @author Gikkman
 *
 */
public class _IDENTIFIERS {
	
	/**Denotes the message's unique ID 
	 */
	public static final String ID			= "id";
	
	/**Denotes the user's unique ID 
	 */
	public static final String USER_ID 		= "user-id";

	/**The users display name (on Twitch). Escaped like the following:<br><br><table style="width:500"><tr>
	 *<td><b>CHARACTER		</b></td><td><b>ESCAPED BY</b></td><tr>
	 *<td>; (semicolon)	</td><td>\: (backslash and colon)</td><tr>
	 *<td>SPACE			</td><td>\s </td><tr>
	 *<td>\				</td><td>\\ </td><tr>
	 *<td>CR			</td><td>\r </td><tr>
	 *<td>LF			</td><td>\n </td><tr>
	 *<td>all others	</td><td>the character itself</td></tr></table> 
	 */
	public static final String DISPLAY_NAME	= "display-name";
	
	/**Denotes badges this message has. Can be:<br>  
	 * {@code staff, admin, global_mod, moderator, subscriber, turbo } and/or {@code bits}
	 */
	public static final String BADGES 		= "badges";
	
	/**Denotes the users color. This comes as a hexadecimal value. Can be empty if never set by the user 
	 */
	public static final String COLOR 		= "color";
	
	/**Denotes if the user is a subscriber or not. <code>1</code> for <code>true</code>, <code>0</code> for <code>false</code> 
	 */
	public static final String IS_SUB 		= "subscriber";
	
	/**Denotes if the user is a moderator or not. <code>1</code> for <code>true</code>, <code>0</code> for <code>false</code> 
	 */
	public static final String IS_MOD 		= "mod";
	
	/**Denotes if the user has turbo or not. <code>1</code> for <code>true</code>, <code>0</code> for <code>false</code> 
	 */
	public static final String IS_TURBO 	= "turbo";
	
	/**Is either <code>empty, mod, global_mod, admin</code> or <code>staff</code>.
	*  <li>The broadcaster can have any of these, including empty.
	*/
	public static final String USERTYPE 	= "user-type";
	
	/**Contains information to replace text in the message with the emote images and <b>can be empty</b>. The format is as follows:<br><br>
	 * <code>emote_id:first_index-last_index,another_first-another_last/another_emote_id:first_index-last_index</code><br><br>
	 *
	 * <code>emote_id</code> is the number to use in this URL: <code>http://static-cdn.jtvnw.net/emoticons/v1/:emote_id/:size</code> (<i>size is 1.0, 2.0 or 3.0</i>)<br><br>
	 *
	 * Emote indexes are simply character indexes. <code>ACTION</code> does not count and indexing starts from the first character that is part of the user's "actual message".<br>
	 * Both start and end is inclusive.  If the message is "Kappa" (emote id 25), start is from character 0 (K) to character 4 (a). 
	 */
	public static final String EMOTES		 = "emotes"; 
	
	/**Contains your emote set, which you can use to request a subset of <a href="https://github.com/justintv/Twitch-API/blob/master/v3_resources/chat.md#get-chatemoticon_images">/chat/emoticon_images</a>
	 * <li>Always contains at least {@code 0}
	 * <li>Ex: <code>https://api.twitch.tv/kraken/chat/emoticon_images?emotesets=0,33,50,237,793,2126,3517,4578,5569,9400,10337,12239</code>
	 */
	public static final String EMOTE_SET	= "emote-sets";
	
	/**The user's login name. Can be different from the user's display name
	 */
	public final static String LOGIN_NAME 	= "login";
	
	/**Identifies what kind of notice it was.
	 */
	public final static String MESSAGE_ID 	= "msg-id";	
	
	/**The message printed in chat along with this notice.
	 */
	public final static String SYSTEM_MESSAGE = "system-msg";
	
	/**The number of consecutive months the user has subscribed for in a resub notice. 
	 */
	public final static String MONTHS 		= "msg-param-months";	
	
	/**Denotes the room's ID which the message what sent to. Each room's ID is unique 
	 */
	public static final String ROOM_ID 		= "room-id";
	
	/**<code>broadcaster-lang</code> is the chat language when <a href="https://blog.twitch.tv/your-channel-your-chat-your-language-80306ab98a59#.i4rpk1gn1">broadcaster language mode</a> is enabled, and empty otherwise. <br>
	 * A few examples would be en for English, fi for Finnish and es-MX for Mexican variant of Spanish 
	 */
	public static final String ROOM_LANG 	= "broadcaster-lang";
	
	/**Messages with more than 9 characters must be unique. 0 means disabled, 1 enabled 
	 */
	public static final String R9K_ROOM 	= "r9k";
	
	/**Only subscribers and moderators can chat. 0 disabled, 1 enabled. 
	 */
	public static final String SUB_ONLY_ROOM= "subs-only";
	
	/**Determines how many seconds chatters without moderator privileges must wait between sending messages 
	 */
	public static final String SLOW_DURATION= "slow";
	
	/**The duration of the timeout in seconds. The tag is omitted on permanent bans. 
	 */
	public static final String BAN_DURATION = "ban-duration";
	
	/**The reason the moderator gave for the timeout or ban.
	 */
	public static final String BAN_REASON   = "ban-reason";
}
