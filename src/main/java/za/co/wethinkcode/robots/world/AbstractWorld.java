package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.commands.Direction;

import java.util.*;

public abstract class AbstractWorld implements IWorld {

    protected List<Obstacle> obstacles = new ArrayList<>();
    private Map<Direction, Artefact> visibleArea;



    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> newObstacle){
        this.obstacles = newObstacle;
    }

    @Override
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

    protected void generateRandomObstacles(Position topLeft, Position bottomRight){
        Random random = new Random();
        int numObstacles = 1;

        int maxX = bottomRight.getX();
        int minY = bottomRight.getY();
        int maxY = topLeft.getY();
        int minX = topLeft.getX();

        // List of obstacle types to choose from
        Class<?>[] obstacleTypes = {MountainObstacle.class, LakesObsticle.class, BottomlessPit.class};

        for (int i = 0; i < numObstacles; i++){
            // Random position for obstacle
            int x = random.nextInt(maxX - minX + 1) + minX;
            int y = random.nextInt(maxY - minY + 1) + minY;
//            int x =5;
//            int y = 5;

//            SquareObstacle obstacle = new SquareObstacle(x,y);
//            obstacles.add(obstacle);

//             Randomly choose an obstacle type (Mountain, Lake, or BottomlessPit)
            Class<?> obstacleType = obstacleTypes[random.nextInt(obstacleTypes.length)];

            try {
                if (obstacleType == MountainObstacle.class) {
                    obstacles.add(new MountainObstacle(x, y));
                } else if (obstacleType == LakesObsticle.class) {
                    obstacles.add(new LakesObsticle(x, y));
                } else if (obstacleType == BottomlessPit.class) {
                    obstacles.add(new BottomlessPit(x, y));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



        }




    }

//    public  void Obb (String newO)



}