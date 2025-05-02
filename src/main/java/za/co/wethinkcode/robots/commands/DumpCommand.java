package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.Obstacle;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumpCommand extends Command {
    public DumpCommand() {
        super("dump");
    }

    @Override
    public Response execute(Robot robot) {
        Map<String,String>dumpInfo = new HashMap<>();

        dumpInfo.put("Robot name: ", robot.getName());
        dumpInfo.put("Position ", robot.getPosition().toString());
        dumpInfo.put("Direction: ", robot.getCurrentDirection().toString());
//        System.out.println("=== ROBOT STATE DUMP ===");
//        System.out.println("Robot name: " + robot.getName());
//        System.out.println("Position: " + robot.getPosition());
//        System.out.println("Direction: " + robot.getCurrentDirection());


        IWorld world = robot.getWorld(); // Assuming you can access the world this way
        if (world != null) {
            System.out.println("Obstacles in world:");
            List<Obstacle> obstacles = world.getObstacles();
            for (Obstacle obs : obstacles) {
                System.out.println(" - " + obs);

            }
        }
        dumpInfo.put("Obstacle",robot.getWorld().getObstacles().toString());

        System.out.println("=========================");

        return new Response("OK","Robot has been dumped", Map.of(robot.getName(), dumpInfo));
    }
}
