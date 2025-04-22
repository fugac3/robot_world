package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        super("off");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return false;
    }
}
