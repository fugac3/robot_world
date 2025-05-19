package za.co.wethinkcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

     @Test
    public void testClientNameAndMessage() throws Exception {
        int port = 5555;

        // Start server in a separate thread
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started on port " + port);
                Socket clientSocket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String clientName = reader.readLine();
                System.out.println(clientName + " connected.");  // should be "TestClient"

                String msg = reader.readLine();
                System.out.println(clientName + ": " + msg);     // should be "Hello!"

                writer.write("ACK");
                writer.newLine();
                writer.flush();

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Let the server start
        Thread.sleep(500);

        // Connect client
        try (Socket socket = new Socket("localhost", port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            writer.write("TestClient");
            writer.newLine();
            writer.flush();

            writer.write("Hello!");
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            assertEquals("ACK", response);
        }
    }
}


