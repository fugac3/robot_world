package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;

public class TurnCommand extends Command {
    private final String argument;

    public TurnCommand(String argument) {
        super("turn", argument);
        this.argument = argument;
    }

    /**
     * Executes the command on the specified robot.
     *
     * @param robot The robot that will execute the command
     * @return A response indicating the result of the command execution
     */
    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();
        if(argument.equals("right")) {
            robot.turnRight();
        }
        if(argument.equals("left")) {
            robot.turnLeft();
        }
        data.put("message","Done");
        return new Response("OK", data, robot);
    }




}
