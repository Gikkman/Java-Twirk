package com.gikk.twirk.types.hostTarget;

import com.gikk.twirk.enums.HOSTTARGET_MODE;
import com.gikk.twirk.types.AbstractType;

/**Class for representing a HOSTTARGET from Twitch.<br><br>
 * 
 * A HOSTTARGET means that we started hosting another. HOSTTARGET comes in two forms: START and STOP. <ul>
 * <li>START - A host starts. <code>hoster</code> tells us who we started hosting and <code>viewerAmount</code> tells us how many viewers we had
 * <li>STOP - A host ended.  <code>hoster</code> tells us who we stopped and <code>viewerAmount</code> tells us how many viewers we gave
 * <br><br>
 * @author Gikkman
 *
 */
public interface HostTarget extends AbstractType {
	
	/** Tells which MODE this HOSTTARGET has (START or STOP)
	 * 
	 * @return This {@link HOSTTARGET_MODE}
	 */
	public HOSTTARGET_MODE getMode();
	
	/** Tells us which channel we started hosting with this HOSTTARGET
	 * 
	 * @return Name of channel we started hosting
	 */
	public String getTarget();
	
	/** Tells us how many viewers this HOSTTARGET affected (MODE:START adds viewers, MODE:STOP removes viewers)
	 * 
	 * @return Amount of viewers we contribute/contributed to the target
	 */
	public int getViewerCount();
	
}
