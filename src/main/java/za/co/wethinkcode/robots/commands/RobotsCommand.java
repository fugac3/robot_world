package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotsCommand extends Command{

    public RobotsCommand() {
        super("robots");
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
                robotInfo.put("direction", r.getCurrentDirection());
                robotInfo.put("shots", r.getAmmo());
                robotInfo.put("status", r.getStatus());
                robotList.add(robotInfo);
            }

// extract this into a class and call it in robots and dump
            state.put("robots", robotList);
//            state.put("position", robot.getPosition());
//            state.put("status", );
            robot.setStatus("NORMAL");

            return new Response("OK", state, robot);
        } else {
            state.put("position", robot.getPosition());
            robot.setStatus("error");
//            state.put("status", "World does not support listing robots.");
            return new Response("ERROR", state,robot);
        }
    }
}
