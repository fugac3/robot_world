package za.co.wethinkcode.robots.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            String msgFromClient;
            while ((msgFromClient = reader.readLine()) != null) {
                System.out.println("Client: " + msgFromClient);

                writer.write("msg received");
                writer.newLine();
                writer.flush();

                if (msgFromClient.equalsIgnoreCase("BYE")) {
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
