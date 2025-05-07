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

        List<Map<String, Object>> robotList = RobotList.getAllRobotsInfo(robot);

        if (robotList == null) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("message", "Failed to retrieve robot list.");
            return new Response("ERROR", errorData, robot);
        }

        state.put("robots", robotList);

        robot.setStatus("NORMAL");

        return new Response("OK", state, robot);

    }
}
