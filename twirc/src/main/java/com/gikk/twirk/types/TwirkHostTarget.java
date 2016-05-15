package com.gikk.twirk.types;

/**Class for representing a HOSTTARGET from Twitch.<br><br>
 * 
 * A HOSTTARGET means that another channel started hosting you. HOSTTARGET comes in two forms: START and STOP. <ul>
 * <li>START - A host starts. <code>hoster</code> tells us who started hosting us and <code>viewerAmount</code> tells us how many viewers the hoster had
 * <li>STOP - A host ended.  <code>hoster</code> tells us who stopped hosting us and <code>viewerAmount</code> tells us how many viewers the hoster gave us
 * <br>
 * @author Gikkman
 *
 */
public class TwirkHostTarget {
	public static enum MODE{ START, STOP };	
	
	/** Tells which MODE this HOSTTARGET has (START or STOP) */
	public final MODE mode;
	/** Tells us which channel issued this HOSTTARGET */
	public final String hoster;
	/** Tells us how many vierwers this HOSTTARGET affected (MODE:START adds viewers, MODE:STOP removes viewers) */
	public final int viwerAmount;
	
	/**Creates a new TwirkHostTarget from a {@link TwirkMessage}. The user is responsible is responsible
	 * for making sure that the {@link TwirkMessage}'s command is HOSTTARGET
	 * 
	 * @param message The TwirkMessage
	 */
	public TwirkHostTarget(TwirkMessage message){
		this.hoster = message.getTarget().substring(1); //Remove the #
		this.mode = message.getContent().startsWith("-") ? MODE.STOP : MODE.START;
		
		int viewerAmountIndex = message.getContent().indexOf(" ");
		if( viewerAmountIndex == -1 )
			this.viwerAmount = 0;
		else
			this.viwerAmount = Integer.parseInt( message.getContent().substring(viewerAmountIndex).trim() );
				
	}
}
