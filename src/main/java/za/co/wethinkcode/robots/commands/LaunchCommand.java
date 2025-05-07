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
        Map<String, Object> data = new HashMap<>();

        if (robot == null) {
            data.put("message", "Robot not initialized.");
            return new Response("ERROR", data, null);
        }
        data.put("position", robot.getPosition());
        robot.setStatus("NORMAL");
        return new Response("OK", data, robot);
    }

    public String getRobotName() {
        return robotName;
    }
}



