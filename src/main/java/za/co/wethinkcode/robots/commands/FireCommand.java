package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The FireCommand class represents the action of firing a weapon by a robot.
 * It checks if the robot has sufficient ammunition and attempts to fire.
 * It returns a response with the result of the action (Hit or Miss).
 */
public class FireCommand extends Command {

    /**
     * Constructs a new FireCommand with the default name "fire".
     */
    public FireCommand() {
        super("fire");
    }

    /**
     * Executes the fire command by checking the robot's ammo and attempting to fire.
     * If the robot has no ammo, it returns a "Miss" message. Otherwise, it tries to fire
     * and returns either "Hit" or "Miss" based on the result.
     *
     * @param robot the {@link Robot} that is executing the command
     * @return a {@link Response} object containing the result of the fire attempt
     */
    @Override
    public Response execute(Robot robot) {
        // Check if the robot has ammo to fire
        if (robot.getAmmo() <= 0) {
            // If no ammo, return a "Miss" response with the remaining shots count
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Miss");

            Map<String, Object> state = new HashMap<>();
            state.put("shots", robot.getAmmo());

            return new Response("FAILED", data, robot);
        }

        // Try to fire the weapon
        boolean fired = robot.fire();

        // Prepare the response data based on whether the shot was successful
        Map<String, Object> data = new HashMap<>();
        data.put("message", fired ? "Hit" : "Miss");

        Map<String, Object> state = new HashMap<>();
        state.put("shots", robot.getAmmo());

        return new Response("OK", data, robot);
    }
}
