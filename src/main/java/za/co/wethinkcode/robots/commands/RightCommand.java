package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class RightCommand extends Command {
    public RightCommand() {
        super("right");
    }

    @Override
    public boolean execute(Robot robot) {
        robot.turnRight();
        robot.setStatus("Turned right.");
        return true;
    }
}
