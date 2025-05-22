package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.VisibleObjectType;

/**
 * Represents a lake obstacle in the robot world.
 * This type of obstacle extends {@link RectangleObstacle} and may affect robot movement or behavior.
 */
public class LakesObstacle extends Obstacle {

    /**
     * Creates a LakesObstacle at the specified bottom-left coordinates.
     *
     * @param bottomLeftX the X-coordinate of the bottom-left corner
     * @param bottomLeftY the Y-coordinate of the bottom-left corner
     */
    public LakesObstacle(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    /**
     * Returns the type of this obstacle.
     *
     * @return a string representing the obstacle type: "Lake"
     */
    public String getType(){
        return VisibleObjectType.LAKE.name();
    }
}
