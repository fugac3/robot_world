package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Command to move the robot backwards.
 * This command moves the robot backward by the specified number of steps in the direction opposite to what it is facing.
 */

public class BackCommand extends Command {

    private final String argument;

    /**
     * Creates a new BackCommand instance.
     *
     * @param argument The number of steps to move backward
     */
    public BackCommand(String argument) {
        super("back", argument);
        this.argument = argument;
    }

    /**
     * Executes the back command, moving the robot backward by the specified number of steps.
     *
     * @param robot The robot to move backward
     * @return A response indicating whether it was a success or failure:
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
            moved = robot.updatePosition(-steps);
        } catch (NumberFormatException e) {
            data.put("message", "Invalid steps: must be a number.");
            robot.setStatus("NORMAL");
            return new Response("ERROR", data, null);
        }

        if (moved) {
            data.put("message", "Done");
            robot.setStatus("NORMAL");
            return new Response("OK", data, robot);
        }else {
            if (Objects.equals(robot.getLastMoveReason(), "pit")){
                data.put("message", "Your robot has fallen into a bottomless pit and has been destroyed! GAME OVER");
                return new Response("Dead", data, null);
            }
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
        return "Back Command " + getArgument() + " steps back";
    }
}

