package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

public class LaunchCommand extends Command {
    private final String robotName;
    private Robot robot;

    public LaunchCommand(String robotName) {
        super("launch", robotName);
        this.robotName = robotName;
    }



    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        if (robotName == null || robotName.trim().isEmpty()) {
            data.put("message", "Launch command needs a name.");
            return new Response("ERROR", data, null);
        }

        TextWorld world = (TextWorld) robot.getWorld();

        // Check if a robot with the same name already exists
        if (world.getAllRobots().stream().anyMatch(r -> r.getName().equalsIgnoreCase(robotName))) {
            data.put("message", "A robot with this name already exists.");
            return new Response("ERROR", data, null);
        }else {
            return new Response("OK", data, null);
        }
    }

    public String getRobotName() {
        return robotName;
    }

    public Robot getRobot() {
        return robot;
    }
}



