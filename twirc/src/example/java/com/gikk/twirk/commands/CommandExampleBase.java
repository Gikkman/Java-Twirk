package com.gikk.twirk.commands;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.events.TwirkListenerBaseImpl;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public abstract class CommandExampleBase extends TwirkListenerBaseImpl{
	public enum CommandType{ PREFIX_COMMAND, CONTENT_COMMAND };
	
	//***********************************************************************************************
	//											VARIABLES
	//***********************************************************************************************
	private Set<String> commandPattern;
	private CommandType type;
	private USER_TYPE minPrivilidge;
	
	//***********************************************************************************************
	//											CONSTRUCTOR
	//***********************************************************************************************
	/**Base class for simpler chat commands. Simple chat commands perform a certain
	 * action whenever a certain pattern of characters are seen. 
	 * 
	 * @param type What type of command is this. PREFIX_COMMAND or CONTENT_COMMAND
	 */
	protected CommandExampleBase(CommandType type){
		this.type = type;
		commandPattern = compile();
		minPrivilidge  = getMinUserPrevilidge();
	}
	
	//***********************************************************************************************
	//											PUBLIC
	//***********************************************************************************************
	@Override
	public void onPrivMsg( TwitchUser sender, TwitchMessage message) {
		/* This could've been done with REGEX matching, instead of using startsWith()/contains().
		 * 
		 * This is much simpler though and easier to understand and maintain. Also, since the 
		 * amount of work required is so small, the gain from using a Matcher is probably 
		 * close to zero
		 * 
		 * We get the command by stripping everything but the first word away.
		 * This is used when looking for prefix commands
		 */
		String content = message.getContent().toLowerCase(Locale.ENGLISH).trim();
		String[] split = content.split("\\s", 2);
		String command = split[0];
		
		if( sender.getUserType().value >= minPrivilidge.value )
			if( type == CommandType.PREFIX_COMMAND ){
				for( String pattern : commandPattern ){
					if( command.startsWith(pattern) ){
						performCommand(pattern, sender, message);
						break;	//We don't want to fire twice for the same message
					}
				}
			}
			else {
				for( String pattern : commandPattern ) {
					if( content.contains(pattern) ){
						performCommand(pattern, sender, message);
						break; //We don't want to fire twice for the same message
					}
				}
			}
	}
	
	//***********************************************************************************************
	//											ABSTRACT
	//***********************************************************************************************
	/**This method must return the words this command should react to. If the command
	 * listen to several words, they should be separated by {@code |} signs. <br><br>
	 * <b>Examlpe:</b> {@code !stats|!stat|?stats|?stat} will react to those chat lines
	 * that starts with any of those 4 strings
	 * <br><br>
	 * Be aware that the pattern recognizer will be case in-sensitive
	 * 
	 * @return A string, comprising of all words this command reacts to
	 */
	protected abstract String getCommandWords();
	
	/**This method must return the minimum {@link USER_TYPE} required to invoke this command.<br>
	 * For example, if only USER_TYPE.MOD or higher should be able to invoke the command, this
	 * method should return USER_TYPE.MOD. <br><br>
	 * 
	 * If everyone should be able to invoke it, simply return {@link USER_TYPE#DEFAULT} 
	 * 
	 * @return The minimum {@link USER_TYPE} which can invoke this command.
	 */
	protected abstract USER_TYPE getMinUserPrevilidge();
	
	/** This method is the commands execution. This will be called whenever a chat line
	 * is seen that matches the commandPattern
	 * 
	 * @param command The string that caused us to fire this command
	 * @param sender The IrcUser who issued the command
	 * @param message The IrcMessage that triggered the command
	 */
	protected abstract void performCommand(String command, TwitchUser sender, TwitchMessage message);
	
	
	
	//***********************************************************************************************
	//											PRIVATE
	//***********************************************************************************************
	private Set<String> compile(){
		String[] patterns = getCommandWords().toLowerCase(Locale.ENGLISH).split("\\|");
		HashSet<String> out = new HashSet<String>();
		for( String pattern : patterns )
			out.add(pattern);
		return out;
	}
}
