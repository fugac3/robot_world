package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class BackCommand extends Command {

    private final String argument;

    public BackCommand(String argument) {
        super("back", argument);
        this.argument = argument;
    }

    @Override
    public Response execute(Robot robot) {
        int steps;

        try {
            steps = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Invalid steps: must be a number.");
            return new Response("ERROR", data, null);
        }

        boolean moved = robot.updatePosition(-steps);

        Map<String, Object> data = new HashMap<>();
        if (moved) {
            data.put("message", "Done");
            return new Response("OK", data, robot);
        }
        else {
            data.put("message", robot.getLastMoveReason());
            return new Response("FAILED", data, robot);
        }
    }
}

