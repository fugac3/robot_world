package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class CurrentDirectionCommand extends Command {
    public CurrentDirectionCommand() {
        super("orientation");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        data.put("message","The current direction is: "+robot.getCurrentDirection());
        return new Response("OK",data,null);
    }

}




