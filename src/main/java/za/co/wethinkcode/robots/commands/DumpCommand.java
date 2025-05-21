package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DumpCommand is responsible for collecting and returning a snapshot of the current
 * state of obstacles and robots in the world. It gathers details about all obstacles and
 * robots present in the world and returns this information as part of a response.
 */
public class DumpCommand extends Command {

    /**
     * Constructs a new DumpCommand with the default name "dump".
     */
    public DumpCommand() {
        super("dump");
    }

    /**
     * Executes the dump command by gathering details about the current obstacles and robots
     * in the world and returning them in a structured response.
     *
     * @param robot the {@link Robot} executing the command
     * @return a {@link Response} containing the data about obstacles and robots, or an error response
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        // Collect obstacle data
        List<Map<String, Object>> obstacleData = new ArrayList<>();
        for (Obstacle o : robot.getWorld().getObstacles()) {
            Map<String, Object> obsInfo = new HashMap<>();
            obsInfo.put("type", o.getClass().getSimpleName());  // Get the type of obstacle (e.g., MountainObstacle)
            obsInfo.put("bottomLeft", "(" + o.getBottomLeftX() + "," + o.getBottomLeftY() + ")");
            obsInfo.put("topRight", "(" + o.getTopRightX() + "," + o.getTopRightY() + ")");
            obstacleData.add(obsInfo);
        }

        // Collect robot list information
        List<Map<String, Object>> robotList = RobotList.getAllRobotsInfo(robot);

        // If there was an error retrieving robot data, return an error response
        if (robotList == null) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", "Failed to retrieve robot list.");
            return new Response("ERROR", errorData, robot);
        }

        // Set the robot's status to "NORMAL"
        robot.setStatus("NORMAL");

        // Add the obstacle and robot data to the response data
//        data.put("Obstacles", obstacleData);
        if (obstacleData.isEmpty()) {
            data.put("obstacles", "None");
        } else {
            data.put("Obstacles", obstacleData);
        }
        data.put("robots", robotList);

        // Return the response with the gathered data
        return new Response("OK", data, robot);
    }
}
