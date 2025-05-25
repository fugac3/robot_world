package za.co.wethinkcode.TestObstacles;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.world.AbstractWorld;
import za.co.wethinkcode.robots.Obstacles.LakesObstacle;
import za.co.wethinkcode.robots.Obstacles.MountainObstacle;
import za.co.wethinkcode.robots.Obstacles.Obstacle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeOverlapTest {
    AbstractWorld world = new AbstractWorld() {
        @Override
        public void showObstacles() {
            super.showObstacles();
        }
    };

    @Test
    public void testOverlapBetweenDifferentTypes() {
        // Set up a world with one old obstacle
        Obstacle existing = new LakesObstacle(-5, 5);     // [-5,5] to [0,9]
        Obstacle newObstacle = new MountainObstacle(-4, 6); // [-4,6] to [1,10] -> overlaps!

        // Simulate the overlapDifferent logic
        boolean overlapsDifferent = !existing.getType().equals(newObstacle.getType()) && world.overlaps(existing, newObstacle);

        assertTrue(overlapsDifferent, "Expected overlapDifferent to be true for different types");
    }

    @Test
    public void testOverlapBetweenSameTypes() {
        Obstacle existing = new LakesObstacle(1, 3);     // [1,3] to [7,7]
        Obstacle newObstacle = new LakesObstacle(1, 1); // [1,1] to [7,5] -> overlaps!

        boolean overlapsDifferent = !existing.getType().equals(newObstacle.getType()) && world.overlaps(existing, newObstacle);

        assertFalse(overlapsDifferent, "Expected overlapDifferent to be false for same types");
    }

    @Test
    public void testNoOverlapBetweenDifferentTypes() {
        Obstacle existing = new LakesObstacle(0, 0);     // [0,0] to [6,4]
        Obstacle newObstacle = new MountainObstacle(-10, 0); // [-10,0] to [-4,4] -> no overlaps!

        boolean overlapsDifferent = !existing.getType().equals(newObstacle.getType()) && world.overlaps(existing, newObstacle);

        assertFalse(overlapsDifferent, "Expected overlapDifferent to be false for same types");
    }
}
