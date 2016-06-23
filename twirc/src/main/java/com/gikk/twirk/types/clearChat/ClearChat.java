package com.gikk.twirk.types.clearChat;

import com.gikk.twirk.enums.CLEARCHAT_MODE;
import com.gikk.twirk.types.AbstractType;

/**Class for representing a CLEARCHAT from Twitch.<br><br>
 * 
 * A CLEARCHAT means that some parts of chat have been purged. This can be because a user was purged or banned, or because the entire
 * chat was clear. CLEARCHAT comes in two forms: USER and COMPLETE. <ul>
 * <li>USER - A user has been purged. <code>target</code> tells us the name of the user whom was purged
 * <li>COMPLETE - The channel has been purged. <code>target</code> will be empty.
 * <br><br>
 * @author Gikkman
 */
public interface ClearChat extends AbstractType{
	/** Retrieves the MODE of the CLEARCHAT. Can be USER, if a user was purged, or COMPLETE, if the entire chat was purged 
	 * 	
	 * @return {@link CLEARCHAT_MODE} of this CLEARCHAT
	 */
	public CLEARCHAT_MODE getMode();
	
	/** Retrieves the target of the CLEARCHAT. Might be a user name, if a user was purged, or empty, if the entire chat was purged 
	 * 
	 * @return Target of the CLEARCHAT, or empty if entire chat was cleared
	 */
	public String getTarget();
	
	/** Retrieves the duration of the ban/purge. Only applies to a CLEARCHAT of type USER. <br>
	 * If the CLEARCHAT is of type {@link CLEARCHAT_MODE#COMPLETE}, this will always return -1. If it is of type {@link CLEARCHAT_MODE#USER}, it
	 * will return the lenght of the timout, or -1 if it was a permanent ban.
	 * 
	 * @return Duration on the ban in seconds, or -1 if no duration was assigned.
	 */
	public int getDuration();
	
	/**Retrieves the reason for the timout/ban/clear. Might be empty
	 * 
	 * @return The reason for this CLEARCHAT. Might be empty
	 */
	public String getReason();
}
