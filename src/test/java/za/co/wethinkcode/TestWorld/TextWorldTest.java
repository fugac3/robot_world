package za.co.wethinkcode.TestWorld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.obstacles.Obstacle;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextWorldTest {
    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("Robo", world,new Position(0,0),type);

    @Test
    void testWorldCreation() { //world should start off with at least one obstacle
        Position obstaclePos = new Position(1, 0);
        world.getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));
        List<Obstacle> obstacles = world.getObstacles(); //get list of obstacles in world

        assertFalse(obstacles.isEmpty()); //should not be 0
    }

    @Test
    void testObstacleBlocksPath() {
        Position pastObstacle = new Position(3, 3);
        //Blocked
        assertFalse(world.blocksPath(robot.getPosition(),pastObstacle));

        //Obstacle doesn't block path
        Position notBlocked = new Position(7,4);
        assertFalse(world.blocksPath(robot.getPosition(),notBlocked));
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

    @BeforeEach
    public void setUp() {
        world = TextWorld.getInstance(); // Get singleton instance
    }

    @Test
    public void testSingletonInstance() {
        TextWorld anotherInstance = TextWorld.getInstance();
        assertSame(world, anotherInstance, "TextWorld should return the same instance each time");
    }

    @Test
    public void testAddRobot() {

        // Add the robot to the world
        world.addRobot(robot);

        // Verify that the robot was added successfully
        assertTrue(world.getAllRobots().contains(robot), "Robot should be added to the world");
    }

    @Test
    public void testGetAllRobots(){
        Position pos = new Position(0,0);
//            Robot robot = new Robot("bot", textWorld,pos);

        world.getAllRobots();

        assertEquals(world.getAllRobots() , world.getAllRobots());
    }
}
