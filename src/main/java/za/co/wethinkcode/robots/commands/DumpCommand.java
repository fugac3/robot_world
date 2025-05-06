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
        Map<String,Object>data = new HashMap<>();

        data.put("Robot name: ", robot.getName());
        data.put("Position ", robot.getPosition());
        data.put("Direction: ", robot.getCurrentDirection());
        data.put("Obstacle",robot.getWorld().getObstacles());


        return new Response("OK",data, robot);
    }
}
