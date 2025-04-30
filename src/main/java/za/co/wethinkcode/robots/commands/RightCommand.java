package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

import java.lang.annotation.Repeatable;

public class RightCommand extends Command {
    public RightCommand() {
        super("right");
    }

    @Override
    public Response execute(Robot robot) {
        robot.turnRight();
        Response.setStatus("Turned right.");
        return new Response("OK", "Robot turned right.", robot.getState());
    }
}
