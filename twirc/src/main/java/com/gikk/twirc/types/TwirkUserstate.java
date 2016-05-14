package com.gikk.twirc.types;

import com.gikk.twirc.events.TwirkListener;

/**Class for representing information about a user<br>
 * Userstate's are received in two different ways:
 * <ul>
 * <li> Whenever you send a message to Twitch's IRC server, you will get a response with YOUR UserState. This response will trigger the {@link TwirkListener#onUserstate(TwirkUserstate)} event in the {@link TwirkListener} class.
 * <li> Whenever a user sends a PRIVMSG or a WHISPER that you can see, that message is also accompanied by a UserState for THAT user. That UserState is wrapped in a {@link TwirkUser} object, and will be be one of the arguments for the {@link TwirkListener#onPrivMsg(TwirkUser, TwirkMessage)} or {@link TwirkListener#onWhisper(TwirkUser, TwirkMessage)} event
 * </ul>
 * @author Gikkman
 *
 */
public class TwirkUserstate {
	public static enum UserType{ MOD, GLOBAL_MOD, ADMIN, STAFF, EMPTY }
	private static final String NAMES_IDENTIFIER 	= "display-name=";
	private static final String COLOR_IDENTIFIER 	= "color=";
	private static final String SUB_IDENTIFIER 		= "subscriber=";
	private static final String MOD_IDENTIFIER 		= "mod=";
	private static final String TURBO_IDENTIFIER 	= "turbo=";
	private static final String USERTYPE_IDENTIFIER = "user-type=";
	private static final String EMOTE_SET_IDENTIFIER= "emote-sets=";
	private static final String BADGE_IDENTIFIER 	= "badges=";
	private static final int[] default_colors = { 0xFF0000, 0x0000FF, 0x00FF00, 0xB22222, 0xFF7F50,
												  0x9ACD32, 0xFF4500, 0x2E8B57, 0xDAA520, 0xD2691E,
												  0x5F9EA0, 0x1E90FF, 0xFF69B4, 0x8A2BE2, 0x00FF7F };
	
	public final int color;
	public final String displayName;
	public final boolean isMod;
	public final boolean isSub;
	public final boolean isTurbo;
	public final int[] emoteSets;
	public final String[] badges;
	public final UserType userType;
	
	public TwirkUserstate( String messageTag, String sender ) {
		//If display-name is empty, it means that the the user name can be read from the IRC message's prefix and
		//that it has it's first character in upper case and the rest of the characters in lower case
		String displayName =  parseFeature( NAMES_IDENTIFIER, messageTag );
		this.displayName = displayName.isEmpty()
						   ? Character.toUpperCase( sender.charAt(1) ) + sender.substring(2, sender.indexOf("!") )
						   : displayName;
		
		String color = parseFeature(COLOR_IDENTIFIER, messageTag);
		this.color = color.matches("") ? getDefaultColor() : Integer.decode(color);
		
		String mod = parseFeature(MOD_IDENTIFIER, messageTag);
		this.isMod = mod.matches("1") ? true : false;
		
		String sub = parseFeature(SUB_IDENTIFIER, messageTag);
		this.isSub = sub.matches("1") ? true : false;
		
		String turbo = parseFeature(TURBO_IDENTIFIER, messageTag);
		this.isTurbo = turbo.matches("1") ? true : false;
		
		String emoteSet = parseFeature(EMOTE_SET_IDENTIFIER, messageTag);
		this.emoteSets = parseEmoteSets( emoteSet );
		
		String userType = parseFeature(USERTYPE_IDENTIFIER, messageTag);
		this.userType = parseUserType( userType );
		
		String badges = parseFeature(BADGE_IDENTIFIER, messageTag);
		this.badges = badges.isEmpty() ? new String[0] : badges.split(",");
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

	private UserType parseUserType(String userType) {
		if( userType.equalsIgnoreCase( "mod" ) )
			return UserType.MOD;
		else if( userType.equalsIgnoreCase( "global_mod" ) )
			return UserType.GLOBAL_MOD;
		else if( userType.equalsIgnoreCase( "admin" ) )
			return UserType.ADMIN;
		else if( userType.equalsIgnoreCase( "staff" ) )
			return UserType.STAFF;
		else 
			return UserType.EMPTY;
	}

	private String parseFeature(String IDENTIFIER, String messageTag) {
		int begin = messageTag.indexOf( IDENTIFIER );
		int end =   messageTag.indexOf(';', begin);	
		//If begin == -1, it means this tag was not present. If begin == end, it means that the tag was empty
		if( begin == end || begin == -1)
			return "";
		//If end == -1, it means that this tag was the last one. We can get valid data if we take from begin (and remove the IDENTIFIER) to the end of the tag.
		else if( end == -1 )
			return messageTag.substring(begin + IDENTIFIER.length());
		//If none of them returned -1, it means that we can get valid data between begin (and remove the IDENTIFIER) and end
		else
			return messageTag.substring(begin + IDENTIFIER.length(), end);
	}

	private int getDefaultColor(){
		//If display name is empty, just semi-random a color
		if( displayName.isEmpty() )
			return default_colors[ ((int) (System.currentTimeMillis()) % default_colors.length) ];
		
		int n = displayName.charAt(0) + displayName.charAt(displayName.length() - 1);
        return default_colors[n % default_colors.length];
	}
}
