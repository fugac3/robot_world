package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The ReloadCommand class represents a command to reload the robot's ammo.
 * When executed, it attempts to reload the robot's ammunition and returns a response indicating success or failure.
 */
public class ReloadCommand extends Command {

    /**
     * Constructs a new ReloadCommand.
     * The "reload" command is used to reload the robot's ammunition.
     */
    public ReloadCommand() {
        super("reload");
    }

    /**
     * Executes the reload command. This method triggers the robot to reload its ammunition.
     * The method returns a response indicating whether the reload operation was successful or not.
     *
     * @param robot the robot that triggered the reload command
     * @return a {@link Response} object indicating whether the reload operation was successful or failed
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        // Check if the robot is dead
        if ("DEAD".equals(robot.getStatus())) {
            data.put("message", "Cannot reload: robot is dead.");
            return new Response("FAILED", data, robot);
        }

        // Attempt to reload the robot's ammo
        boolean reloaded = robot.reload();
        robot.setStatus("NORMAL");
        // Set the message and return an appropriate response
        data.put("message", "Done");

        return reloaded ? new Response("OK", data, robot) : new Response("FAILED", data, robot);
    }
}
