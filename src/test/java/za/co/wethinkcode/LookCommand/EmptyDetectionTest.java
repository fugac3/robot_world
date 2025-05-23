package za.co.wethinkcode.LookCommand;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.commands.LookCommand;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmptyDetectionTest {

    @Test
    public void testEmptyDetection() {
        LookCommand lookCmd = new LookCommand();
        List<Map<String, Object>> objects = new ArrayList<>();

        // No obstacle placed
        Direction direction = Direction.EAST;
        // Call the method
        lookCmd.emptyDirection(direction,20, objects);

        // Assertions
        assertEquals(1, objects.size(), "Should contain one EMPTY object");
        Map<String, Object> detected = objects.getFirst();
        assertEquals("EMPTY", detected.get("type"));
        assertEquals("EAST", detected.get("direction"));
        assertEquals( 20, detected.get("distance"));
    }

}
