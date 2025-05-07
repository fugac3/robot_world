package za.co.wethinkcode.robots.server;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.LaunchCommand;
import za.co.wethinkcode.robots.commands.QuitCommand;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//  allows handling multiple clients at the same time
public class ClientHandler implements Runnable {
    private final ConnectionManager connectionManager;
    private volatile boolean running = true;
    private final TextWorld world;
    private Robot robot;
    private String clientName;
    private String robotName;

    public ClientHandler(Socket socket, TextWorld world) {
        this.connectionManager = new ConnectionManager(socket);
        this.world = world;
    }

    public String getClientName(){
        return this.clientName;
    }


    @Override
    public void run() {
        Gson gson = new Gson();
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

                Request request = null;
                Command command = null;

                try {
                    if (msgFromClient.trim().startsWith("{")) {
                        try {
                            request = gson.fromJson(msgFromClient, Request.class);
                        } catch (Exception e) {
                            sendError(writer, "Malformed JSON request.");
                            continue;
                        }
                    } else {
                        command = Command.create(msgFromClient.toLowerCase());
                        if (command == null) {
                            throw new IllegalArgumentException("Unknown command: " + msgFromClient);
                        }

                        Map<String, Object> args = new HashMap<>();
                        String arg = command.getArgument();

                        if (arg != null && !arg.isEmpty()) {
                            switch (command.getName()) {
                                case "forward":
                                case "back":
                                    args.put("steps", arg);
                                    break;
                                case "launch":
                                    args.put("name", arg);
                                    break;
                            }
                        }

                        request = new Request(command.getName(), args);
                    }
                } catch (IllegalArgumentException e) {
                    sendError(writer, e.getMessage());
                    continue;
                }

                Response response;

                try {
                    String cmdName = request.getCommand();

                    if ("launch".equalsIgnoreCase(cmdName)) {
                        String name = (String) request.getArguments().get("name");
                        if (name == null || name.isEmpty()) {
                            response = new Response("ERROR", Map.of("message", "Launch command needs a name."), null);
                        }else if(robot != null){
                            response = new Response("ERROR", Map.of("message", "A robot has already been launched into world."), null);
                        } else {
                            command = new LaunchCommand(name);
                            this.robotName = name;
                            this.robot = new Robot(robotName, world);
                            world.addRobot(robot);
                            world.showObstacles();
                            response = command.execute(robot);
                        }

                    } else if ("quit".equalsIgnoreCase(cmdName)) {
                        Server.shutdownServer();
                        connectionManager.stop();
                        break;

                    } else {
                        // All other commands after launch
                        Object stepsArg = request.getArguments().get("steps");
                        String reconstructed = cmdName + (stepsArg != null ? " " + stepsArg : "");
                        command = Command.create(reconstructed);

                        if (robot == null) {
                            response = new Response("ERROR", Map.of("message", "Please launch a robot first using: launch robot-name"), null);
                        } else {
                            assert command != null;
                            response = robot.handleCommand(command);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    response = new Response("ERROR", Map.of("message", e.getMessage()), null);
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
        System.out.println(json); // Optional: debug log
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

