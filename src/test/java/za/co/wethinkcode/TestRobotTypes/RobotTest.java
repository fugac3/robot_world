package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.*;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    private TextWorld world;
    private Robot robot;

    @BeforeEach
    void setUp(){
        //Creating a controlled environment with no obstacles and defined position to make testing easier
        world = TextWorld.getInstance();
        world.getObstacles().clear(); //get rid of all obstacles in world
        TextWorld world = new TextWorld();
        RobotType type = new RobotType("bot",5,5,5);
        robot = new Robot("Robo", world, new Position(0,0),type);
        robot.setStatus("NORMAL");
    }

    @Test
    void testInitialPosition() {
        assertEquals(new Position(0, 0), robot.getPosition());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
        assertEquals("Robo", robot.getName());
        assertEquals("NORMAL", robot.getStatus());
        assertEquals(5, robot.getAmmo());
    }

    @Test
    void testBackCommand(){
        BackCommand back = new BackCommand("5");
        Response response = back.execute(robot);

        assertEquals("OK", response.getResult());
        Map<String, Object> data = response.getData();
        assertEquals("Done", data.get("message"));
        Map<String, Object> state = response.getState();
        assertNotNull(state);
        int[] position = (int[]) state.get("position");
        assertEquals(0,position[0]); //x
        assertEquals(-5, position[1]); //y coordinate
        assertEquals(Direction.NORTH, state.get("direction"));
        assertEquals("NORMAL", state.get("status"));
        assertEquals(new Position(0, -5), robot.getPosition());
    }

    @Test
    void testTurnRight(){
        assertEquals(Direction.NORTH, robot.getCurrentDirection()); //should face North initially

        robot.turnRight();
        assertEquals(Direction.EAST, robot.getCurrentDirection());

        robot.turnRight();
        assertEquals(Direction.SOUTH, robot.getCurrentDirection());

        robot.turnRight();
        assertEquals(Direction.WEST, robot.getCurrentDirection());

        robot.turnRight();
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
    }

    @Test
    void testTurnLeft(){
        assertEquals(Direction.NORTH, robot.getCurrentDirection());

        robot.turnLeft();
        assertEquals(Direction.WEST, robot.getCurrentDirection());

        robot.turnLeft();
        assertEquals(Direction.SOUTH, robot.getCurrentDirection());

        robot.turnLeft();
        assertEquals(Direction.EAST, robot.getCurrentDirection());

        robot.turnLeft();
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
    }

    @Test
    void testMovingAroundInDifferentDirections(){
        robot.updatePosition(5);
        assertEquals(new Position(0,5), robot.getPosition());

        robot.turnRight();

        robot.updatePosition(7);
        assertEquals(new Position(7,5), robot.getPosition());
    }

    @Test
    void testForwardCommandWithObstacleInPath() {
        //Add obstacle in the robot's path
        world.getObstacles().add(new MountainObstacle(0, 3));

        ForwardCommand forwardCommand = new ForwardCommand("5");
        Response response = forwardCommand.execute(robot);

        //Check response format
        assertEquals("FAILED", response.getResult());
        Map<String, Object> data = response.getData();
        assertEquals("Obstructed", data.get("message"));

        // Robot should not have moved
        assertEquals(new Position(0, 0), robot.getPosition());
    }

    @Test
    void testForwardCommandWithWorldEdge() {
        //Try to move forward past edge
        ForwardCommand forwardCommand = new ForwardCommand("1000");
        Response response = forwardCommand.execute(robot);

        assertEquals("FAILED", response.getResult());
        Map<String, Object> data = response.getData();
        assertEquals("Edge of world", data.get("message")); //"Edge of world" as nothing in protocol about it

        //Robot should not have moved
        assertEquals(new Position(0, 0), robot.getPosition());
    }

}