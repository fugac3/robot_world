package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class LookCommand extends Command {
    public LookCommand() {
        super("look");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, String> lookInfo = new HashMap<>();
        lookInfo.put("Position",robot.getPosition().toString());
        lookInfo.put("Obstacle", robot.getWorld().getObstacles().toString());
        lookInfo.put("State", robot.getStatus());

        return new Response("Ok","Robot looking around the world",Map.of(robot.getName(),lookInfo));
    }
}
