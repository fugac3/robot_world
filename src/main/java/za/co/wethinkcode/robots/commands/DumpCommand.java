package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import java.util.HashMap;
import java.util.Map;

public class DumpCommand extends Command {
    public DumpCommand() {
        super("dump");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String,String>dumpInfo = new HashMap<>();

        dumpInfo.put("Robot name: ", robot.getName());
        dumpInfo.put("Position ", robot.getPosition().toString());
        dumpInfo.put("Direction: ", robot.getCurrentDirection().toString());
        dumpInfo.put("Obstacle",robot.getWorld().getObstacles().toString());


        return new Response("OK","Robot has been dumped", Map.of(robot.getName(), dumpInfo));
    }
}
