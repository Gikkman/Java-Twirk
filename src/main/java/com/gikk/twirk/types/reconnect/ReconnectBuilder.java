package com.gikk.twirk.types.reconnect;

import com.gikk.twirk.types.twitchMessage.TwitchMessage;

/**Constructs a {@link Reconnect} object. To create a {@link Reconnect} object, call the {@link #build()} method
 *
 * @author Gikkman
 *
 */
public interface ReconnectBuilder {
    static ReconnectBuilder getDefault() {
        return new DefaultReconnectBuilder();
    }

    Reconnect build();
}
