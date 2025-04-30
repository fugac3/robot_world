package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.Obstacle;

import java.lang.reflect.Array;
import java.util.List;

public class DumpCommand extends Command {
    public DumpCommand() {
        super("dump");
    }

    @Override
    public Response execute(Robot robot) {
        System.out.println("=== ROBOT STATE DUMP ===");
        System.out.println("Robot name: " + robot.getName());
        System.out.println("Position: " + robot.getPosition());
        System.out.println("Direction: " + robot.getCurrentDirection());
//        System.out.println("History: " + robot.getCommands());
//        System.out.println("Visible surroundings: " + robot.lookAround());

        IWorld world = robot.getWorld(); // Assuming you can access the world this way
        if (world != null) {
            System.out.println("Obstacles in world:");
            List<Obstacle> obstacles = world.getObstacles();
            for (Obstacle obs : obstacles) {
                System.out.println(" - " + obs);
            }
        }

        System.out.println("=========================");

        return null;
    }



}
