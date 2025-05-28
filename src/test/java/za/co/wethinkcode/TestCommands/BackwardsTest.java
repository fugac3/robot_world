package za.co.wethinkcode.TestCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.BackCommand;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BackwardsTest {
    private Robot robot;

    Position TOP_LEFT = new Position(-5,5);
    Position BOTTOM_RIGHT = new Position(5,-5);
    TextWorld world = TextWorld.getInstance(TOP_LEFT,BOTTOM_RIGHT);

    @Test
    void testBackCommand() {
        //Creating a controlled environment with no obstacles and defined position to make testing easier
        world.getObstacles().clear(); //get rid of all obstacles in world
        TextWorld world = new TextWorld();
        RobotType type = new RobotType("bot",5,5,5);
        robot = new Robot("Robo", world, new Position(0,0),type);
        robot.setStatus("NORMAL");

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
}
