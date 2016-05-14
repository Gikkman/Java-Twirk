package com.gikk.twirc.messages;

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
	private static final String EMOTE_SET_IDENTIFIER= "emote-sets";
	@SuppressWarnings("unused")
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
	public final UserType userType;
	
	public TwirkUserstate( String messageTag ) {
		this.displayName = parseFeature( NAMES_IDENTIFIER, messageTag );
		
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
		
		//TODO: Badges
	}
	
	private int[] parseEmoteSets(String emoteSet) {
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
		int begin = messageTag.indexOf( IDENTIFIER ) + IDENTIFIER.length();
		int end =   messageTag.indexOf(';', begin);	
		if( begin == end || end == -1)
			return "";
		else 
			return messageTag.substring(begin, end);
	}

	private int getDefaultColor(){
		int n = displayName.charAt(0) + displayName.charAt(displayName.length() - 1);
        return default_colors[n % default_colors.length];
	}
}
