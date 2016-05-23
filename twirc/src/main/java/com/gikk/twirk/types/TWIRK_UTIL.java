package com.gikk.twirk.types;

public class TWIRK_UTIL {
	public static String parseFeature(String IDENTIFIER, String tag) {
		int begin = tag.indexOf( IDENTIFIER );
		int end =   tag.indexOf(';', begin);	
		//If begin == -1, it means this tag was not present. If begin + IDENTIFIER.lenght() == end, it means that the tag was empty
		if( begin == -1 || begin + IDENTIFIER.length() == end)
			return "";
		//If end == -1, it means that this tag was the last one. We can get valid data if we take from begin (and remove the IDENTIFIER) to the end of the tag.
		else if( end == -1 )
			return tag.substring(begin + IDENTIFIER.length());
		//If none of them returned -1, it means that we can get valid data between begin (and remove the IDENTIFIER) and end
		else
			return tag.substring(begin + IDENTIFIER.length(), end);
	}
}
