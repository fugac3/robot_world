package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class RepairCommand extends Command {
    public RepairCommand() {
        super("repair");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        if (robot.getIsRepairing()) {
            robot.setStatus("NORMAL");
            data.put("message", "Repair in progress");
            data.put("repairingTime", 5); // or dynamically tracked time if implemented
            return new Response("FAILED", data, robot);
        }

        if (robot.getMaxShieldStrength() == robot.getCurrentShieldStrength()) {

            robot.setStatus("NORMAL");
            data.put("message", "Shield already at max strength");
            data.put("shieldStrength", robot.getCurrentShieldStrength());
            return new Response("FAILED", data, robot);
        }

        boolean startedRepair = robot.repairing();
        robot.setStatus("REPAIR");
        data.put("message", startedRepair ? "Repair started" : "Could not start repair");
        data.put("shieldStrength", robot.getCurrentShieldStrength());

        return new Response("OK", data, robot);
    }


}
