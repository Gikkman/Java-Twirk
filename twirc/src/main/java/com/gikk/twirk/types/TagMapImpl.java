package com.gikk.twirk.types;

import java.util.HashMap;

class TagMapImpl extends HashMap<String, String> implements TagMap{
	//***********************************************************
	// 				CONSTRUCTOR
	//***********************************************************
	public TagMapImpl(String tag){
        super();
        if(tag.isEmpty()) {
            return;
        }
		//Substring the tag to remove the @ at the beginning
		String[] segments = tag.substring(1).split(";");

		int i = 0;
		String key, value;
		for(String s : segments){
			if( (i = s.indexOf('=')) != -1){
				key   = s.substring(0, i);
				value = mightBeEscaped(key)
						? replaceEscapes(s.substring(i+1))
						: s.substring(i+1);
				this.put( key, value );
			}
		}
	}

	//***********************************************************
	// 				PUBLIC
	//***********************************************************
	/**{@inheritDoc}
	 */
    @Override
	public String getAsString(String identifier){
		return this.getOrDefault(identifier, "");
	}

	/**{@inheritDoc}
	 */
    @Override
	public int getAsInt(String identifier) {
		try{
			return Integer.decode(this.getOrDefault(identifier, "-1"));
		} catch (NumberFormatException e){
			return -1;
		}
	}

    /**{@inheritDoc}
	 */
    @Override
    public long getAsLong(String identifier) {
       try{
			return Long.decode(this.getOrDefault(identifier, "-1"));
		} catch (NumberFormatException e){
			return -1;
		}
    }

	/**{@inheritDoc}
	 */
    @Override
	public boolean getAsBoolean(String identifier) {
		return this.getOrDefault(identifier, "0").equals("1");
	}

	//***********************************************************
	// 				PRIVATE
	//***********************************************************
	private boolean mightBeEscaped(String segment){
		return  segment.equals(TwitchTags.DISPLAY_NAME)  ||
				segment.equals(TwitchTags.BAN_REASON)    ||
				segment.equals(TwitchTags.SYSTEM_MESSAGE)||
                segment.equals(TwitchTags.PARAM_SUB_PLAN_NAME);
	}

	private String replaceEscapes(String segment){
		segment = segment.replace("\\s", " ");
		segment = segment.replace("\\:", ";");
		return segment;
	}
}
