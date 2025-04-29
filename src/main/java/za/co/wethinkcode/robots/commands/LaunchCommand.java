package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.HashMap;
import java.util.Map;

public class LaunchCommand extends Command {
    private final String robotName;

    public LaunchCommand(String robotName) {
        super("launch", robotName);
        this.robotName = robotName;
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> state = new HashMap<>();

        if (robot != null) {
            // Already launched
            state.put("position", robot.getPosition());
            return new Response("ERROR", "Robot already launched.", state);
        }
        Robot newRobot = new Robot(robotName, TextWorld.getInstance()); // or however your world works
        TextWorld.getInstance().addRobot(newRobot);

        state.put("position", newRobot.getPosition());
        state.put("status", "Robot '" + robotName + "' launched successfully.");

        return new Response("OK", "Robot launched successfully.", state);
    }

    public String getRobotName() {
        return robotName;
    }
}
