package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class LookCommand extends Command{
    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(Robot target) {
        return true;
    }
}
