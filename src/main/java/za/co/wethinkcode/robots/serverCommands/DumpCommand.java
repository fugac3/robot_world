package za.co.wethinkcode.robots.serverCommands;

import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.obstacles.Obstacle;

import java.util.List;

/**
 * The DumpCommand is responsible for collecting and returning a snapshot of the current
 * state of obstacles and robots in the world. It gathers details about all obstacles and
 * robots present in the world and returns this information as part of a response.
 */

public class DumpCommand {

    /**
     * Executes the dump command by gathering details about the current obstacles and robots
     * in the world and returning them in a structured print response.
     *
     * @param world the {@link TextWorld} executing the command
     * prints all the data about obstacles and robots, or an error response
     */
    public static void dumpWorldState(TextWorld world) {
        // Collect obstacle data
        List<Obstacle> obstacles = world.getObstacles();
        if (obstacles.isEmpty()) {
            System.out.println("No obstacles present in the world.");
        } else {
            System.out.println("== Obstacles ==");
            System.out.printf("%-20s %-12s %-12s\n",
                                "Type", "bottom left", "top right");
            for (Obstacle o : obstacles) {
                System.out.printf("%-20s (%-1d,%-1d)        (%-1d,%-1d)\n",
                        o.getClass().getSimpleName(), // Get the type of obstacle (e.g., MountainObstacle)
                        o.getBottomLeftX(), o.getBottomLeftY(),
                        o.getTopRightX(), o.getTopRightY());
            }
        }
        System.out.println();
    }
}
