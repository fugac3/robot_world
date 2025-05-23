package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.Bullet;

import java.util.HashMap;
import java.util.Map;

public class RepairCommand extends Command {
    public RepairCommand() {
        super("repair");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> state = new HashMap<>();

        if (robot.getIsRepairing()) {
            data.put("message", "Repair in progress");
            state.put("repairingTime", 5); // or dynamically tracked time if implemented
            return new Response("FAILED", data, robot);
        }

        if (robot.getMaxShieldStrength() == robot.getCurrentShieldStrength()) {

            data.put("message", "Shield already at max strength");
            state.put("shieldStrength", robot.getCurrentShieldStrength());
            return new Response("FAILED", data, robot);
        }

        boolean startedRepair = robot.repairing();

        data.put("message", startedRepair ? "Repair started" : "Could not start repair");
        state.put("shieldStrength", robot.getCurrentShieldStrength());

        return new Response("OK", data, robot);
    }


}
