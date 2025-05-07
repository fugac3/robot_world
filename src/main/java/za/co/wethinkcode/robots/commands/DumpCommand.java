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

//        "data":
        List<Map<String, Object>> obstacleData = new ArrayList<>();
        for (Obstacle o : robot.getWorld().getObstacles()) {
            Map<String, Object> obsInfo = new HashMap<>();
            obsInfo.put("type", o.getClass().getSimpleName());
            obsInfo.put("bottomLeft", "(" + o.getBottomLeftX() + "," + o.getBottomLeftY() + ")");
            obsInfo.put("topRight", "(" + o.getTopRightX() + "," + o.getTopRightY() + ")");
            obstacleData.add(obsInfo);
        }

        List<Map<String, Object>> robotList = RobotList.getAllRobotsInfo(robot);

        if (robotList == null) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", "Failed to retrieve robot list.");
            return new Response("ERROR", errorData, robot);
        }
        robot.setStatus("NORMAL");

        data.put("Obstacles", obstacleData);
        data.put("robots", robotList);

        return new Response("OK", data, robot);
    }

}
