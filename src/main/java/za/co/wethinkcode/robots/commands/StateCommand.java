package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class StateCommand extends Command {
    public StateCommand() {
        super("state");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        data.put("Position",robot.getPosition());
        data.put("Direction", robot.getCurrentDirection());
        data.put("ammo", robot.getAmmo());

        Map<String, Object> state = new HashMap<>();
        state.put("shots", robot.getAmmo());

        return new Response("OK", data, robot);
    }
}
