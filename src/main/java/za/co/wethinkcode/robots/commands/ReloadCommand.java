package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public Response execute(Robot robot) {
        if (robot == null) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Error: No robot specified.");
            return new Response("ERROR", data, null);
        }

        boolean reloaded = robot.reload();

        Map<String, Object> data = new HashMap<>();
        data.put("message", robot.getStatus());
        data.put("ammo", robot.getAmmo());

        return reloaded ? new Response("OK", data, robot) : new Response("FAILED", data, robot);
    }

    @Override
    public String toString() {
        return "Reload Command executed";
    }
}
