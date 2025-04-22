package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class LeftCommand extends Command {
    public LeftCommand() {
        super("left");
    }

    @Override
    public boolean execute(Robot target) {
        target.turnLeft();
        target.setStatus("Turned left.");
        return true;
    }
}
