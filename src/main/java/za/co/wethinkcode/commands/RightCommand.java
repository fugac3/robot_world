package za.co.wethinkcode.commands;

import za.co.wethinkcode.robot.Robot;

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
