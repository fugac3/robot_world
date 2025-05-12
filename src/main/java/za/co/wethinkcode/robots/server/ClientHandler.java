package za.co.wethinkcode.robots.server;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.LaunchCommand;
import za.co.wethinkcode.robots.commands.QuitCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

                        //assign args based on cmd name
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

                Map<String, Object> data = new HashMap<>();

                try {
                    String cmdName = request.getCommand();
                    if ("launch".equalsIgnoreCase(cmdName)) {

                        String name = (String) request.getArguments().get("name");

                        //Block clients trying to launch more than one robot
                        if (this.robot != null) {
                            data.put("message", "A robot has already been launched for this client.");
                            response = new Response("ERROR", data, null);
                        } else {
                            //Basic name check using LaunchCommand
                            LaunchCommand launchCommand = new LaunchCommand(name);
                            response = launchCommand.execute(null);  // Validates name
                            //Check world for duplicate robot names
                            boolean nameTaken = world.getAllRobots().stream().anyMatch(r -> r.getName().equalsIgnoreCase(name));
                            if (nameTaken) {
                                data.put("message", "Too many of you in this world");
                                response = new Response("ERROR", data, null);
                            } else {
                                //If all good, launch the robot
                                this.robotName = name;
                                Position startPos = world.getRandomFreePosition();

                                this.robot = new Robot(robotName, world, startPos);

                                data.put("message", "Robot successfully launched.");
                                Position pos = robot.getPosition();
                                data.put("position", new int[]{pos.getX(), pos.getY()});
                                world.addRobot(this.robot);
                                response = new Response("OK", data, robot);
                            }
                        }
                    }
                    else if ("quit".equalsIgnoreCase(cmdName)) {
                        Server.shutdownServer();
                        connectionManager.stop();
                        break;
                    } else {
                        // All other commands after launch
                        Object stepsArg = request.getArguments().get("steps");
                        String reconstructed = cmdName + (stepsArg != null ? " " + stepsArg : "");
                        command = Command.create(reconstructed);

                        //If robot has not been launched yet
                        if (robot == null) {
                            data.put("message", "Please launch a robot first using: launch robot-name");
                            response = new Response("ERROR", data, null);
                        } else {
                            assert command != null;
                            response = robot.handleCommand(command);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    data.put("message", e.getMessage());
                    response = new Response("ERROR", data, null);
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
        System.out.println(clientName+": "+json);//prints all input to server
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

