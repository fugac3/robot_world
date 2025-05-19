package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to turn the robot left.
 * This command changes the direction the robot is facing by turning it 90 degrees to the left.
 */
public class LeftCommand extends Command {

    /**
     * Creates a new LeftCommand instance.
     */
    public LeftCommand() {
        super("left");
    }

    /**
     * Executes the left command, turning the robot 90 degrees to the left.
     *
     * @param robot The robot to turn left
     * @return A response indicating success
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        robot.turnLeft();
        data.put("message","Done");
        return new Response("OK", data, robot);
    }
}
