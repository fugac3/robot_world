package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumpCommand extends Command {
    public DumpCommand() {
        super("dump");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String,Object> data = new HashMap<>();

        data.put("Robot name", robot.getName());
        data.put("Position", robot.getPosition());
        data.put("Direction", robot.getCurrentDirection());

        List<Map<String, Object>> obstacleData = new ArrayList<>();
        for (Obstacle o : robot.getWorld().getObstacles()) {
            Map<String, Object> obsInfo = new HashMap<>();
            obsInfo.put("type", o.getClass().getSimpleName());
            obsInfo.put("bottomLeft", "(" + o.getBottomLeftX() + "," + o.getBottomLeftY() + ")");
            obsInfo.put("topRight", "(" + o.getTopRightX() + "," + o.getTopRightY() + ")");
            obstacleData.add(obsInfo);
        }

        data.put("Obstacles", obstacleData);

        return new Response("OK", data, robot);
    }

}
