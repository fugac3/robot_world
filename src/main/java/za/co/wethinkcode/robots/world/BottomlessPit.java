package za.co.wethinkcode.robots.world;

/**
 * Represents a Bottomless Pit obstacle in the world.
 * This type of obstacle extends {@link RectangleObstacle} and
 * is typically impassable by the robot.
 */
public class BottomlessPit extends RectangleObstacle {

    /**
     * Creates a BottomlessPit obstacle at the specified bottom-left coordinates.
     *
     * @param bottomLeftX the X-coordinate of the bottom-left corner.
     * @param bottomLeftY the Y-coordinate of the bottom-left corner.
     */
    public BottomlessPit(int bottomLeftX, int bottomLeftY) {
        super(bottomLeftX, bottomLeftY);
    }

    /**
     * Returns the type of the obstacle.
     *
     * @return a string representing the type: "bottomless pits".
     */
    public String getType() {
        return "bottomless pits";
    }
}
