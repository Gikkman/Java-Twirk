package com.gikk.twirk.types.users;

import com.gikk.twirk.types.TWIRK_UTIL;
import com.gikk.twirk.types.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Since Userstate and TwitchUser shares many fields, it is easier to have one class which does all the parsing 
 * and then let the respective types Builder classes use it
 * 
 * @author Gikkman
 *
 */
abstract class AbstractUserBuilder {
	private static final String BADGE_IDENTIFIER 	= "badges=";
	private static final String	USER_ID_IDENTIFIER  = "user-id=";
	private static final String NAMES_IDENTIFIER 	= "display-name=";
	private static final String COLOR_IDENTIFIER 	= "color=";
	private static final String SUB_IDENTIFIER 		= "subscriber=";
	private static final String MOD_IDENTIFIER 		= "mod=";
	private static final String TURBO_IDENTIFIER 	= "turbo=";
	private static final String USERTYPE_IDENTIFIER = "user-type=";
	private static final String EMOTE_SET_IDENTIFIER= "emote-sets=";
	private static final int[] default_colors = { 0xFF0000, 0x0000FF, 0x00FF00, 0xB22222, 0xFF7F50,
												  0x9ACD32, 0xFF4500, 0x2E8B57, 0xDAA520, 0xD2691E,
												  0x5F9EA0, 0x1E90FF, 0xFF69B4, 0x8A2BE2, 0x00FF7F };

	String 	  displayName;
	int 	  color;
	int 	  userID;
	int[] 	  emoteSets;
	boolean   isMod;
	boolean   isSub;
	boolean   isTurbo;
	USER_TYPE userType;
	Userstate userstate;
	String[]  badges;
	String 	  rawLine;
	
	void parseUserProperties(TwitchMessage message){
		//If display-name is empty, it means that the the user name can be read from the IRC message's prefix and
		//that it has it's first character in upper case and the rest of the characters in lower case
		String sender = message.getPrefix().substring(1); //Strip the initial ':' from the prefix
		String tag = 	 message.getTag();
		String channelOwner = message.getTarget().substring(1);	//Strip the # from the channel name
		
		String temp =  TWIRK_UTIL.parseFeature( NAMES_IDENTIFIER, tag );
		this.displayName = temp.isEmpty()
						   ? Character.toUpperCase( sender.charAt(1) ) + sender.substring(2, sender.indexOf("!") )
						   : temp;
		
	    temp = TWIRK_UTIL.parseFeature(COLOR_IDENTIFIER, tag);
		this.color = temp.isEmpty() ? getDefaultColor() : Integer.decode(temp);
		
		temp = TWIRK_UTIL.parseFeature(MOD_IDENTIFIER, tag);
		this.isMod = temp.equals("1") ? true : false;
		
		temp = TWIRK_UTIL.parseFeature(SUB_IDENTIFIER, tag);
		this.isSub = temp.equals("1") ? true : false;
		
		temp = TWIRK_UTIL.parseFeature(TURBO_IDENTIFIER, tag);
		this.isTurbo = temp.equals("1") ? true : false;
		
		temp = TWIRK_UTIL.parseFeature(EMOTE_SET_IDENTIFIER, tag);
		this.emoteSets = parseEmoteSets( temp );

		temp = TWIRK_UTIL.parseFeature(USERTYPE_IDENTIFIER, tag);
		this.userType = parseUserType( temp, displayName, channelOwner );
		
		temp = TWIRK_UTIL.parseFeature(BADGE_IDENTIFIER, message.getTag());
		this.badges = temp.isEmpty() ? new String[0] : temp.split(",");
		
		temp = TWIRK_UTIL.parseFeature(USER_ID_IDENTIFIER, message.getTag());
		this.userID = temp.isEmpty() ? -1 : Integer.parseInt(temp);
		
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
