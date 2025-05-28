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
        if (robot.getIsReloading()) {
            robot.setStatus("NORMAL");
            data.put("message", "reload in progress");
            data.put("reload time", robot.getReloadTime());
            return new Response("FAILED", data, robot);
        }

        if (robot.getMaxAmmo() == robot.getAmmo()) {
            robot.setStatus("NORMAL");
            data.put("message", "Ammo already full");
            data.put("Shots", robot.getAmmo());
            return new Response("FAILED", data, robot);
        }

        boolean startedReload = robot.reloading();
        robot.setStatus("RELOAD");
        data.put("message", startedReload ? "Reload started" : "Could not start reload");
        data.put("shots", robot.getAmmo());

        return new Response("OK", data, robot);
    }
}
