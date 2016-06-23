package com.gikk.twirk.enums;

import com.gikk.twirk.types.notice.Notice;

/** Represents different types of NOTICE events.<br> 
 * See <a href="https://github.com/justintv/Twitch-API/blob/master/IRC.md">
 * 				https://github.com/justintv/Twitch-API/blob/master/IRC.md </a> <br><br>
 * 
 * There are several other types of NOTICE events, such as 'usage_subs_on', and 'already_r9k_on' and so on, but
 * I felt that it wasn't really necessary to cover them. Also, since they are not covered by Twitch's documentation,
 * they are eligible for change at any moment.*/
public enum NOTICE_EVENT { 
	SUB_MODE_ON, SUB_MODE_OFF,
	SLOW_MODE_ON, SLOW_MODE_OFF,
	R9K_MODE_ON, R9K_MODE_OFF,
	HOST_MODE_ON, HOST_MODE_OFF,
	
	/**We default to this one if we do not recognize this NOTICE's event type. Since Twitch uses a lot of different undocumented
	 * NOTICE types, there is need for a event type that works as a catch-all. <br>
	 * Consider parsing the {@link Notice#getRawNoticeID()}.
	 */
	OTHER 
};