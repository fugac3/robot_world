package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

//    ================ HOW TO RUN ======================   //
//    use to run in terminal
//    in: /path/to/oop-ex-toy-robot-group$
//    java -cp target/classes za.co.wethinkcode.robots.server.Server
//    java -cp target/classes za.co.wethinkcode.robots.server.Client
//    ==================================================   //

    public static void main(String[] args) throws IOException {
//        throw new UnsupportedOperationException( "TODO" );
//        int port = 1234;
        PortNum portNum = new PortNum(4433);

//        create a server that can listen for incoming client connections
        try (ServerSocket serverSocket = new ServerSocket(portNum.getPort())) {
            System.out.println("Server started. Listening on port " + portNum.getPort());
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
