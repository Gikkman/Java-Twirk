package com.gikk.twirk.types.twitchMessage;

/**Constructs a {@link TwitchMessage} object. To create a {@link TwitchMessage} object, call the {@link #build(String)} method
 * 
 * @author Gikkman
 *
 */
public interface TwitchMessageBuilder {
	
	/**Constructs a new {@link TwitchMessage} object from a chat line received from Twitch.<br>
	 * A valid chat line has 2-5 segments:<br>
	 * <code>@TAG<optional> :PREFIX COMMAND TARGET<optional> :CONTENT<optional></code>
	 * <br><br>
	 * From this chat line, we create a {@link TwitchMessage}. If certain parts are not present, the corresponding
	 * <code>get</code> method on the {@link TwitchMessage} will return an empty string.
	 * 
	 * @param chatLine The chat line, <b>exactly</b> as received from Twitch
	 * @return A {@link TwitchMessage}, or <code>null</code> if a {@link TwitchMessage} could not be created
	 */
	public TwitchMessage build(String chatLine);
}
