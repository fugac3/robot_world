package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;

public class DefendCommand extends Command {
    public DefendCommand() {
        super("defend");
    }

    @Override
    public Response execute(Robot robot) {

        if (robot.getDefence() <= 0){
             ;
        }

        return null;
    }
}
