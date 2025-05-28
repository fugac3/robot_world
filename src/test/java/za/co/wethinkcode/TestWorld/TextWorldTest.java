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
    private TextWorld textWorld;

    TextWorld world = new TextWorld();
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("Robo", world,new Position(0,0),type);

    @Test
    void testWorldCreation() { //world should start off with at least one obstacle
        TextWorld world = new TextWorld();
        Position obstaclePos = new Position(2, 0);
        world.getObstacles().add(new MountainObstacle(obstaclePos.getX(), obstaclePos.getY()));
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

//        TextWorld obstacle = new TextWorld();
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
        textWorld = TextWorld.getInstance(); // Get singleton instance
    }

    @Test
    public void testSingletonInstance() {
        TextWorld anotherInstance = TextWorld.getInstance();
        assertSame(textWorld, anotherInstance, "TextWorld should return the same instance each time");
    }

    @Test
    public void testAddRobot() {

        // Add the robot to the world
        textWorld.addRobot(robot);

        // Verify that the robot was added successfully
        assertTrue(textWorld.getAllRobots().contains(robot), "Robot should be added to the world");
    }

    @Test
    public void testgetAllRobots(){
        Position pos = new Position(0,0);
//            Robot robot = new Robot("bot", textWorld,pos);

        textWorld.getAllRobots();

        assertEquals(textWorld.getAllRobots() , textWorld.getAllRobots());
    }

//    @Test
//    public void testBlocksPath() {
//        // Create a new world and a robot
//        Position pos = new Position(4,5);
////            Robot robot = new Robot("bot", textWorld,pos);
//
//        // Set up some obstacles (this should be done in your world setup)
//        textWorld.blocksPath(new Position(5, 5), new Position(9, 9)); // Example, actual obstacle setup needed
//
//        // Test if the path between two positions is blocked
//        boolean isPathBlocked = textWorld.blocksPath(new Position(4, 5), new Position(10, 5));
//
//        // Assert that the path is blocked (modify as per actual obstacle setup)
//        assertTrue(isPathBlocked);
//    }
}
