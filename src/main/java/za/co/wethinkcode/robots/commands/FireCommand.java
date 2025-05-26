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
     * Executes the fire command by checking the robot's ammo and attempting to fire.
     * and returns either "Hit" or "Miss" based on the result.
     * @return a {@link Response} object containing the result of the fire attempt
     */
    @Override
    public Response execute(Robot robot) {
        // Check if the robot has ammo to fire
        int currentAmmo = robot.getAmmo();
        if (currentAmmo <= 0) {
            // If no ammo, return "Out of ammo" response.
            robot.setStatus("NORMAL");
            return new Response("ERROR", Map.of("message", "No ammo"), robot);
        }else {
            robot.setAmmo(1); // consuming a bullet
            // Create a new bullet travelling in the current direction
            Bullet bullet = new Bullet(robot.getPosition(), robot.getCurrentDirection(), robot.getShootingRange(), robot);
            int distance = bullet.getDistanceLeft();
            HitResult result = addBulletAndCheckHit(bullet,robot);

            Map<String, Object> data = new HashMap<>();

            if (result.hitRobot != null) {
                if (result.hitRobot.getStatus().equals("DEAD")) {
                    return new Response("OK", Map.of("message", "You have destroyed "+result.hitRobot.getName()), robot);
                }
                //constructing hit bots data
                data.put("message", "Hit");
                data.put("robot", result.hitRobot.getName());
                data.put("state", Response.buildState(result.hitRobot));
                data.put("distance", distance);
                result.hitRobot.setStatus("HIT");
                } else {
                    data.put("message", "Miss");
                }

            robot.setStatus("NORMAL");
            return new Response("OK", data, robot);
        }
    }

    public HitResult addBulletAndCheckHit(Bullet bullet,Robot robot) {
        // Move the bullet and check for collision with robots
        while (!bullet.hasStopped()) {
            bullet.move();
            for (Robot listRobot : robot.getWorld().getAllRobots()) {
                if (!listRobot.equals(bullet.getShooter()) && listRobot.getPosition().equals(bullet.getPosition())) {
                    // Apply damage here!
                    listRobot.applyDamage(1);  // Apply damage, adjust as needed
                    return new HitResult(true, listRobot);
                }
            }
        }
        return new HitResult(false, null);
    }
}
