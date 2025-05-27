package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.commands.ForwardCommand;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForwardTest {

    private TextWorld world;
    private Robot robot;


    @Test
    void testForwardCommand() {
        //Creating a controlled environment with no obstacles and defined position to make testing easier
        world = TextWorld.getInstance();
        world.getObstacles().clear(); //get rid of all obstacles in world
        TextWorld world = new TextWorld();
        RobotType type = new RobotType("bot",5,5,5);
        robot = new Robot("Robo", world, new Position(0,0),type);
        robot.setStatus("NORMAL");

        //Create a forward command moving 5 steps forward
        ForwardCommand forwardCommand = new ForwardCommand("5");
        //Execute command
        Response response = forwardCommand.execute(robot);

        //Checking response format
        assertEquals("OK", response.getResult());
        Map<String, Object> data = response.getData(); //get data to see what message returns inside
        assertEquals("Done", data.get("message"));

        //Check robot state in response
        Map<String, Object> state = response.getState();
        assertNotNull(state); //state should not be empty
        int[] position = (int[]) state.get("position"); //position is a list in [x,y] format
        assertEquals(0, position[0]); //x coordinate
        assertEquals(5, position[1]); //y coordinate
        assertEquals(Direction.NORTH, state.get("direction"));
        assertEquals("NORMAL", state.get("status"));

        //Check actual robot state
        assertEquals(new Position(0, 5), robot.getPosition());
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
