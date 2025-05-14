package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class LaunchCommand extends Command {

    public LaunchCommand(String robotName) {
        super("launch", robotName);
    }

    @Override
    public Response execute(Robot robot) {
        return null;
    }
}