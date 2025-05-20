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

    protected List<Obstacle> existingObstacles = new ArrayList<>();


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


//    protected boolean isOverlapWithDifferentType(int x, int y, Class<?> newType) {
////        Position position = new Position(x,y);
//        for (Obstacle o : existingObstacles) {
//            boolean overlap = x >= o.getBottomLeftX() && x <= o.getTopRightX()
//                    && y >= o.getBottomLeftY() && y <= o.getTopRightY();
//
//            if (overlap && !o.getClass().equals(newType)) {
////                System.out.println("ob type made in the way"+newType+" at "+position);
//                return true; // different type overlaps — not allowed
//            }
//        }
////        System.out.println("ob added type: "+newType+" at "+position);
//        return false; // safe to place
//    }


    /**
     * Generate a random obstacle at a random location within the bounds defined by TextWorld.
     * Randomly selects an obstacle type from MountainObstacle, LakesObstacle, or BottomlessPit.
     */
//    public void generateRandomObstacles(int numObstacles) {
//        Random random = new Random();
//
//        int maxX = TextWorld.BOTTOM_RIGHT.getX();
//        int minY = TextWorld.BOTTOM_RIGHT.getY();
//        int maxY = TextWorld.TOP_LEFT.getY();
//        int minX = TextWorld.TOP_LEFT.getX();
//        int x, y;
//
//        // List of obstacle types to choose from
//        Class<?>[] obstacleTypes = {MountainObstacle.class, LakesObstacle.class, BottomlessPit.class};
//
//        for (int i = 0; i < numObstacles; i++) {
//            //Randomly choose an obstacle type (Mountain, Lake, or BottomlessPit)
//            Class<?> obstacleType = obstacleTypes[random.nextInt(obstacleTypes.length)];
//            do {
//            // Random position for obstacle
//            //between ~300 - ~200 + 1 + the min again to stay in bounds
//            // (100 - (-200) + 1 = 301), shift by -200
//            x = random.nextInt(maxX - minX + 1) + minX;
//            y = random.nextInt(maxY - minY + 1) + minY;
//
//            } while (isOverlapWithDifferentType(x, y, obstacleType));
//
//
//            try {
//                Obstacle newObstacle = null;
//                if (obstacleType == MountainObstacle.class) {
//                    newObstacle = new MountainObstacle(x, y);
//                } else if (obstacleType == LakesObstacle.class) {
//                    newObstacle = new LakesObstacle(x, y);
//                }
//                else if (obstacleType == BottomlessPit.class) {
//                    newObstacle = new BottomlessPit(x, y);
//                }
//                obstacles.add(newObstacle);
//                existingObstacles.add(newObstacle);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
    public void generateRandomObstacles(int numObstacles) {
        Random random = new Random();
        List<Class<? extends Obstacle>> types = Arrays.asList(
                LakesObstacle.class,
                MountainObstacle.class,
                BottomlessPit.class
        );

        int maxX = TextWorld.BOTTOM_RIGHT.getX();
        int minY = TextWorld.BOTTOM_RIGHT.getY();
        int maxY = TextWorld.TOP_LEFT.getY();
        int minX = TextWorld.TOP_LEFT.getX();

        int attempts = 0;

        while (this.obstacles.size() < numObstacles && attempts < numObstacles * 10) {
            attempts++;

            // 1. Choose type
            Class<? extends Obstacle> type = types.get(random.nextInt(types.size()));

            // 2. Generate position
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;

            // 3. Create obstacle
            Obstacle newObstacle = createObstacleOfType(type, x, y);

            // 4. Check for overlap with DIFFERENT TYPES
            boolean overlapsDifferent = false;
            for (Obstacle existing : this.obstacles) {
                if (!existing.getClass().equals(newObstacle.getClass()) && overlaps(existing, newObstacle)) {
                    overlapsDifferent = true;
                    break;
                }
            }
            Position posBotLeft = new Position(newObstacle.getBottomLeftX(),newObstacle.getBottomLeftY());
            Position posTopRight = new Position(newObstacle.getTopRightX(),newObstacle.getTopRightY());

            // 5. If no overlap with different types, add
            if (!overlapsDifferent) {
                System.out.println("ob added type: "+newObstacle.getClass()+" at ["+posBotLeft+":"+posTopRight+"]");
                this.obstacles.add(newObstacle);
            }
            System.out.println("ob type made in the way"+newObstacle.getClass()+" at ["+posBotLeft+":"+posTopRight+"]");
        }
    }

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

    private boolean overlaps(Obstacle a, Obstacle b) {
        return a.getBottomLeftX() <= b.getTopRightX() &&
                a.getTopRightX() >= b.getBottomLeftX() &&
                a.getBottomLeftY() <= b.getTopRightY() &&
                a.getTopRightY() >= b.getBottomLeftY();
    }
}