package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class LeftCommand extends Command {
    public LeftCommand() {
        super("left");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        robot.turnLeft();
        data.put("message","Done");
        return new Response("OK", data, robot);
    }
}
