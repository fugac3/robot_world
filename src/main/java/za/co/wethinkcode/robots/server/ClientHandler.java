package za.co.wethinkcode.robots.server;

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


    @Override
    public void run() {
        Gson gson = new Gson();

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(connectionManager.getSocket().getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connectionManager.getSocket().getOutputStream()))
        ) {
            this.clientName = reader.readLine();   // Save into field
            System.out.println("DEBUG: New client name = '" + clientName + "'");

            if (clientName == null || clientName.isBlank()) {
                System.out.println("Enter valid name");
                connectionManager.stop();
                return;
            }

            System.out.println(this.clientName + " connected.");

            String msgFromClient;
            while (running && (msgFromClient = reader.readLine()) != null) {
                //Take JSON string (msgFromClient) from client.
                //Convert (deserialize) it into a Java object of type Request.
                //Store that object into variable request.
                Request request;
                if (msgFromClient.equalsIgnoreCase("quit")){
                    Server.shutdownServer();
                    connectionManager.stop();
                    break;
                }

                if (msgFromClient.trim().startsWith("{")) {
                    // It is already JSON
                    request = gson.fromJson(msgFromClient, Request.class);
                } else {
                    // It is normal user input like "forward 10"
                    Command command = Command.create(msgFromClient);
                    Map<String, Object> args = new HashMap<>();

                    System.out.println("DEBUG: Command name = " + command.getName() + " - in cli handler");
                    System.out.println("DEBUG: Command argument = " + command.getArgument());

                    if (command.getArgument() != null && !command.getArgument().isEmpty()) {
                        if (command.getName().equals("forward") || command.getName().equals("back") || command.getName().equals("sprint")) {
                            args.put("steps", command.getArgument());
                        } else if (command.getName().equals("launch")) {
                            args.put("name", command.getArgument());
                        }

                    }
                    request = new Request(command.getName(), args);
                }




                Response response;

                try {
                    Command command;
                    if (request.getCommand().equalsIgnoreCase("launch")) {
                        String name = (String) request.getArguments().get("name");
                        command = new LaunchCommand(name);
                    } else if (request.getCommand().equalsIgnoreCase("quit")) {
                        command = new QuitCommand();
                    } else {
                        String reconstructedInstruction = request.getCommand();
                        Object stepsArg = request.getArguments().get("steps");

                        if (stepsArg != null) {
                            reconstructedInstruction += " " + stepsArg.toString();
                        }

                        command = Command.create(reconstructedInstruction);

                    }

                    if (robot == null) {
                        if (command instanceof LaunchCommand) {
                            this.robotName = (String) request.getArguments().get("name");

                            if (robotName == null || robotName.isEmpty()) {
                                response = new Response("ERROR", "Launch command needs a robot name from cli handler.", null);
                            }
//                            else if (robotName == this.robotName) {

                             else {
                                this.robot = new Robot(robotName, world);   //Create robot manually
                                world.addRobot(robot); //Add robot to the shared world

                                Map<String, Object> state = new HashMap<>();
                                state.put("robot", robotName);
                                state.put("position", robot.getPosition());
                                state.put("status", robot.getStatus());

                                response = new Response("OK", "Robot '" + robotName + "' launched", robot);
                            }
                        } else {
                            // Any other command before launch
                            response = new Response("ERROR", "Please launch a robot first using: launch <robotname>", null);
                        }
                    } else {
                        // Robot already launched, can handle other commands
                        response = robot.handleCommand(command);

                        if (command instanceof QuitCommand) {
                            Server.shutdownServer();
                            connectionManager.stop();    // stops client handler too
                            break;
                        }
                    }

                //end of inner try
                } catch (IllegalArgumentException e) {
                    response = new Response("ERROR", e.getMessage(), null);
                }

                // Send the response back
                writer.write(gson.toJson(response));
                writer.newLine();
                writer.flush();
            }
        //end of main try
        } catch (IOException e) {
//            System.out.println("Client error or disconnected: " + e.getMessage());
            System.out.println((clientName != null ? clientName : "Unknown client") + " disconnected: " + e.getMessage());
        } finally {
            connectionManager.stop();
            System.out.println("Client handler exiting.");
        }
    }

    //================
}

