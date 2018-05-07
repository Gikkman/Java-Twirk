package com.gikk.twirk.types.usernotice.subtype;

/**Class for representing a Raid event and its associated values
 *
 * @author Gikkman
 */
public interface Raid {

    /**Fetches the display name of the user whom initiated the raid.
     *
     * @return the display name of the raid initiator
     */
    public String getSourceDisplayName();

    /**Fetches the login name of the user whom initiated the raid. This will
     * also give you the name of the channel which the raid originated from.
     *
     * @return the login name of the raid initiator
     */
    public String getSourceLoginName();


    /**Fetches the number of users participating in the raid.
     *
     * @return number of raiders
     */
    public int getRaidCount();
}
