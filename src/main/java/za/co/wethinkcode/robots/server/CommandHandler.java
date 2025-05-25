package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.RobotTypes.RobotType;
import za.co.wethinkcode.robots.RobotTypes.RobotTypeFactory;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler{
    private final TextWorld world;
    private Robot robot;
    private final ConnectionManager connectionManager;

    public CommandHandler(TextWorld world, ConnectionManager connectionManager) {
        this.world = world;
        this.connectionManager = connectionManager;
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

                RobotType type = RobotTypeFactory.createRobotType(typeName);
                if (type == null) { //if no robot gets created aka type doesn't exist
                    data.put("message", "Unknown robot type: " + typeName);
                    return new Response("ERROR", data, null);
                }

                // If all good, launch the robot
                Position startPos = world.getRandomFreePosition();
                this.robot = new Robot(name, world, startPos, type);
                world.addRobot(this.robot);

                data.put("message", "Robot successfully launched.");
                Position pos = robot.getPosition();
                data.put("position", new int[]{pos.getX(), pos.getY()});
                data.put("type", type.getTypeName());
                data.put("shield", type.getMaxShieldStrength());

                return new Response("OK", data, robot);

            } else if ("quit".equalsIgnoreCase(cmdName)) {
                Server.shutdownServer();
                connectionManager.stop();
                return null; // Signal to break the loop
            } else { // All other commands after launch
                Object stepsArg = request.getArguments().get("steps");
                String reconstructed = cmdName + (stepsArg != null ? " " + stepsArg : "");
                command = Command.create(reconstructed);

                //If robot has not been launched yet
                if (robot == null) {
                    return new Response("ERROR", Map.of("message", "Please launch a robot first using: launch <type> <name>"), null);
                }

                assert command != null;
                return robot.handleCommand(command);
            }
        } catch (IllegalArgumentException e) {
            return new Response("ERROR", Map.of("message", e.getMessage()), null);
        }
    }

}
