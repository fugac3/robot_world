package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;

public class SprintCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());

        for (int i = nrSteps; i > 1; i--) {
            if (target.updatePosition(i)) {
                target.setStatus("Moved forward by " + i + " steps.");
                System.out.println(target);
            } else {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
                System.out.println(target.getStatus());
                break;
            }
        }

        if (target.updatePosition(1)) {
            target.setStatus("Moved forward by 1 steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public SprintCommand(String argument) {
        super("sprint", argument);
    }
}
