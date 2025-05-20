package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;

import java.util.*;

/**
 * AbstractWorld provides the basic functionality and data structure for a world
 * where obstacles can be placed and visualized. It includes methods for managing obstacles
 * and for generating random obstacles.
 */
public abstract class AbstractWorld  {

    /**
     * List to hold all obstacles present in the world.
     */
    protected List<Obstacle> obstacles = new ArrayList<>();

    /**
     * Map representing the area visible in a particular direction.
     */
    private Map<Direction, Artefact> visibleArea;

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
    protected void generateRandomObstacles(int numObstacles) {
        Random random = new Random();

        int maxX = TextWorld.BOTTOM_RIGHT.getX();
        int minY = TextWorld.BOTTOM_RIGHT.getY();
        int maxY = TextWorld.TOP_LEFT.getY();
        int minX = TextWorld.TOP_LEFT.getX();

        // List of obstacle types to choose from
        Class<?>[] obstacleTypes = {MountainObstacle.class, LakesObstacle.class, BottomlessPit.class};

        for (int i = 0; i < numObstacles; i++) {
            // Random position for obstacle
            //between ~300 - ~200 + 1 + the min again to stay in bounds
            // (100 - (-200) + 1 = 301), shift by -200
//            int x = random.nextInt(maxX - minX + 1) + minX;
//            int y = random.nextInt(maxY - minY + 1) + minY;
            int x = 5;
            int y = 5;

            //Randomly choose an obstacle type (Mountain, Lake, or BottomlessPit)
            Class<?> obstacleType = obstacleTypes[random.nextInt(obstacleTypes.length)];

            try {
                if (obstacleType == MountainObstacle.class) {
                    obstacles.add(new MountainObstacle(x, y));
                } else if (obstacleType == LakesObstacle.class) {
                    obstacles.add(new LakesObstacle(x, y));
                } else if (obstacleType == BottomlessPit.class) {
                    obstacles.add(new BottomlessPit(x, y));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}