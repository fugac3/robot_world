package za.co.wethinkcode.TestObstacles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.robots.world.BottomlessPit;
import za.co.wethinkcode.robots.world.MountainObstacle;
import za.co.wethinkcode.robots.world.Obstacle;
import za.co.wethinkcode.robots.world.LakesObstacle;

public class ObstacleCreationTest {

    @Test
    public void testSingleLakesObstacleCreated() {
        // Create obstacle
        Obstacle lake = new LakesObstacle(3, 7);

        // Check bottom-left coordinates
        assertEquals(3, lake.getBottomLeftX(), "Bottom-left X should be 3");
        assertEquals(7, lake.getBottomLeftY(), "Bottom-left Y should be 7");

        // Check top-right coordinates (width = 7, height = 5)
        assertEquals(3 + 7 - 1, lake.getTopRightX(), "Top-right X should be 9");
        assertEquals(7 + 5 - 1, lake.getTopRightY(), "Top-right Y should be 11");

        // Check type
        assertEquals(LakesObstacle.class, lake.getClass(), "Should be a LakesObstacle");
    }

    @Test
    public void testSingleMountainObstacleCreated() {
        // Create obstacle
        Obstacle mountain = new MountainObstacle(13, 17);

        // Check bottom-left coordinates
        assertEquals(13, mountain.getBottomLeftX());
        assertEquals(17, mountain.getBottomLeftY());

        // Check top-right coordinates (width = 7, height = 5)
        assertEquals(13 + 7 - 1, mountain.getTopRightX());
        assertEquals(17 + 5 - 1, mountain.getTopRightY());

        // Check type
        assertEquals(MountainObstacle.class, mountain.getClass(), "Should be a MountainObstacle");
    }
    
    @Test
    public void testSingleBottomlessPitCreated() {
        // Create obstacle
        Obstacle pit = new BottomlessPit(13, 17);

        // Check bottom-left coordinates
        assertEquals(13, pit.getBottomLeftX());
        assertEquals(17, pit.getBottomLeftY());

        // Check top-right coordinates (width = 7, height = 5)
        assertEquals(13 + 7 - 1, pit.getTopRightX());
        assertEquals(17 + 5 - 1, pit.getTopRightY());

        // Check type
        assertEquals(BottomlessPit.class, pit.getClass(), "Should be a BottomlessPit");
    }
}
