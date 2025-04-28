package za.co.wethinkcode.robots.commands;

//import java.awt.*;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class ForwardCommand extends Command {



    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public Response execute(Robot robot) {
        boolean moved = robot.updatePosition(Integer.parseInt(this.argument));
        if (moved) {
            return new Response("OK", "Moved forward " + this.argument + " steps.", robot.getState());
        } else {
            return new Response("FAILED", "Cannot move forward.", robot.getState());
        }
    }



    @Override
    public String toString() {
        // This will be used for logging the command
        return "Forward Command " + getArgument() + " steps";
    }

}

