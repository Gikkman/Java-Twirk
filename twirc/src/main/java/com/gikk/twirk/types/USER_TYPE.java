package com.gikk.twirk.types;

/** Enum for representing different types of users, as labeled by Twitch. 
 * EMPTY in this case means that no information was given, and it can be assumed that a user
 * with EMPTY user type is a normal user, without any special privileges is this channel.
 */
public enum USER_TYPE{ 
	BROADCASTER,
	MOD, 
	GLOBAL_MOD, 
	ADMIN, 
	STAFF, 
	EMPTY 
}