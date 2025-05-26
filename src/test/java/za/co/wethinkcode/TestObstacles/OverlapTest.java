package za.co.wethinkcode.TestObstacles;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.obstacles.BottomlessPit;
import za.co.wethinkcode.robots.obstacles.LakesObstacle;
import za.co.wethinkcode.robots.obstacles.MountainObstacle;
import za.co.wethinkcode.robots.obstacles.Obstacle;
import za.co.wethinkcode.robots.world.*;

import static org.junit.jupiter.api.Assertions.*;

public class OverlapTest {
    AbstractWorld world = new AbstractWorld() {
        @Override
        public void showObstacles() {
            super.showObstacles();
        }
    };

    @Test
    public void testOverlapTrue() {
        Obstacle a = new LakesObstacle(2, 5);  // [2,5] to [8,9]
        Obstacle b = new BottomlessPit(2, 3);  // [2,3] to [8,7]
        assertTrue(world.overlaps(a, b),"Expected overlap to be true");
    }

    @Test
    public void testOverlapFalse() {
        Obstacle a = new LakesObstacle(-10, 0);  // [-10,0] to [-4,4]
        Obstacle b = new LakesObstacle(0, 5);       // [0,5] to [6,9]
        assertFalse(world.overlaps(a, b), "Expected overlap to be false");
    }

    @Test
    public void testJustTouchingEdges() {
        Obstacle a = new LakesObstacle(1, 1);       // [1,1] to [7,5]
        Obstacle b = new MountainObstacle(1, -3);    // [1,-3] to [7,1]
        assertFalse(world.overlaps(a, b), "Touching edges should not count as overlap");
    }
}
