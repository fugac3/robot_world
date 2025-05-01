package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class BackCommand extends Command {

    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public Response execute(Robot robot) {
        boolean moved = robot.updatePosition(-Integer.parseInt(this.argument));
        if (moved) {
            robot.setStatus("NORMAL");
            return new Response("OK", "Moved backwards " + this.argument + " steps.", robot);
        } else {
            return new Response("FAILED", "Cannot move backwards.", robot);
        }
    }






}

