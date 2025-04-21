package za.co.wethinkcode.world;

import za.co.wethinkcode.robot.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractWorld implements IWorld{


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
                System.out.printf("-At position %d,%d to (%d,%d)\n", o.getBottomLeftX(), o.getBottomLeftY(), o.getTopRightX(), o.getTopRightY());
            }
        }
    }

    protected void generateRandomObstacles(Position topLeft,Position  bottomRight) {
        Random random = new Random();
//        int numObstacles = random.nextInt(3) + 1; // 1 to 3 obstacles
        int numObstacles = 3;

        // Get world bounds
        int maxX = bottomRight.getX();
        int minX = topLeft.getX();
        int minY = bottomRight.getY();
        int maxY = topLeft.getY();


        for (int i = 0; i < numObstacles; i++) {
//            random.nextInt(301) - 200; range -200 to 100
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;


            SquareObstacle obstacle = new SquareObstacle(x, y);
            obstacles.add(obstacle);

        }
    }

//===========
}
