package za.co.wethinkcode.maze;

import za.co.wethinkcode.world.Obstacle;
import za.co.wethinkcode.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;


public class EmptyMaze extends AbstractMaze {


    @Override
    public List<Obstacle> getObstacles(){
        List<Obstacle> obstacles = new ArrayList<>();
        return obstacles;
    }

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

                listXYCoordinates.add(screenX);
                listXYCoordinates.add(screenY);
                listPathCoordinates.add(listXYCoordinates);



            }
        }
        return listPathCoordinates;
    }

}
