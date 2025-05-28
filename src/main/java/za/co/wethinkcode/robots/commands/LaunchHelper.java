package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The LaunchCommand class represents the action of launching a robot in the world.
 * The robot is identified by its name, and the command ensures that the name is provided before the launch.
 */
public class LaunchHelper extends Command {
    private final String robotName;

    /**
     * Constructs a new LaunchCommand with the given robot name.
     *
     * @param robotName the name of the robot to launch
     */
    public LaunchHelper(String robotTypeName, String robotName) {
        super("launch", robotTypeName + " " + robotName);
        this.robotName = robotName;
    }

    /**
     * Executes the launch command, ensuring that the robot name is valid.
     * If the robot name is not provided, it returns an error message.
     * Otherwise, it returns a success message indicating the command was executed.
     *
     * @param robot the robot executing the command (currently not used)
     * @return a {@link Response} object indicating the result of the command execution
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Launch ignored.");
        return new Response("IGNORED", data, null);
    }

}
