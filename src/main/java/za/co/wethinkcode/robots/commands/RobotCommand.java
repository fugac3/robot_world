package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotCommand extends Command{

    public RobotCommand() {
        super("robot");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> state = new HashMap<>();

        if (robot.getWorld() instanceof TextWorld) {
            TextWorld world = (TextWorld) robot.getWorld();

            List<Map<String, Object>> robotList = new ArrayList<>();

            for (Robot r : world.getAllRobots()) {
                Map<String, Object> robotInfo = new HashMap<>();
                robotInfo.put("name", r.getName());
                robotInfo.put("position", r.getPosition());
                robotList.add(robotInfo);
            }

            state.put("robots", robotList);
            state.put("position", robot.getPosition());
            state.put("status", "Listed all robots.");

            return new Response("OK", "Command executed.", state);
        } else {
            state.put("position", robot.getPosition());
            state.put("status", "World does not support listing robots.");
            return new Response("ERROR", "World does not support listing robots.", state);
        }
    }
}
