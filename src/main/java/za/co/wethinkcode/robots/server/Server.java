package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Server {
    private static boolean running = true;
//    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static ServerSocket serverSocket;

//    private static final TextWorld world = new TextWorld();
    private static final TextWorld world = TextWorld.getInstance();
    public static void main(String[] args) {
        int port = 4435;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);

            while (running) {
                //Socket object :
                //is the individual connection between server and one specific client.
                //The ServerSocket is listening for new connections.
                //When client connects, serverSocket.accept():
                //returns Socket object that represents the connection to that client.
                //clientSocket is used to read and write from server to that specific client.
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, world);
                clients.add(handler);
                System.out.println("New client connected.");
                new Thread(handler).start();
            }

        } catch (IOException e) {
            if (running) {
                System.out.println("Server error: " + e.getMessage());
            } else {
                System.out.println("Server shut down.");
            }
        } finally {
            shutdownServer(); // Clean up even if crash
        }
    }

    public static void shutdownServer() {
        running = false;

        //Close the server socket (stops accept loop)
        try {
            //checks if the server socket was created successfully.
            //else it would cause a NullPointerException
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Server and all clients shut down.");
    }
    // The following initialisation is REQUIRED for `flow` monitoring.
    // DO NOT REMOVE OR MODIFY THIS CODE.
    static {
        new Recorder().logRun();
    }

}
