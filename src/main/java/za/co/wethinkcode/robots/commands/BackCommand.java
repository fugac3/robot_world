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
        boolean moved;
        Map<String, Object> data = new HashMap<>();

        try {
            int steps = Integer.parseInt(argument);
            moved = robot.updatePosition(-steps);
        } catch (NumberFormatException e) {
            data.put("message", "Invalid steps: must be a number.");
            robot.setStatus("NORMAL");
            return new Response("ERROR", data, robot);
        }

        if (moved) {
            data.put("message", "Done");
            robot.setStatus("NORMAL");
            return new Response("OK", data, robot);
        }
        else {
            data.put("message", robot.getLastMoveReason());
            robot.setStatus("NORMAL");
            return new Response("FAILED", data, robot);
        }
    }
}

