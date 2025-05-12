package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.List;

/**
 * Represents a mountain obstacle in the robot world.
 * This type of obstacle extends {@link RectangleObstacle} and typically blocks robot movement.
 */
public class MountainObstacle extends RectangleObstacle {

    /**
     * Creates a MountainObstacle at the specified bottom-left coordinates.
     *
     * @param bottomLeftX the X-coordinate of the bottom-left corner
     * @param bottomLeftY the Y-coordinate of the bottom-left corner
     */
    public MountainObstacle(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    /**
     * Returns the type of this obstacle.
     *
     * @return a string representing the obstacle type: "Mountain"
     */
    public String getType(){
        return "Mountain";
    }
}
