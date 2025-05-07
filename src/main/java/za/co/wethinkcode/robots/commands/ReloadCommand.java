package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class ReloadCommand extends Command {
    public ReloadCommand() {super("reload");}

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        boolean reloaded = robot.reload();
        data.put("message", "Done");
        return reloaded ? new Response("OK", data, robot) : new Response("FAILED", data, robot);
    }
}
