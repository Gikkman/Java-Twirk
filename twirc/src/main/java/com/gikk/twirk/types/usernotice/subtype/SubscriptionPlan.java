package com.gikk.twirk.types.usernotice.subtype;

/**
 *
 * @author Gikkman
 */
public enum SubscriptionPlan {
    /** Subscribed with Twitch Prime
     */
    SUB_PRIME("Prime", "Prime"),

    /** Subscribed with a 4.99$ plan
     */
    SUB_LOW("1000", "4.99$"),

    /** Subscribed with a 9.99$ plan
     */
    SUB_MID("2000", "9.99$"),

    /** Subscribed with a 24.99$ plan
     */
    SUB_HIGH("3000", "24.99$");

    private final String message;
    private final String value;
    private SubscriptionPlan(String msg, String val){
        this.message = msg;
        this.value = val;
    }

    public String getValue(){
        return value;
    }

    /**Attempts to parse which Subscription plan that's been used, given the
     * subscription notice message.
     * <p>
     * If no match is found (i.e. the input parameter is unknown) this defaults
     * to returning {@link SUB_LOW}
     *
     * @param paramSubPlan the sub-plan message
     * @return a SubscriptionPlan
     */
    public static SubscriptionPlan of(String paramSubPlan){
        for(SubscriptionPlan s : values()){
            if(s.message.equals(paramSubPlan)) {
                return s;
            }
        }
        return SUB_LOW;
    }
}
