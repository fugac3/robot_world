package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class QuitCommand extends Command {

    public QuitCommand() {
        super("quit");
    }

    @Override
    public Response execute(Robot robot) {
        return new Response("EXIT", "Server shutting down.", null);
    }
}
