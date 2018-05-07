package com.gikk.twirk.types;

import java.util.Map;

/**
 *
 * @author Gikkman
 */
public interface TagMap extends Map<String, String>{
    //***********************************************************
	// 				PUBLIC
	//***********************************************************
	/**Fetches a certain field value that might have been present in the tag.
	 * If the field was not present in the tag, or the field's value was empty,
	 * this method returns <code>NULL</code>
	 *
	 * @param identifier  The fields identifier. See {@link TwitchTags}
	 * @return  The fields value, if there was any. <code>NULL</code> otherwise
	 */
	public String getAsString(String identifier);

	/**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into an <code>int</code>. If the field was not present in the tag,
	 * or the field's value was empty, this method returns <code>-1</code>
	 *
	 * @param identifier  The fields identifier. See {@link TwitchTags}
	 * @return  The fields value, if it was present and an int. <code>-1</code> otherwise
	 */
	public int getAsInt(String identifier);

    /**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into a <code>long</code>. If the field was not present in the tag,
	 * or the field's value was empty, this method returns <code>-1</code>
	 *
	 * @param identifier  The fields identifier. See {@link TwitchTags}
	 * @return  The fields value, if it was present and a long. <code>-1</code> otherwise
	 */
    long getAsLong(String identifier);

	/**Fetches a certain field value that might have been present in the tag, and
	 * tries to parse it into a <code>boolean</code>. For parsing purpose, <code>1</code> is interpreted
	 * as <code>true</code>, anything else as <code>false</code>.
	 * If the field was not present in the tag, or the field's value was empty, this method returns <code>false</code>
	 *
	 * @param identifier  The fields identifier. See {@link TwitchTags}
	 * @return  The fields value, if it was present and could be parsed to a boolean. <code>false</code> otherwise
	 */
	public boolean getAsBoolean(String identifier);

    /**Creates a new TagMap using the default TagMap implementation.
     *
     * @param tag
     * @return the default tag map
     */
    static TagMap getDefault(String tag){
        return new TagMapImpl(tag);
    }
}
