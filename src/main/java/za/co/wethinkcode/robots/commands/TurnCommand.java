package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TurnCommand extends Command {
    private final String argument;

    @Override
    public Response execute(Robot robot) {
        Map<String, Object> data = new HashMap<>();

        String direction = argument.trim().toLowerCase();


        switch (direction) {
            case "left":
                robot.turnLeft();
                break;
            case "right":
                robot.turnRight();

                break;
            default:
                data.put("message", "Invalid turn direction. Use 'left' or 'right'.");
                return new Response("ERROR", data, robot);
        }

        data.put("message", "Turned r" );
        return new Response("OK", data, robot);
    }


    public TurnCommand(String argument) {
        super("turn", argument);
        this.argument = argument;
    }
}
