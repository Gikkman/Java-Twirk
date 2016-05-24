package com.gikk.twirk.types.hostTarget;

/**Class for representing a HOSTTARGET from Twitch.<br><br>
 * 
 * A HOSTTARGET means that we started hosting another. HOSTTARGET comes in two forms: START and STOP. <ul>
 * <li>START - A host starts. <code>hoster</code> tells us who we started hosting and <code>viewerAmount</code> tells us how many viewers we had
 * <li>STOP - A host ended.  <code>hoster</code> tells us who we stopped and <code>viewerAmount</code> tells us how many viewers we gave
 * <br>
 * @author Gikkman
 *
 */
public interface HostTarget {
	public static enum HOSTTARGET_MODE{ START, STOP };
	
	/** Tells which MODE this HOSTTARGET has (START or STOP) */
	public HOSTTARGET_MODE getMode();
	/** Tells us which channel we started hosting with this HOSTTARGET */
	public String getHoste();
	/** Tells us how many viewers this HOSTTARGET affected (MODE:START adds viewers, MODE:STOP removes viewers) */
	public int getViewerCount();
	
}
