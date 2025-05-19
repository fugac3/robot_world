package za.co.wethinkcode;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.ClientHandler;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientTest {
    static Thread serverThread;
    static final int PORT = 5500;

    @BeforeAll
    static void startServer() {
        serverThread = new Thread(() -> {
            try (ServerSocket testSocket = new ServerSocket(PORT)) {
                while (!Thread.currentThread().isInterrupted()) {
                    Socket clientSocket = testSocket.accept();
                    ClientHandler handler = new ClientHandler(clientSocket, TextWorld.getInstance());
                    new Thread(handler).start();
                }
            } catch (IOException e) {
                // Ignore if server closed
            }
        });
        serverThread.start();
        try {
            Thread.sleep(500); // Allow time for server to start
        } catch (InterruptedException ignored) {}
    }

    @AfterAll
    static void stopServer() {
        serverThread.interrupt();
    }

    @Test
    public void testClientConnectionAndLaunch() {
        try (
                Socket client = new Socket("localhost", PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
        ) {
            // Send name
            writer.write("TestClient");
            writer.newLine();
            writer.flush();

            String welcome = reader.readLine();
            assertTrue(welcome.contains("TestClient"));

            // Send launch command
            writer.write("launch TestBot");
            writer.newLine();
            writer.flush();

            String launchResponse = reader.readLine();
            assertTrue(launchResponse.contains("Robot successfully launched."));

        } catch (IOException e) {
            fail("Client failed to connect or communicate: " + e.getMessage());
        }
    }
}
