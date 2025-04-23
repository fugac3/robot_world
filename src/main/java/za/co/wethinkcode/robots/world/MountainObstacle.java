package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.List;

public class MountainObstacle extends SquareObstacle {
    public MountainObstacle(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    public String getType(){
        return "Mountain";
    }
}
