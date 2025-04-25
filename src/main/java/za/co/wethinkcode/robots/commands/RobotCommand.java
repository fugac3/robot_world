package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.TextWorld;

public class RobotCommand extends Command{

    public RobotCommand() {
        super("robot");
    }

    @Override
    public boolean execute(Robot robot) {
        // Cast world to TextWorld so we can call getAllRobots()
        if (robot.getWorld() instanceof TextWorld) {
            TextWorld world = (TextWorld) robot.getWorld();
            System.out.println("Robots in the world:");
            StringBuilder sb = new StringBuilder("Robots in the world:\n");

            for (Robot r : world.getAllRobots()) {
                sb.append("- ").append(r.getName())
                        .append(" at ")
                        .append(r.getPosition())
                        .append("\n");
            }

            robot.setStatus(sb.toString().trim());
            return true;
        } else {
            robot.setStatus("World does not support listing robots.");
            return false;
        }
    }
}
