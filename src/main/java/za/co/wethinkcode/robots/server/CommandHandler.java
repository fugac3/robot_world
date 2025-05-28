package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.RepairCommand;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.robotTypes.RobotCreator;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final TextWorld world;
    private Robot robot;
    private final ConnectionManager connectionManager;
    private final ClientHandler clientHandler;

    public CommandHandler(TextWorld world, ConnectionManager connectionManager, ClientHandler clientHandler) {
        this.world = world;
        this.connectionManager = connectionManager;
        this.clientHandler = clientHandler;
    }

    public void disconnect() {
        System.out.println("Disconnecting client: " + (clientHandler.getClientName() != null ? clientHandler.getClientName() : "unknown"));
        connectionManager.stop();
    }

    public void removeRobot() {
        if (robot != null) {
            world.removeRobot(robot);
            System.out.println("Robot '" + robot.getName() + "' removed from world.");
        }
    }


    public Response handleClientCommand(String msgFromClient) {
        Map<String, Object> data = new HashMap<>();
        Request request;
        Command command;

        command = Command.create(msgFromClient.toLowerCase());
        if (command == null) {
            return new Response("ERROR", Map.of("message", "Unsupported command: " + msgFromClient), null);
        }

        Map<String, Object> args = new HashMap<>();
        String arg = command.getArgument();

        //assign args based on cmd name
        if (arg != null && !arg.isEmpty()) {
            switch (command.getName()) {
                case "turn":
                    args.put("direction", arg);
                    break;
                case "forward":
                case "back":
                    args.put("steps", arg);
                    break;
                case "launch":
                    String[] parts = arg.split("\\s+"); //take into account robot type
                    if (parts.length == 2) {
                    args.put("type", parts[0]);
                    args.put("name", parts[1]);}
                    else{
                        args.put("name", arg);
                    }
                    break;


            }
        }

        request = new Request(command.getName(), args);

        try {
            String cmdName = request.getCommand();

            if ("launch".equalsIgnoreCase(cmdName)||"l".equalsIgnoreCase(cmdName)) {
//            if ("launch".equalsIgnoreCase(cmdName)) {
                String name = (String) request.getArguments().get("name");
                String typeName = (String) request.getArguments().get(("type"));

                if (typeName == null) {
                    data.put("message", "Launch command needs a robot type.");
                    return new Response("ERROR", data, null);
                }

                //Block clients trying to launch more than one robot
                if (this.robot != null) {
                    data.put("message", "A robot has already been launched for this client.");
                    return new Response("ERROR", data, null);
                }

                //Check world for duplicate robot names
                boolean nameTaken = world.getAllRobots().stream()
                        .anyMatch(r -> r.getName().equalsIgnoreCase(name));
                if (nameTaken) {
                    data.put("message", "Too many of you in this world (name taken)");
                    return new Response("ERROR", data, null);
                }

                RobotType type = RobotCreator.createRobotType(typeName);
                if (type == null) { //if no robot gets created aka type doesn't exist
                    data.put("message", "Unknown robot type: " + typeName);
                    return new Response("ERROR", data, null);
                }

                // If all good, launch the robot
                Position startPos = world.getRandomFreePosition();
                this.robot = new Robot(name, world, startPos, type);
                world.addRobot(this.robot);

                Position pos = robot.getPosition();
                data.put("position", new int[]{pos.getX(), pos.getY()});
                data.put("visibility","Hardcode");
                data.put("reload","Hardcode");
                data.put("repair","Hardcode");
                data.put("shield", type.getMaxShieldStrength());

                return new Response("OK", data, robot);

            }//If robot has not been launched yet
            else if (robot == null) {
                return new Response("ERROR", Map.of("message", "Please launch a robot first using: launch <type> <name>"), null);
            }// Check if robot died during this command
            else if (robot.getStatus().equals("DEAD")) {
                clientHandler.markRobotAsDead();
                robot.getWorld().removeRobot(robot);  // cleanup from world
                return new Response("DEAD", Map.of("message", "Your robot has been destroyed!\n GAME OVER"), null);
            } else if (robot.getRobotHealth()==0) {
                robot.setStatus("DEAD");
                robot.getWorld().removeRobot(robot);  // cleanup from world
                return new Response("DEAD", Map.of("message", "YOU FELL INTO A hole! GAME OVER"), null);
            } else if ("quit".equalsIgnoreCase(cmdName)) {
                world.removeRobot(robot);
                clientHandler.disconnect();
                return null; // Signal to break the loop
            }// Only allow status check or repair command
            else if (robot.getIsRepairing()){
                return new Response("FAILED", Map.of(
                        "message", "Robot is currently repairing. Please wait."
                ), robot);
            } else {
                if ("repair".equalsIgnoreCase(cmdName)) {
                    // Directly create and execute RepairCommand
                    RepairCommand repairCommand = new RepairCommand();
                    return repairCommand.execute(robot);
                }
                // Reconstruct full command string from name + args
                String argument = (String) request.getArguments().get("steps"); // for forward/back
                if (argument == null) {
                    argument = (String) request.getArguments().get("direction"); // for turn
                }
                String reconstructed = cmdName + (argument != null ? " " + argument : "");
                command = Command.create(reconstructed);
                if (command == null) {
                    return new Response("ERROR", Map.of("message", "Invalid command"), null);
                }
            return robot.handleCommand(command);
            }
        } catch (IllegalArgumentException e) {
            return new Response("ERROR", Map.of("message", e.getMessage()), null);
        }
    }
}
