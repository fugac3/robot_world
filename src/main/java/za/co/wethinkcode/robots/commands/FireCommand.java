package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class FireCommand extends Command {

    public FireCommand() {
        super("fire");
    }

    @Override
    public Response execute(Robot robot) {
        if (robot.getAmmo() <= 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Miss");

            Map<String, Object> state = new HashMap<>();
            state.put("shots", robot.getAmmo());

            return new Response("FAILED", data, robot);
        }
        boolean fired = robot.fire();

        Map<String, Object> data = new HashMap<>();
        data.put("message", fired ? "Hit" : "Miss");

        Map<String, Object> state = new HashMap<>();
        state.put("shots", robot.getAmmo());

        return new Response("OK", data, robot);
    }
}