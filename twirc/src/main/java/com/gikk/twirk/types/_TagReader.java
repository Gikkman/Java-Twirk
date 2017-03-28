package com.gikk.twirk.types;

import java.util.HashMap;

public class _TagReader {
	//***********************************************************
	// 				VARIABLES
	//***********************************************************
	private final HashMap<String, String> map = new HashMap<String, String>();

	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************	
	public _TagReader(String tag){
		//Substring the tag to remove the @ at the beginning
		String[] segments = tag.substring(1).split(";");
		
		int i = 0;
		String key = "", value = "";
		for(String s : segments){		
			if( (i = s.indexOf('=')) != -1){
				key   = s.substring(0, i);
				value = mightBeEscaped(key) 
						? replaceEscapes(s.substring(i+1)) 
						: s.substring(i+1);
				map.put( key, value );
			}
		}
	}
	
	//***********************************************************
	// 				PUBLIC
	//***********************************************************	
	/**Fetches a certain field value that might have been present in the tag. 
	 * If the field was not present in the tag, or the field's value was empty,
	 * this method returns <code>NULL</code>
	 * 
	 * @param identifier  The fields identifier. See {@link _IDENTIFIERS}
	 * @return  The fields value, if there was any. <code>NULL</code> otherwise
	 */
	public String getAsString(String identifier){
		return map.getOrDefault(identifier, "");
	}
	
	/**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into an <code>int</code>. If the field was not present in the tag, 
	 * or the field's value was empty, this method returns <code>-1</code>
	 * 
	 * @param identifier  The fields identifier. See {@link _IDENTIFIERS}
	 * @return  The fields value, if it was present and an int. <code>-1</code> otherwise
	 */
	public int getAsInt(String identifier) {
		try{
			return Integer.decode(map.getOrDefault(identifier, "-1"));
		} catch (NumberFormatException e){
			return -1;
		}
	}
    
    /**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into a <code>long</code>. If the field was not present in the tag, 
	 * or the field's value was empty, this method returns <code>-1</code>
	 * 
	 * @param identifier  The fields identifier. See {@link _IDENTIFIERS}
	 * @return  The fields value, if it was present and a long. <code>-1</code> otherwise
	 */
    long getAsLong(String identifier) {
       try{
			return Long.decode(map.getOrDefault(identifier, "-1"));
		} catch (NumberFormatException e){
			return -1;
		} 
    }
	
	/**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into a <code>boolean</code>. For parsing purpose, <code>1</code> is interpreted
	 * as <code>true</code>, anything else as <code>false</code>.
	 * If the field was not present in the tag, or the field's value was empty, this method returns <code>false</code>
	 * 
	 * @param identifier  The fields identifier. See {@link _IDENTIFIERS}
	 * @return  The fields value, if it was present and could be parsed to a boolean. <code>false</code> otherwise
	 */
	public boolean getAsBoolean(String identifier) {
		return map.getOrDefault(identifier, "0").equals("1");
	}
	
	//***********************************************************
	// 				PRIVATE
	//***********************************************************	
	private boolean mightBeEscaped(String segment){
		return  segment.equals(_IDENTIFIERS.DISPLAY_NAME) || 
				segment.equals(_IDENTIFIERS.BAN_REASON)	 ||
				segment.equals(_IDENTIFIERS.SYSTEM_MESSAGE);
	}
	
	private String replaceEscapes(String segment){
		segment = segment.replace("\\s", " ");
		segment = segment.replace("\\:", ";");
		return segment;
	}
}
