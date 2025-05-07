package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.List;
import java.util.Map;

/**
 * Your Text and Turtle worlds must implement this interface.
 */
public interface IWorld {
    boolean blocksPath(Position a, Position b);

    void setPosition(Position newPosition);

    Map<za.co.wethinkcode.robots.commands.Direction, Artefact> look();

    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles();

    /**
     * Gives opportunity to world to draw or list obstacles.
     */
    void showObstacles();


}
