package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class DumpCommand extends Command{
    public DumpCommand() {
        super("dump");
    }

    @Override
    public boolean execute(Robot target) {
        return true;
    }
}
