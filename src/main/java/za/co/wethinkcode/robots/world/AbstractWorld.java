package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractWorld implements IWorld {

    protected List<Obstacle> obstacles = new ArrayList<>();

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public void showObstacles() {
        if (obstacles.isEmpty()) {
            System.out.println("No obstacles in the world.");
        } else {
            System.out.println("There are some obstacles:");
            for (Obstacle o : obstacles) {
                System.out.printf("- At position (%d, %d) to (%d, %d) [Type: %s]\n",
                        o.getBottomLeftX(), o.getBottomLeftY(),
                        o.getTopRightX(), o.getTopRightY(), ((SquareObstacle) o).getType());
            }
        }
    }

    protected void generateRandomObstacles(Position topLeft, Position bottomRight) {
        Random random = new Random();
        int numObstacles = 1; // Number of obstacles to place

        // Get world bounds
        int maxX = bottomRight.getX();
        int minX = topLeft.getX();
        int minY = bottomRight.getY();
        int maxY = topLeft.getY();

        // List of obstacle types to choose from
        Class<?>[] obstacleTypes = {MountainObstacle.class, LakesObsticle.class, BottomlessPit.class};

        for (int i = 0; i < numObstacles; i++) {
            // Random position for obstacle
//            int x = random.nextInt(maxX - minX + 1) + minX;
//            int y = random.nextInt(maxY - minY + 1) + minY;
            int x =5;
            int y = 5;
            // Randomly choose an obstacle type (Mountain, Lake, or BottomlessPit)
            Class<?> obstacleType = obstacleTypes[random.nextInt(obstacleTypes.length)];

            // Create the obstacle based on the randomly selected type
            try {
                if (obstacleType == MountainObstacle.class) {
                    obstacles.add(new MountainObstacle(x, y));
                }
//                } else if (obstacleType == LakesObsticle.class) {
//                    obstacles.add(new LakesObsticle(x, y));
//                } else if (obstacleType == BottomlessPit.class) {
//                    obstacles.add(new BottomlessPit(x, y));
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
