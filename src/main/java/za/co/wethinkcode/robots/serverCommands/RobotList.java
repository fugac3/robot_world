package za.co.wethinkcode.robots.serverCommands;

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
    public static List<Map<String, Object>> getAllRobotsInfo() {
        // Create a list to store information about all robots
        List<Map<String, Object>> robotInfoList = new ArrayList<>();

        // Iterate over all robots in the world
        for (Robot r : TextWorld.getInstance().getAllRobots()) {
            Map<String, Object> robotInfo = new HashMap<>();

            robotInfo.put("name", r.getName());
            robotInfo.put("type", r.getTypeName());
            Position pos = r.getPosition();
            robotInfo.put("position", new int[]{pos.getX(), pos.getY()});
            robotInfo.put("direction", r.getCurrentDirection());
            robotInfo.put("shields", r.getCurrentShieldStrength());
            robotInfo.put("shots", r.getAmmo());
            robotInfo.put("status", r.getStatus());

            robotInfoList.add(robotInfo);
        }

        return robotInfoList;
    }
}


