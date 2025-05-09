package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class QuitCommand extends Command {

    public QuitCommand() {
        super("quit");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        data.put("message","Server shutting down.");
        return new Response("EXIT", data, null);
    }
}
