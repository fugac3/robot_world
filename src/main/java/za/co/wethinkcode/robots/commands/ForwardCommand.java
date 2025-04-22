package za.co.wethinkcode.robots.commands;

//import java.awt.*;
import za.co.wethinkcode.robots.robot.Robot;

public class ForwardCommand extends Command {



    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(nrSteps)){
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    @Override
    public String toString() {
        // This will be used for logging the command
        return "Forward Command " + getArgument() + " steps";
    }

}

