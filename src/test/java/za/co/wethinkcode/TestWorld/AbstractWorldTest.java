package za.co.wethinkcode.TestWorld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.world.AbstractWorld;
import za.co.wethinkcode.robots.obstacles.Obstacle;
import java.util.List;
import za.co.wethinkcode.robots.obstacles.*;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.Arrays;
public class AbstractWorldTest {

    private AbstractWorld world;

    @BeforeEach
    void setUp() {
        world = new TextWorld();
    }

    @Test
    void testSetAndGetObstacles() {
        Obstacle mountain = new MountainObstacle(1, 1);
        Obstacle lake = new LakesObstacle(5, 5);
        List<Obstacle> list = Arrays.asList(mountain, lake);

        world.setObstacles(list);

        assertEquals(2, world.getObstacles().size());
        assertEquals(mountain, world.getObstacles().get(0));
    }

    @Test
    void testGenerateRandomObstacles() {
        world.generateRandomObstacles(5);
        assertTrue(world.getObstacles().size() <= 5);
        assertTrue(world.getObstacles().stream().allMatch(o ->
                o instanceof MountainObstacle || o instanceof LakesObstacle || o instanceof BottomlessPit));
    }

    @Test
    void testOverlapTrue() {
        Obstacle ob1 = new MountainObstacle(0, 0); // 0,0 to 4,4
        Obstacle ob2 = new LakesObstacle(3, 3);    // 3,3 to 6,6

        assertTrue(world.overlaps(ob1, ob2));
    }

    @Test
    void testOverlapFalse() {
        Obstacle ob1 = new MountainObstacle(0, 0); // 0,0 to 4,4
        Obstacle ob2 = new LakesObstacle(5, 5);    // 5,5 to 8,8

        assertFalse(world.overlaps(ob1, ob2));
    }

    @Test
    void testNoOverlapWithSameType() {
        // Overlapping obstacles of the same type should be allowed
        Obstacle ob1 = new MountainObstacle(0, 0);
        Obstacle ob2 = new MountainObstacle(2, 2);

        world.setObstacles(List.of(ob1));
        assertTrue(world.overlaps(ob1, ob2)); // This test might pass/fail based on usage logic
    }
}

