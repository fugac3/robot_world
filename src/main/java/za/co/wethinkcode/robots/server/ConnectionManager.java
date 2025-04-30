package za.co.wethinkcode.robots.server;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {
    private final Socket socket;

    public ConnectionManager(Socket socket) {
        this.socket = socket;
    }

    public void stop() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // Log if needed
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
