package za.co.wethinkcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @BeforeAll
    public static void startServer() {
        Thread serverThread = new Thread(() -> {
            try {
                Server.main(null);
            } catch (Exception ignored) {
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        // Give server time to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }


    @Test
    public void testClientServerConnection() {
        try (Socket socket = new Socket("localhost", 4433)) {
            assertTrue(socket.isConnected());
        } catch (IOException e) {
            fail("Could not connect to server");
        }
    }

    @Test
    public void testMessageExchange() {
        try (
                Socket socket = new Socket("localhost", 4433);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String testMsg = "Hello Server";
            out.write(testMsg);
            out.newLine();
            out.flush();

            String response = in.readLine();
            assertNotNull(response);
            assertEquals("msg received", response); // or whatever your server responds with

        } catch (IOException e) {
            fail("Message exchange failed: " + e.getMessage());
        }
    }

    @Test
    public void testClientLogoff() {
        try (
                Socket socket = new Socket("localhost", 4433);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            out.write("BYE");
            out.newLine();
            out.flush();

            String response = in.readLine();
            assertEquals("msg received", response); // Server should respond before closing

        } catch (IOException e) {
            fail("Client logoff test failed: " + e.getMessage());
        }
    }
}

//    @Test
//        //Ensure correct response for valid input.
//    void testValidRequests(){
//        Server server = new Server(); // depends on constructor implementation
//        String response = server.handleRequest(in); // hypothetical method
//        assertEquals("EXPECTED_RESPONSE", response); // replace with actual expected
//    }
//
//    @Test
//        //Handle bad input gracefully.
//    void testInvalidRequests(){
//        Server server = new Server();
//        String response = server.handleRequest(in);
//        assertEquals("ERROR_RESPONSE", response);
//    }
//
//    //    Simulate internal failures.
//    @Test
//    void testServerErrors(){
//        Server server = new Server();
//        assertThrows(RuntimeException.class, () -> {
//            server.handleRequest(null); // or some input that causes failure
//        });
//    }
//
//    //    Simultaneous connections/requests.
//    @Test
//    void testConcurrency(){
//        Server server = new Server();
//        assertThrows(RuntimeException.class, () -> {
//            server.handleRequest(null); // or some input that causes failure
//        });
//    }
//}



