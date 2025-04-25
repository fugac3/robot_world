package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.*;
import java.net.Socket;

//  allows handling multiple clients at the same time
public class ClientHandler implements Runnable {
    private final Socket socket;
    private volatile boolean running = true;
    private final TextWorld world;
    private Robot robot;

    public ClientHandler(Socket socket,TextWorld world) {
        this.socket = socket;
        this.world = world;
    }

    public void stop() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            // Ignore, probably already closed
        }
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
            //It blocks until the client sends a line. Stores in var msgFromClient
            while (running && (msgFromClient = reader.readLine()) != null) {
//                System.out.println(clientName + ": " + msgFromClient);

                if (msgFromClient.equalsIgnoreCase("QUIT")) {
                    System.out.println("Shutdown command received from " + clientName);
                    Server.shutdownServer(); // Notify server to shut down
                    break;
                }

                //Check for robot launch from client
                if (robot == null && msgFromClient.toLowerCase().startsWith("launch")) {
                    String[] parts = msgFromClient.split(" ");
                    if (parts.length >= 2) {
                        String robotName = parts[1];
                        this.robot = new Robot(robotName, world);
                        writer.write("Robot '" + robotName + "' launched into the world.");
                        robot.getWorld().showObstacles();
//                        world.addRobot(robot);
                        writer.flush();
                    }else {
                        //if launch has no args
                        writer.write("Please launch a robot first using: launch <name>");
                    }
                }


                else if (robot != null) {
                    try {
                        Command command = Command.create(msgFromClient);
//                        boolean success = robot.handleCommand(command);
                        robot.handleCommand(command);
                        writer.write(robot.toString());
                        System.out.println("Command from " + clientName + ": " + msgFromClient + " -> " + robot.getStatus());
                        writer.flush();
                    } catch (IllegalArgumentException e) {
                        writer.write("Invalid command: " + e.getMessage());
                        System.out.println("Invalid command from " + clientName + ": " + msgFromClient);
                    }
                } else {
                    // robot == null but not a launch command
                    writer.write("Please launch a robot first using: launch <name>");
                }


//                writer.write("msg received");
//                // client reads with readline(). without client would hang waiting forever.
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Client error or disconnected: " + e.getMessage());
        } finally {
            stop();
            System.out.println("Client handler exiting.");
        }
    }
}
