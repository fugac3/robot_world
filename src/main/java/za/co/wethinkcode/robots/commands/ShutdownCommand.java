package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.server.ClientHandler;
import za.co.wethinkcode.robots.server.ConnectionManager;
import za.co.wethinkcode.robots.server.Server;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * Shuts down the server and disconnects all clients and robots.
 *
 * @param serverSocket the server's ServerSocket instance
 * @param clientHandlers list of active client handler threads
 */
public class ShutdownCommand {
    public static void shutdownServer(ServerSocket serverSocket, List<ClientHandler> clientHandlers) {
        // Stop the server loop
        Server.setRunning(false);

        // Disconnect all client
        for (ClientHandler handler : clientHandlers) {
            handler.disconnect();
        }

        // Clear all robots from the world
        TextWorld.getInstance().clearRobots();

        // Close the server socket
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing server socket: " + e.getMessage());
        }

        System.out.println("All clients disconnected. Robots cleared.");
    }
}
