package com.gikk.twirk.types.emote;

import com.gikk.twirk.types.TagMap;

import java.util.List;

public interface EmoteParser {
    /**
     * @deprecated Use {{@link #parseEmotes(TagMap, String)} instead. This will be removed in a future release}
     */
    @Deprecated
    static List<Emote> parseEmotes(String content, String tag) {
        TagMap tagMap = TagMap.getDefault(tag);
        return new EmoteParserImpl().parseEmotes(tagMap, content);
    }

    static EmoteParser getDefaultImplementation() {
        return new EmoteParserImpl();
    }

    List<Emote> parseEmotes(TagMap tagMap, String content);
}
