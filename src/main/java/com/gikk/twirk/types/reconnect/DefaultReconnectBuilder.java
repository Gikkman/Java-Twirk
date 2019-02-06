package com.gikk.twirk.types.reconnect;

public class DefaultReconnectBuilder implements ReconnectBuilder{
    @Override
    public Reconnect build() {
        return new ReconnectImpl();
    }
}
