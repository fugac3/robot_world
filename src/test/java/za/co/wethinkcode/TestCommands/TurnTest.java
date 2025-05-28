package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnTest {
    private TextWorld world;
    private Robot robot;

    @BeforeEach
    void setUp(){
        //Creating a controlled environment with no obstacles and defined position to make testing easier
        world = TextWorld.getInstance();
        world.getObstacles().clear(); //get rid of all obstacles in world
        RobotType type = new RobotType("bot",5,5,5);
        robot = new Robot("Robo", world, new Position(0,0),type);
        robot.setStatus("NORMAL");
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
        robot.updatePosition(4);
        assertEquals(new Position(0,4), robot.getPosition());

        robot.turnRight();

        robot.updatePosition(4);
        assertEquals(new Position(4,4), robot.getPosition());
    }
}
