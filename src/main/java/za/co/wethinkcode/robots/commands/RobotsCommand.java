package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class RobotsCommand extends Command{
    public RobotsCommand() {
        super("robots");
    }

    @Override
    public boolean execute(Robot target) {
        return true;
    }
}
