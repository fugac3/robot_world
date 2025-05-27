package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The RobotList class provides a method to retrieve information about all the robots
 * in a given world. It collects details like name, position, direction, ammo, and status
 * for each robot present in the world.
 */
public class RobotList {

    /**
     * Retrieves information about all the robots present in the world associated with the given robot.
     * The information includes the robot's name, position, direction, ammo count, and status.
     *
     * @param robot the robot whose world will be checked for other robots
     * @return a list of maps, where each map contains the information about a robot in the world
     *         or `null` if the world is not a {@link TextWorld}.
     */
    public static List<Map<String, Object>> getAllRobotsInfo(Robot robot) {
        // Ensure the robot's world is an instance of TextWorld
        if (robot.getWorld() == null) {
            return null;
        }

        // Cast the world to TextWorld
        TextWorld world = (TextWorld) robot.getWorld();

        // Create a list to store information about all robots
        List<Map<String, Object>> robotInfoList = new ArrayList<>();

        // Iterate over all robots in the world
        for (Robot r : world.getAllRobots()) {
            Map<String, Object> robotInfo = new HashMap<>();

            // Add robot's details to the map
            robotInfo.put("name", r.getName());
            Position pos = r.getPosition();
            robotInfo.put("position", new int[]{pos.getX(), pos.getY()});
            robotInfo.put("direction", r.getCurrentDirection());
            robotInfo.put("shots", r.getAmmo());
            robotInfo.put("status", r.getStatus());

            // Add the robot's information to the list
            robotInfoList.add(robotInfo);
        }

        return robotInfoList;
    }
}


