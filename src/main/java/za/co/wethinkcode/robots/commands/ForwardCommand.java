package za.co.wethinkcode.robots.commands;

//import java.awt.*;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to move the robot forward.
 * This command moves the robot forward by the specified number of steps in the direction it is facing.
 */
public class ForwardCommand extends Command {

    private final String argument;

    /**
     * Creates a new ForwardCommand instance.
     *
     * @param argument The number of steps to move forward
     */
    public ForwardCommand(String argument) {
        super("forward", argument);
        this.argument = argument;
    }

    /**
     * Executes the forward command, moving the robot forward by the specified number of steps.
     *
     * @param robot The robot to move forward
     * @return A response indicating success or failure:
     *         - OK if the robot moved successfully
     *         - FAILED if the robot was obstructed or reached the edge of the world
     *         - ERROR if the steps argument is not a valid number
     */
    @Override
    public Response execute(Robot robot) {
        boolean moved;
        Map<String, Object> data = new HashMap<>();

        try {
            int steps = Integer.parseInt(argument);
            moved = robot.updatePosition(steps);
        } catch (NumberFormatException e) {
            data.put("message","Invalid steps: steps must be a number.");
            robot.setStatus("NORMAL");
            return new Response("ERROR",data,null);
        }

        if (moved) {
            data.put("message", "Done");
            robot.setStatus("NORMAL");
            return new Response("OK", data, robot);
        }else {
            data.put("message", robot.getLastMoveReason());
            robot.setStatus("NORMAL");
            return new Response("FAILED", data, robot);
        }
    }

    /**
     * Returns a string representation of this command.
     *
     * @return A string describing this command
     */
    @Override
    public String toString() {
        // This will be used for logging the command
        return "Forward Command " + getArgument() + " steps";
    }

}

