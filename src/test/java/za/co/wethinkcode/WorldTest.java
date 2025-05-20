package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.world.Obstacle;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.List;

public class WorldTest {
    @Test
    void testWorldCreation() { //world should start off with at least one obstacle
        TextWorld world = new TextWorld();
        List<Obstacle> obstacles = world.getObstacles(); //get list of obstacles in world

        assertFalse(obstacles.isEmpty()); //should not be 0
    }

    @Test
    void testObstacleBlocksPosition() {
        //Blocked - obstacle that starts (5,5) and ends at (9,9)
        TextWorld obstacle = new TextWorld();
        Position inObstacle = new Position(6,8);
        assertFalse(obstacle.blocksPosition(inObstacle)); //(6,8) is blocked

        // Not Blocked
        Position outsideObstacle = new Position(0, 0);
        assertFalse(obstacle.blocksPosition(outsideObstacle));

        Position justOutsideObs = new Position(10, 10);
        assertFalse(obstacle.blocksPosition(justOutsideObs));
    }

    @Test
    void testObstacleBlocksPath() {
        TextWorld world = new TextWorld();
        Robot robo = new Robot("Robo", world,new Position(0,0));
        TextWorld obstacle = new TextWorld();
        Position pastObstacle = new Position(7, 11);
        //Blocked
        assertFalse(obstacle.blocksPath(robo.getPosition(),pastObstacle));

        //Obstacle doesn't block path
        Position notBlocked = new Position(7,4);
        assertFalse(obstacle.blocksPath(robo.getPosition(),notBlocked));
    }


    @Test
    void testPositionIsInBounds() {
        // Size of world
        Position topLeft = new Position(-200, 100);
        Position bottomRight = new Position(100, -200);

        // Is within bounds
        Position withinBounds = new Position(0, 0);
        assertTrue(withinBounds.isIn(topLeft, bottomRight));
        Position stillWithinBounds = new Position(-50, -80);
        assertTrue(stillWithinBounds.isIn(topLeft, bottomRight));

        // Testing boundaries
        Position atTopLeft = new Position(-200, 100);
        assertTrue(atTopLeft.isIn(topLeft, bottomRight));

        // Positions outside bounds
        Position outsideTop = new Position(0, 101);
        assertFalse(outsideTop.isIn(topLeft, bottomRight));

        Position outsideLeft = new Position(-201, 0);
        assertFalse(outsideLeft.isIn(topLeft, bottomRight));
    }
}
