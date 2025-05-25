package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;

import java.util.*;

/**
 * AbstractWorld provides the basic functionality and data structure for a world
 * where obstacles can be placed and visualized. It includes methods for managing obstacles
 * and for generating random obstacles.
 */
public abstract class AbstractWorld {

    /**
     * List to hold all obstacles present in the world.
     */
    protected List<Obstacle> obstacles = new ArrayList<>();

    /**
     * Get the list of obstacles currently in the world.
     *
     * @return a list of Obstacle objects.
     */

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Replace the current list of obstacles with a new list.
     *
     * @param newObstacle the new list of Obstacle objects.
     */
    public void setObstacles(List<Obstacle> newObstacle) {
        this.obstacles = newObstacle;
    }

    /**
     * Display all obstacles currently in the world.
     * If no obstacles are present, prints a message saying so.
     */
    public void showObstacles() {
        if (obstacles.isEmpty()) {
            System.out.println("No obstacles in the world.");
        } else {
            System.out.println("There are some obstacles:");
            for (Obstacle o : obstacles) {
                System.out.printf("- At position (%d, %d) to (%d, %d) \n",
                        o.getBottomLeftX(), o.getBottomLeftY(),
                        o.getTopRightX(), o.getTopRightY());
            }
        }
    }

    /**
     * Generate a random obstacle at a random location within the bounds defined by TextWorld.
     * Randomly selects an obstacle type from MountainObstacle, LakesObstacle, or BottomlessPit.
     */
    public void generateRandomObstacles(int numObstacles) {
        Random random = new Random();
        //Ensures that all classes in the list extend Obstacle.
        //List of classes allow the creation of new ones when the check fails
        List<Class<? extends Obstacle>> obstacleTypes = Arrays.asList(
                LakesObstacle.class,
                MountainObstacle.class,
                BottomlessPit.class
        );

        int maxX = TextWorld.BOTTOM_RIGHT.getX();
        int minY = TextWorld.BOTTOM_RIGHT.getY();
        int maxY = TextWorld.TOP_LEFT.getY();
        int minX = TextWorld.TOP_LEFT.getX();

        int attempts = 0;
        int tries = 50;

        while (this.obstacles.size() < numObstacles && attempts < tries) {
            attempts++;

            //Randomly choose an obstacle type (Mountain, Lake, or BottomlessPit)
            //Only a subclass of Obstacle can be in the list
            Class<? extends Obstacle> obstacleType = obstacleTypes.get(random.nextInt(obstacleTypes.size()));

            // Random position for obstacle
            //between ~300 - ~200 + 1 + the min again to stay in bounds
            // (100 - (-200) + 1 = 301), shift by -200
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
//            int x = 8;
//            int y = 0;

            // Create new obstacle to be checked an added
            Obstacle newObstacle = createObstacleOfType(obstacleType, x, y);

            // Check for overlap with different types and position
            boolean overlapsDifferent = false;
            for (Obstacle existing : this.obstacles) {
                if (!existing.getType().equals(newObstacle.getType()) && overlaps(existing, newObstacle)) {
                    overlapsDifferent = true;
                    break;
                }
            }

            //If no overlap with different types, then add
            if (!overlapsDifferent) {
//                System.out.println("ob added type: "+newObstacle.getClass()+" at "+newObstacle.ObstacleBottomLeft()+newObstacle.ObstacleTopRight());
                this.obstacles.add(newObstacle);
            }
//            else System.out.println("ob type was made in the way "+newObstacle.getClass()+" at: "+newObstacle.ObstacleBottomLeft()+newObstacle.ObstacleTopRight());
        }
    }

    //get the type based on the random selection
    //Support different logic for different types
    private Obstacle createObstacleOfType(Class<? extends Obstacle> type, int x, int y) {
        if (type.equals(MountainObstacle.class)) {
            return new MountainObstacle(x, y);
        } else if (type.equals(LakesObstacle.class)) {
            return new LakesObstacle(x, y);
        } else if (type.equals(BottomlessPit.class)) {
            return new BottomlessPit(x, y);
        }
        throw new IllegalArgumentException("Unknown type");
    }

//    checking if two rectangles overlap at all
    public boolean overlaps(Obstacle a, Obstacle b) {
        return a.getBottomLeftX() < b.getTopRightX() &&
                a.getTopRightX() > b.getBottomLeftX() &&
                a.getBottomLeftY() < b.getTopRightY() &&
                a.getTopRightY() > b.getBottomLeftY();
    }
}