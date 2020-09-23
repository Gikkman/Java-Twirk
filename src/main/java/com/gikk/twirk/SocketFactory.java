package com.gikk.twirk;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;

/** Factory method for creating a {@link Socket} for the Twirk instance. The socket is later used to connect to Twitch
 *
 * @author Gikkman
 */
@FunctionalInterface
public interface SocketFactory {

    /**
     *  Given a server and a port, creates {@link Socket}  that can be used to connect to Twitch's IRC interface
     * @param server the server
     * @param port the port
     * @return a {@link Socket}
     * @throws IOException if a {@link Socket} cannot be created
     */
    Socket createSocket(String server, int port) throws IOException;

    static SocketFactory getDefault(boolean useSSL) {
        return useSSL ? SSLSocketFactory.getDefault()::createSocket : Socket::new;
    }
}
