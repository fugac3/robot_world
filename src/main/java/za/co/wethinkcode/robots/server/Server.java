package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
//        throw new UnsupportedOperationException( "TODO" );
        int port = 1235;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);
            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected.");

                // Handle each client in a new thread
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();

            }
        }
    }
    // The following initialisation is REQUIRED for `flow` monitoring.
    // DO NOT REMOVE OR MODIFY THIS CODE.
    static {
        new Recorder().logRun();
    }
}
