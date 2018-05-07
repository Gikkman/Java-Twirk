package com.gikk.twirk;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Gikkman
 */
public interface SocketFactory {
    public Socket createSocket() throws IOException;
}
