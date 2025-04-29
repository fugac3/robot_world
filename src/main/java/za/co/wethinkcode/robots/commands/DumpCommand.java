package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.Obstacle;


import java.util.List;

public class DumpCommand extends Command {
    public DumpCommand() {
        super("dump");
    }

    @Override
    public boolean execute(Robot target) {
        System.out.println("=== ROBOT STATE DUMP ===");
        System.out.println("Robot name: " + target.getName());
        System.out.println("Position: " + target.getPosition());
        System.out.println("Direction: " + target.getCurrentDirection());
        System.out.println("History: " + target.getCommands());
        System.out.println("Visible surroundings: " + target.lookAround());

        IWorld world = target.getWorld(); // Assuming you can access the world this way
        if (world != null) {
            System.out.println("Obstacles in world:");
            List<Obstacle> obstacles = world.getObstacles();
            for (Obstacle obs : obstacles) {
                System.out.println(" - " + obs);
            }
        }

        System.out.println("=========================");
        return true;
    }
}
