package za.co.wethinkcode.robots.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//  allows handling multiple clients at the same time
class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
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

//            It blocks until the client sends a line. Stores in var msgFromClient
            while ((msgFromClient = reader.readLine()) != null) {
                System.out.println(clientName + ": " + msgFromClient);

                writer.write("msg received");
//                client reads with readline(). without client would hang waiting forever.
                writer.newLine();
                writer.flush();

//================= Not needed for now ==================//
                if (msgFromClient.equalsIgnoreCase("SHUTDOWN")) {
                    System.out.println("Shutdown command received.");
//                    socket.close();
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected or error: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Client connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
