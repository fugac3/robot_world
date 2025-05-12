package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The RobotsCommand class is responsible for retrieving and displaying a list of all robots in the world.
 * It uses the {@link RobotList} class to gather information about the robots in the world and returns the information
 * in a structured format.
 */
public class RobotsCommand extends Command {

    /**
     * Constructs a RobotsCommand instance with the command name "robots".
     */
    public RobotsCommand() {
        super("robots");
    }

    /**
     * Executes the "robots" command, which retrieves the list of all robots in the world.
     * It uses the {@link RobotList#getAllRobotsInfo(Robot)} method to gather information about the robots.
     * If the robot list is successfully retrieved, it returns the list along with an "OK" response.
     * If there is an error in retrieving the list, it returns an error message with an "ERROR" response.
     *
     * @param robot the robot executing the command
     * @return a {@link Response} containing the list of all robots in the world or an error message
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> state = new HashMap<>();

        // Retrieve information about all robots
        List<Map<String, Object>> robotList = RobotList.getAllRobotsInfo(robot);

        // Check if the robot list is null (indicating an error in retrieving the list)
        if (robotList == null) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", "Failed to retrieve robot list.");
            return new Response("ERROR", errorData, robot);
        }

        // Add the robot list to the response data
        state.put("robots", robotList);

        // Set the robot's status to "NORMAL"
        robot.setStatus("NORMAL");

        // Return the response with the robot list
        return new Response("OK", state, robot);
    }
}
