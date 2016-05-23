package com.gikk.twirk.types.hostTarget;

/**Class for representing a HOSTTARGET from Twitch.<br><br>
 * 
 * A HOSTTARGET means that another channel started hosting you. HOSTTARGET comes in two forms: START and STOP. <ul>
 * <li>START - A host starts. <code>hoster</code> tells us who started hosting us and <code>viewerAmount</code> tells us how many viewers the hoster had
 * <li>STOP - A host ended.  <code>hoster</code> tells us who stopped hosting us and <code>viewerAmount</code> tells us how many viewers the hoster gave us
 * <br>
 * @author Gikkman
 *
 */
public interface HostTarget {
	public static enum HOSTTARGET_MODE{ START, STOP };
	
	/** Tells which MODE this HOSTTARGET has (START or STOP) */
	public HOSTTARGET_MODE getMode();
	/** Tells us which channel issued this HOSTTARGET */
	public String getHoster();
	/** Tells us how many viewers this HOSTTARGET affected (MODE:START adds viewers, MODE:STOP removes viewers) */
	public int getViewerCount();
	
}
