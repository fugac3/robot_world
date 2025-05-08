package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotList {

    public static List<Map<String, Object>>getAllRobotsInfo(Robot robot) {
        if (!(robot.getWorld() instanceof TextWorld)) {
            return null;
        }

        TextWorld world = (TextWorld) robot.getWorld();

        List<Map<String, Object>> robotInfoList = new ArrayList<>();

        for (Robot r : world.getAllRobots()) {
            Map<String, Object> robotInfo = new HashMap<>();
            robotInfo.put("name", r.getName());
            Position pos = robot.getPosition();
            robotInfo.put("position", new int[]{pos.getX(), pos.getY()});
            robotInfo.put("direction", r.getCurrentDirection());
            robotInfo.put("shots", r.getAmmo());
            robotInfo.put("status", r.getStatus());
            robotInfoList.add(robotInfo);
        }

        return robotInfoList;
    }

}
