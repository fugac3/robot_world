package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class LeftCommand extends Command {
    public LeftCommand() {
        super("left");
    }

    @Override
    public Response execute(Robot robot) {
        robot.turnLeft();
        Response.setStatus("Turned left.");
        return new Response("OK", "Robot turned left.", robot.getState());
    }
}
