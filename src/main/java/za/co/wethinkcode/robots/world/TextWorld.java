package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.ArrayList;
import java.util.List;
//import za.co.wethinkcode.robot.position;


public class TextWorld extends AbstractWorld{

    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    public static final Position CENTRE = new Position(0,0);
    private List<Robot> robots = new ArrayList<>();


    private Position position;

    public TextWorld() {
        this.position = CENTRE;
        generateRandomObstacles(TOP_LEFT,BOTTOM_RIGHT);
    }


    @Override
    public boolean blocksPath(Position start, Position end , Robot bot) {
        for (Obstacle o : obstacles) {
            if (o.blocksPath(start, end ,bot, robots)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Updates the position of your robot in the world by moving the nrSteps in the robots current direction.

//     * @param nrSteps steps to move in current direction
//     * @return true if this does not take the robot over the world's limits, or into an obstacle.
     */
    @Override
    public boolean updatePosition(int nrSteps) {
        return false;
    }



    /**
     * Updates the current direction your robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     *
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    @Override
    public void updateDirection(boolean turnRight) {

    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    /**
     * Gets the current direction the robot is facing in relation to a world edge.
     *
     * @return Direction.UP, RIGHT, DOWN, or LEFT
     */
    @Override
    public Direction getCurrentDirection() {
        return null;
    }

    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     *
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    @Override
    public boolean isNewPositionAllowed(Position position) {
        return false;
    }

    /**
     * Checks if the robot is at one of the edges of the world
     *
     * @return true if the robot's current is on one of the 4 edges of the world
     */
    @Override
    public boolean isAtEdge() {
        return false;
    }

    /**
     * Reset the world by:
     * - moving current robot position to center 0,0 coordinate
     * - removing all obstacles
     * - setting current direction to UP
     */
    @Override
    public void reset() {

    }




//==========
}
