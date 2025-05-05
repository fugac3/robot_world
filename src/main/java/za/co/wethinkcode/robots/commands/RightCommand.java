package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.lang.annotation.Repeatable;
import java.util.HashMap;
import java.util.Map;

public class RightCommand extends Command {
    public RightCommand() {
        super("right");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        robot.turnRight();
        data.put("message","Done");
        return new Response("OK", data, robot);
    }
}
