package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.lang.annotation.Repeatable;
import java.util.HashMap;
import java.util.Map;

/**
 * Command to turn the robot right.
 * This command changes the direction the robot is facing by turning it 90 degrees to the right.
 */
public class RightCommand extends Command {
    /**
     * Creates a new RightCommand instance.
     */
    public RightCommand() {
        super("right");
    }

    /**
     * Executes the right command, turning the robot 90 degrees to the right.
     *
     * @param robot The robot to turn right
     * @return A response indicating success
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        robot.turnRight();
        data.put("message","Done");
        return new Response("OK", data, robot);
    }
}
