package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.*;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.commands.LookCommand;


import java.util.List;
import java.util.Map;



import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    private LookCommand look;

    TextWorld world = TextWorld.getInstance();
    RobotType type = new RobotType("bot",5,5,5);
    Robot robot = new Robot("Reloader", world, new Position(0, 0),type);


    @Test
    public void testCreateForwardCommand() {
        Command command = Command.create("forward 10");
        assertNotNull(command);
        assertTrue(command instanceof ForwardCommand);
        assertEquals("10", command.getArgument());
    }

    @Test
    public void testInvalidCommandReturnsNull() {
        Command command = Command.create("dance 5");
        assertNull(command);
    }

    @Test
    public void testCreateThrowsOnEmptySteps() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Command.create("forward ");
        });
        assertEquals("Could not parse arguments: Steps cannot be null.", exception.getMessage());
    }


    @BeforeEach
    void setUp() {
        //Create controlled environment
        world = TextWorld.getInstance();
        world.getObstacles().clear();

        //Create a robot at the center
        world.addRobot(robot);
        look = new LookCommand();
    }



    @Test
    void testLookWithMountainObstacle() {
        //Add mountain obstacle to the north
        world.getObstacles().add(new MountainObstacle(0, 5));
        Response response = look.execute(robot);
        assertEquals("OK", response.getResult());

        //Get objects from response
        Map<String, Object> data = response.getData();
        List<Map<String, Object>> objects = (List<Map<String, Object>>) data.get("objects");

        //Assuming objects are always returned in order: NORTH, EAST, SOUTH, WEST
        Map<String, Object> northObject = objects.get(0);

        //Check north object is a mountain 5 distances away
        assertEquals("NORTH", northObject.get("direction"));
        assertEquals("MOUNTAIN", northObject.get("type"));
        assertEquals(5, northObject.get("distance"));
    }


}
