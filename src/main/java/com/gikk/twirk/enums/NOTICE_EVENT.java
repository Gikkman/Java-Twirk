package com.gikk.twirk.enums;

import com.gikk.twirk.types.notice.Notice;
import java.util.HashMap;
import java.util.Map;

/** Represents different types of NOTICE events.<br>
 * See <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">
 * 				https://github.com/justintv/Twitch-API/blob/master/IRC.md </a> <br><br>
 *
 * <p>
 * Most NOTICE_EVENT does not have a parameter to tell you what the target of a
 * notice was. For example, the ALREADY_BANNED notice does not have a parameter
 * for telling which user that was already banned. Instead, you have to look at
 * the message that comes with the Notice object.
 * <p>
 * Each NOTICE_EVENT has a short doc-string which tells you how the message that
 * comes with that event should look like. If a descriptor is placed inside []
 * brackets, it indicates that that value holds a parameter.
 *
 */
public enum NOTICE_EVENT {
    /** [User] is already banned in this room. */
    ALREADY_BANNED("already_banned"),
    /** This room is not in emote-only mode. */
    ALREADY_EMOTES_ONLY_OFF("already_emote_only_off"),
    /** This room is already in emote-only mode. */
    ALREADY_EMOTES_ONLY_ON("already_emote_only_on"),
    /** This room is not in r9k mode. */
    ALREADY_R9K_OFF("already_r9k_off"),
    /** This room is already in r9k mode. */
    ALREADY_R9K_ON("already_r9k_on"),
    /** This room is not in subscribers-only mode. */
    ALREADY_SUBS_OFF("already_subs_off"),
    /** This room is already in subscribers-only mode. */
    ALREADY_SUBS_ON("already_subs_on"),
    /** This channel is hosting [Channel}. */
    BAD_HOST_HOSTING("bad_host_hosting"),
	/** [User] is not banned from this room. (See #getTargetDisplayName())*/
    BAD_UNBAN_NO_BAN("bad_unban_no_ban"),
    /** [User] is banned from this room. */
    BAN_SUCCESS("ban_success"),
    /** This room is no longer in emote-only mode. */
    EMOTE_ONLY_OFF("emote_only_off"),
    /** This room is now in emote-only mode. */
    EMOTE_ONLY_ON("emote_only_on"),
    /** Exited host mode. */
    HOST_OFF("host_off"),
    /** Now hosting [Channel]. */
    HOST_ON("host_on"),
    /** There are [Number] host commands remaining this half hour. */
    HOST_REMAINING("hosts_remaining"),
    /** This channel is suspended. */
    MESSAGE_CHANNEL_SUSPENDED("msg_channel_suspended"),
    /**This room is no longer in r9k mode. */
    R9K_OFF("r9k_off"),
    /** This room is now in r9k mode. */
    R9K_ON("r9k_on"),
    /** This room is no longer in slow mode. */
    SLOW_OFF("slow_off"),
    /** This room is now in slow mode. You may send messages every [Seconds] seconds.*/
    SLOW_ON("slow_on"),
    /** This room is no longer in subscribers-only mode. */
    SUBSCRIBER_MODE_OFF("subs_off"),
    /** This room is now in subscribers-only mode. */
    SUBSCRIBER_MODE_ON("subs_on"),
    /** [User] has been timed out for [Seconds] seconds. */
    TIMEOUT_SUCCESS("timeout_success"),
    /**	[User] is no longer banned from this chat room. */
    UNBAN_SUCCESS("unban_success"),
    /** Unrecognized command: [Command] */
    UNRECOGNIZED_COMMAND("unrecognized_cmd"),

	/**We default to this one if we do not recognize this NOTICE's event type. Since Twitch uses a lot of different undocumented
	 * NOTICE types, there is need for a event type that works as a catch-all. <br>
	 * Consider parsing the {@link Notice#getRawNoticeID()}.
	 */
	OTHER("");

    private final String msgID;
    private NOTICE_EVENT(String msgID){
        this.msgID = msgID;
    }

    private final static Map<String, NOTICE_EVENT> mapping = new HashMap<>();
    static{
        for(NOTICE_EVENT n : values()){
            mapping.put(n.msgID, n);
        }
    }

    public static NOTICE_EVENT of(String msgID){
        return mapping.getOrDefault(msgID, OTHER);
    }
};