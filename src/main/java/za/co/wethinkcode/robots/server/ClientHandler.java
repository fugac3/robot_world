package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.world.TextWorld;
import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.Map;

//  allows handling multiple clients at the same time
public class ClientHandler implements Runnable {
    private final ConnectionManager connectionManager;
    private final boolean running = true;
    private String clientName;
    private final CommandHandler commandHandler;

    public ClientHandler(Socket socket, TextWorld world) {
        this.connectionManager = new ConnectionManager(socket);
        this.commandHandler = new CommandHandler(world, connectionManager);
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(connectionManager.getSocket().getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connectionManager.getSocket().getOutputStream()))
        ) {
            this.clientName = reader.readLine();
            System.out.println("Client " + clientName + " has connected.");
            writer.write("Welcome, " + this.clientName + "!");
            writer.newLine();
            writer.flush();

            String msgFromClient;
            while (running && (msgFromClient = reader.readLine()) != null) {
                if (msgFromClient.equalsIgnoreCase("quit")) {
                    Server.shutdownServer();
                    connectionManager.stop();
                    break;
                }
                //Takes the user input and executes the command if possible and returns a response
                Response response;
                try {
                    response = commandHandler.handleClientCommand(msgFromClient);
                } catch (Exception e) {
                    sendError(writer, "Internal error: " + e.getMessage());
                    continue;
                }

                sendResponse(writer, response);
            }
        } catch (IOException e) {
            System.out.println((clientName != null ? clientName : "Unknown client") + " disconnected: " + e.getMessage());
        } finally {
            connectionManager.stop();
            System.out.println("Client handler exiting.");
        }
    }

    private void sendResponse(BufferedWriter writer, Response response) throws IOException {
        String json = new Gson().toJson(response);
        System.out.println(clientName+": "+json); // Optional: debug log
        writer.write(json);
        writer.newLine();
        writer.flush();
    }

    private void sendError(BufferedWriter writer, String errorMessage) throws IOException {
        Response errorResponse = new Response("ERROR", Map.of("message", errorMessage), null);
        sendResponse(writer, errorResponse);
    }

    //================
}