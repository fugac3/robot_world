package za.co.wethinkcode.maze;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.world.Obstacle;

import java.util.ArrayList;
import java.util.List;


public class AbstractMaze implements Maze {
    public List<Obstacle> getObstaclePath(){
        return new ArrayList<>();
    }

    public boolean blocksPath(Position a, Position b){
        return  false;
    }

}
