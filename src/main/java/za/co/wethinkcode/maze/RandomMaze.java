package za.co.wethinkcode.maze;



import za.co.wethinkcode.world.Obstacle;
import za.co.wethinkcode.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaze extends AbstractMaze {
    private  List<Obstacle> obstacles = new ArrayList<>();

    public List<Obstacle> getObstaclePath() {
        List<Obstacle> obstacles = new ArrayList<>();
        for(ArrayList<Integer> cor : getRobotPath()){
            int Xcor = cor.get(0);
            int Ycor = cor.get(1);
            obstacles.add(new SquareObstacle(Xcor, Ycor));
        }
        return obstacles;
    }


    private ArrayList<ArrayList<Integer>> getRobotPath(){
        getObstacles();

        ArrayList<ArrayList<Integer>> listPathCoordinates = new ArrayList<>();
        for(int y = 0; y < 100 ; y++){
            for(int x = 0; x < 50; x++){
                ArrayList<Integer> listXYCoordinates = new ArrayList<>();
                int screenX = -100 + (x*4);
                int screenY = 200 - (y*4);
                for(int i = 0; i < this.obstacles.size(); i++) {
                    int x1 = this.obstacles.get(i).getBottomLeftX();
                    int y1 = this.obstacles.get(i).getBottomLeftX();
                    if (screenX != x1 && screenY != y1) {
                        listXYCoordinates.add(screenX);
                        listXYCoordinates.add(screenY);
                        listPathCoordinates.add(listXYCoordinates);
                    }
                }
            }
        }
        return listPathCoordinates;
    }


    @Override
    public List<Obstacle> getObstacles() {

        Random random = new Random();
        int numberOfObstacles = random.nextInt(25);

        for (int i = 0; i < numberOfObstacles; i++) {
            int Xcor = random.nextInt(100 + 200) - 200;
            int Ycor = random.nextInt(100 + 100) - 200;
            this.obstacles.add(new SquareObstacle(Xcor, Ycor));

        }
        return this.obstacles;
    }

}

