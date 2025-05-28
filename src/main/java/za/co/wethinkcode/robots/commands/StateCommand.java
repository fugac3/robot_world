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
        if ((robot.getCurrentShieldStrength()<robot.getMaxShieldStrength())){
            robot.setStatus("DAMAGED");
        }
        return new Response(null, null,robot);
    }
}
