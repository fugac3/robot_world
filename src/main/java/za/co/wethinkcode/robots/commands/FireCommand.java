package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.combat.HitResult;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.combat.Bullet;

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
     * Executes the fire command:
     * - Checks if the robot has ammo to fire.
     * - If no ammo, returns an "Out of ammo" response.
     * - Otherwise, consumes a bullet, creates a bullet object, and checks for a hit.
     * - Returns either "Hit", "Miss", or "You have destroyed ..." based on the result.
     */
    @Override
    public Response execute(Robot robot) {
        // Check if the robot has ammo to fire
        if (robot.getAmmo() <= 0) {
            robot.setStatus("NORMAL");
            // If no ammo, return "Out of ammo" response
            return new Response("ERROR", Map.of("message", "No ammo"), robot);
        }

        // Consume a bullet
        robot.setAmmo(1);

        // Create a new bullet travelling in the current direction
        Bullet bullet = new Bullet(robot.getPosition(), robot.getCurrentDirection(), robot.getShootingRange(), robot);

        // Check for collision with robots and determine hit result
        HitResult result = addBulletAndCheckHit(bullet, robot);

        // Build response based on hit or miss
        Response response = (result.hitRobot != null)
                ? buildHitResponse(robot, result, bullet.getDistanceLeft())
                :buildMissResponse(robot);

        robot.setStatus("NORMAL");
        return response;
    }

    /**
     * Constructs a response for a hit:
     * - If the hit robot is dead, returns a destruction message.
     * - Otherwise, returns hit details (robot name, state, distance).
     */
    private Response buildHitResponse(Robot robot, HitResult result, int distance) {
        if ("DEAD".equals(result.hitRobot.getStatus())) {
            // If the hit robot is dead, return destruction message
            return new Response("OK", Map.of("message", "You have destroyed " + result.hitRobot.getName()), robot);
        }
        // Constructing hit bot's data
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hit");
        data.put("robot", result.hitRobot.getName());
        data.put("state", Response.buildState(result.hitRobot));
        data.put("distance", distance);
        result.hitRobot.setStatus("HIT");
        return new Response("OK", data, robot);
    }

    /**
     * Constructs a response for a miss.
     */
    private Response buildMissResponse(Robot robot) {
        return new Response("OK", Map.of("message", "Miss"), robot);
    }

    /**
     * Moves the bullet and checks for collision with robots:
     * - If a robot (other than the shooter) is hit, applies damage and returns hit result.
     * - Otherwise, returns a miss result.
     */
    public HitResult addBulletAndCheckHit(Bullet bullet, Robot robot) {
        while (!bullet.hasStopped()) {
            bullet.move();
            for (Robot other : robot.getWorld().getAllRobots()) {
                if (!other.equals(bullet.getShooter()) && other.getPosition().equals(bullet.getPosition())) {
                    // Apply damage to the hit robot
                    other.applyDamage(1);
                    return new HitResult(true, other);
                }
            }
        }
        // No robot was hit
        return new HitResult(false, null);
    }
}
