package com.gikk.twirk.types;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.Userstate;

/**Since many types shares these typical User-fields, it is easier to have one class which does all the parsing 
 * and then let the respective types Builder classes use it
 * 
 * @author Gikkman
 *
 */
public abstract class AbstractTwitchUserFields {
	private static final int[] default_colors = { 0xFF0000, 0x0000FF, 0x00FF00, 0xB22222, 0xFF7F50,
												  0x9ACD32, 0xFF4500, 0x2E8B57, 0xDAA520, 0xD2691E,
												  0x5F9EA0, 0x1E90FF, 0xFF69B4, 0x8A2BE2, 0x00FF7F };

	public  String 	  displayName;
	public  int 	  color;
	public  int 	  userID;
	public  int[] 	  emoteSets;
	public  boolean   isMod;
	public  boolean   isSub;
	public  boolean   isTurbo;
	public  USER_TYPE userType;
	public  Userstate userstate;
	public  String[]  badges;
	public  String 	  rawLine;
	
	protected void parseUserProperties(TwitchMessage message){
		//If display-name is empty, it means that the the user name can be read from the IRC message's prefix and
		//that it has it's first character in upper case and the rest of the characters in lower case
		String sender = message.getPrefix().substring(1); //Strip the initial ':' from the prefix
		String channelOwner = message.getTarget().substring(1);	//Strip the # from the channel name
		
		_TagReader r = new _TagReader(message.getTag());
		
		String temp =  r.getAsString(_IDENTIFIERS.DISPLAY_NAME);
		this.displayName = temp.isEmpty()
						   ? Character.toUpperCase( sender.charAt(1) ) + sender.substring(2, sender.indexOf("!") )
						   : temp;
		temp = r.getAsString(_IDENTIFIERS.BADGES);
		this.badges = temp.isEmpty() ? new String[0] : temp.split(",");	
	
		this.isMod   = r.getAsBoolean(_IDENTIFIERS.IS_MOD);
		this.isSub   = r.getAsBoolean(_IDENTIFIERS.IS_SUB);
		this.isTurbo = r.getAsBoolean(_IDENTIFIERS.IS_TURBO);
		this.userID = r.getAsInt(_IDENTIFIERS.USER_ID);
		this.color  = r.getAsInt(_IDENTIFIERS.COLOR);
		this.color = this.color == -1 ? getDefaultColor() : this.color;
		
		this.emoteSets = parseEmoteSets( r.getAsString(_IDENTIFIERS.EMOTE_SET) );
		this.userType  = parseUserType(  r.getAsString(_IDENTIFIERS.USERTYPE), displayName, channelOwner );
		
		this.rawLine = message.getRaw();
	}
	
	private int[] parseEmoteSets(String emoteSet) {
		if( emoteSet.isEmpty() )
			return new int[0];
		
		String[] sets = emoteSet.split(",");
		int[] out = new int[ sets.length ];
		
		for( int i = 0; i < sets.length; i++ )
			out[i] = Integer.parseInt( sets[i] );
		
		return out;
	}

	private USER_TYPE parseUserType(String userType, String sender, String channelOwner) {
		if( userType.isEmpty() )
			return USER_TYPE.DEFAULT;
		else if( sender.equalsIgnoreCase( channelOwner ) )
			return USER_TYPE.OWNER;			
		else if( userType.equals( "mod" ) )
			return USER_TYPE.MOD;
		else if( userType.equals( "global_mod" ) )
			return USER_TYPE.GLOBAL_MOD;
		else if( userType.equals( "admin" ) )
			return USER_TYPE.ADMIN;
		else if( userType.equals( "staff" ) )
			return USER_TYPE.STAFF;
		else
			return USER_TYPE.DEFAULT;	//Safety valve
	}

	private int getDefaultColor(){
		//If display name is empty, just semi-random a color
		if( displayName.isEmpty() )
			return default_colors[ ((int) (System.currentTimeMillis()) % default_colors.length) ];
		
		int n = displayName.charAt(0) + displayName.charAt(displayName.length() - 1);
        return default_colors[n % default_colors.length];
	}
}
