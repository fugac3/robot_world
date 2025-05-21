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
        if (robot.repairing()) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "repairing");

            Map<String, Object> state = new HashMap<>();
            state.put("repairingTime", robot.getAmmo());  // You may want to track the time to repair as well.

            return new Response("FAILED", data, robot);
        }

        // Proceed to repair completion
        boolean repaired = robot.repairing();
        Map<String, Object> data = new HashMap<>();
        data.put("message", repaired ? "Repairing" : "Repaired");

        Map<String, Object> state = new HashMap<>();
        state.put("shieldStrength", robot.getDefence());

        return new Response("OK", data, robot);
    }

}
