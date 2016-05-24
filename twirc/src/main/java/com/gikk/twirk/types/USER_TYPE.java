package com.gikk.twirk.types;

/** Enum for representing different types of users, as labeled by Twitch. <br>
 * DEFAULT in this case means that no information was given, and it can be assumed that a user
 * with DEFAULT user type is a normal user, without any special privileges is this channel.<br><br>
 * 
 * USER_TYPE comes with a value. OWNER = 9, MOD = 2, GLOBAL_MOD = ADMIN = STAFF = 1, DEFAULT = 0.<br>
 * These values can be used to make sure that only users of a certain type can do something.<br><br>
 * 
 * For example:<br>
 * <pre><code>if( user.USER_TYPE.value >= USER_TYPE.MOD.value )</code>
 * 	<code>doSomething();</code></pre>
 */
public enum USER_TYPE{ 
	OWNER(9),
	MOD(2),  
	GLOBAL_MOD(1),  ADMIN(1),  STAFF(1),
	DEFAULT(0);
	
	public final int value;
	
	private USER_TYPE(int value) {
		this.value = value;
	}
}