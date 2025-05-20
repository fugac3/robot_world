package za.co.wethinkcode.robots.server;

import com.google.gson.GsonBuilder;
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
                PrintWriter writer = new PrintWriter(connectionManager.getSocket().getOutputStream(), true);  // autoFlush = true
        ) {
            this.clientName = reader.readLine();
            System.out.println("Client " + clientName + " has connected.");
            writer.println("Welcome, " + this.clientName + "!");
            writer.flush();

            String msgFromClient;
            while (running && (msgFromClient = reader.readLine()) != null) {
                if (msgFromClient.equalsIgnoreCase("quit")) {
                    writer.println("Bye, " + this.clientName + "!");
                    writer.flush();
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

    private void sendResponse(PrintWriter writer, Response response) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = gson.toJson(response);
        System.out.print(clientName+": ");
        System.out.println(jsonResponse);
        for (String line : jsonResponse.split("\n")) {
            writer.println(line);
        }
        // When non arg cmds are called state is not read by client until further inputs,
        //this creates a clear end of response.
        writer.println("===END===");
        writer.flush();
    }

    private void sendError(PrintWriter writer, String errorMessage) throws IOException {
        Response errorResponse = new Response("ERROR", Map.of("message", errorMessage), null);
        sendResponse(writer, errorResponse);
    }
    //================
}