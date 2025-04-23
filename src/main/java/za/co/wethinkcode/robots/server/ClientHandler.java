package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.world.TextWorld;

import java.io.*;
import java.net.Socket;

//  allows handling multiple clients at the same time
public class ClientHandler implements Runnable {
    private final Socket socket;
    private volatile boolean running = true;
    private final TextWorld world;

    public ClientHandler(Socket socket,TextWorld world) {
        this.socket = socket;
        this.world = world;
    }

    public void stop() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            // Ignore, probably already closed
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {


            String clientName = reader.readLine();  // Read name sent from client
            System.out.println(clientName + " connected.");

            String msgFromClient;
            //It blocks until the client sends a line. Stores in var msgFromClient
            while (running && (msgFromClient = reader.readLine()) != null) {
                System.out.println(clientName + ": " + msgFromClient);

                if (msgFromClient.equalsIgnoreCase("QUIT")) {
                    System.out.println("Shutdown command received from " + clientName);
                    Server.shutdownServer(); // Notify server to shut down
                    break;
                }

                writer.write("msg received");
                // client reads with readline(). without client would hang waiting forever.
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Client error or disconnected: " + e.getMessage());
        } finally {
            stop();
            System.out.println("Client handler exiting.");
        }
    }
}
