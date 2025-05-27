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


    @Test
    void testInitialPosition() {
        //Creating a controlled environment with no obstacles and defined position to make testing easier
        world = TextWorld.getInstance();
        world.getObstacles().clear(); //get rid of all obstacles in world
        TextWorld world = new TextWorld();
        RobotType type = new RobotType("bot",5,5,5);
        robot = new Robot("Robo", world, new Position(0,0),type);
        robot.setStatus("NORMAL");

        assertEquals(new Position(0, 0), robot.getPosition());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
        assertEquals("Robo", robot.getName());
        assertEquals("NORMAL", robot.getStatus());
        assertEquals(5, robot.getAmmo());
    }

}