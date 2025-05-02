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
        Map<String, String> fireInfo = new HashMap<>();


        return new Response("OK","Hit",Map.of());
    }
}
