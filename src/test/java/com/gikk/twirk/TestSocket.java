package com.gikk.twirk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

/**A socket which fakes a remote server. This implementation allows us to send
 * Strings, which will "come out" the Socket's input stream. This allows us to
 * test how whatever is listening to the socket reacts to certain input
 *
 * @author Gikkman
 */
class TestSocket extends Socket{
    private static final int PIPE_BUFFER = 2048;
    private final PipedInputStream is;
    private final PipedOutputStream os;
    private final BufferedWriter writer;

    private final OutputStream voidStream;
    private final OutputQueue queue = new OutputQueue();
    private boolean isAlive = true;

    TestSocket() throws IOException {
        is = new PipedInputStream(PIPE_BUFFER);
        os = new PipedOutputStream(is);
        writer = new BufferedWriter( new OutputStreamWriter(os) );

        // Incase the one using the socket writes data to it, that data ends up here
        voidStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // Do nothing with whatever is written
            }
        };

        // An output stream can only be written to by the same thread.
        // Since multiple threads might call the putMessage method, it is
        // safer to wrap the writing to the stream in a separate thread
        Thread writeThread = new Thread( () -> {
            while(isAlive){
                String s = queue.next();
                if(s != null){
                    try {
                    writer.write(s + "\r\n");
                    writer.flush();
                    }
                    catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        writeThread.start();
    }

    @Override
    public void close(){
        try {
            isAlive = false;
            queue.releaseWaitingThreads();

            writer.close();
            os.close();
            is.close();
            voidStream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    void putMessage(String s) {
        queue.add(s);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return is;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return voidStream;
    }
}
