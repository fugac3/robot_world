package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.ReloadCommand;
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
    private final ClientHandler clientHandler;
    Map<String, Object> data = new HashMap<>();

    public CommandHandler(TextWorld world, ClientHandler clientHandler) {
        this.world = world;
        this.clientHandler = clientHandler;
    }

    public void removeRobot() {
        if (robot != null) {
            world.removeRobot(robot);
            System.out.println("Robot '" + robot.getName() + "' removed from world.");
        }
    }

    public Response handleClientCommand(String msgFromClient) {
        Command command = Command.create(msgFromClient.toLowerCase());
        if (command == null) {
            return new Response("ERROR", Map.of("message", "Unsupported command: " + msgFromClient), null);
        }
        String arg = command.getArgument();
        Map<String, Object> args = parseArgsForCommand(command, arg);

        Request request = new Request(command.getName(), args);
        return routeRequest(request);
    }

    private Response routeRequest(Request request) {
        String cmdName = request.getCommand();
        String name = (String) request.getArguments().get("name");
        String type = (String) request.getArguments().get("type");

        if ("launch".equalsIgnoreCase(cmdName)) {
            return LaunchChecker(name,type);
        }

        if (robot == null) {
            data.put("message", "Please launch a robot first using: launch type name");
            return new Response("ERROR",data, null);
        }

        if (robot.getStatus().equals("DEAD")) {
            String message = "Your robot has been destroyed! GAME OVER";
            return new Response("DEAD", Map.of("message", message), null);
        }

        if (robot.getRobotHealth() == 0) {
            robot.setStatus("DEAD");
            String message = "you fell into a bottomless pit! GOOD JOB";
            return new Response("DEAD", Map.of("message", message), null);
        }

        if ("quit".equalsIgnoreCase(cmdName)) {
            world.removeRobot(robot);
            clientHandler.disconnect();
            return null;
        }
        if (robot.getIsRepairing()) {
            data.put("message","Robot is currently repairing. Please wait.");
            return new Response("ERROR",data, null);
        }
        if (robot.getIsReloading()) {
            data.put("message","Robot is currently reloading. Please wait.");
            return new Response("ERROR",data, null);
        }
        return handleOtherCommand(request);
    }

    private Response handleOtherCommand(Request request) {
        String cmdName = request.getCommand();
        // Directly create and execute RepairCommand/ReloadCommand
        switch (cmdName.toLowerCase()) {
            case "repair":
                return new RepairCommand().execute(robot);
            case "reload":
                return new ReloadCommand().execute(robot);
            default:
                return handleMovementOrCustomCommand(request);
        }
    }

    private Response handleMovementOrCustomCommand(Request request) {
        String arg = (String) request.getArguments().get("steps");
        if (arg == null) {
            arg = (String) request.getArguments().get("direction");
        }

        String reconstructed = request.getCommand() + (arg != null ? " " + arg : "");
        Command command = Command.create(reconstructed);

        if (command == null) {
            data.put("message","Command cannot be blank");
            return new Response("ERROR",data, null);
        }

        return robot.handleCommand(command);
    }

    //launch logic
    public Response LaunchChecker(String robotName,String robotTypeName) {

//        if(robot!=null ||  world.getRobotByName(robotName) != null){
//            data.put("message","Too many of you in this world");
//            return new Response("FAILED",data,null);
//        }

        if (world.getAllRobots().stream().anyMatch(r -> r.getName().equals(robotName))) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Robot already added");
            return new Response("FAILED", data, null);
        }

        // Check if robot name is null or empty
        if (robotName == null || robotName.trim().isEmpty()) {
            data.put("message","Launch command needs a name.");
            return new Response("ERROR",data, null);
        }

        // Check if robot type is null or empty
        if (robotTypeName == null || robotTypeName.trim().isEmpty()) {
            data.put("message","Launch command needs a robot type.");
            return new Response("ERROR",data, null);
        }

        // Check if robot type is valid
        RobotType type = RobotCreator.createRobotType(robotTypeName);
        if (type == null) {
            data.put("message","Unknown robot type: " + robotTypeName);
            return new Response("ERROR",data, null);
        }
        // Check if name is taken
        boolean nameTaken = world.getAllRobots().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(robotName));
        if (nameTaken) {
            data.put("message","Too many of you in this world (name taken)");
            return new Response("ERROR",data, null);
        }

        // Launch the robot
        Position startPos = world.getRandomFreePosition();
        this.robot = new Robot(robotName, world, startPos, type);
        world.addRobot(this.robot);

        Position pos = robot.getPosition();
        data.put("position", new int[]{pos.getX(), pos.getY()});
        data.put("visibility", world.getConfig().visibilityConstraint);
        data.put("reload", robot.getReloadTime());
        data.put("repair", robot.getRepairTime());
        data.put("shield", type.getMaxShieldStrength());

        return new Response("OK", data, robot);
    }

    //arg parser
    private Map<String, Object> parseArgsForCommand(Command command, String arg) {
        Map<String, Object> args = new HashMap<>();

        if (arg != null && !arg.isEmpty()) {
            switch (command.getName()) {
                case "turn":
                    args.put("direction", arg.trim());
                    break;

                case "forward":
                case "back":
                    args.put("steps", arg.trim());
                    break;

                case "launch":
                    String[] parts = arg.trim().split("\\s+");
                    if (parts.length == 2) {
                        args.put("type", parts[0]);
                        args.put("name", parts[1]);
                    } else {
                        args.put("name", arg.trim());
                    }
                    break;
            }
        }
        return args;
    }
}