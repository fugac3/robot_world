package za.co.wethinkcode.Combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.combat.Bullet;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the {@link Bullet} class.
 * </p>
 * Verifies correct movement, stopping, shooter reference, and string representation.
 */
public class BulletTest {
    private Robot shooter;
    private Bullet bullet;

    /**
     * Sets up a shooter robot and a bullet before each test.
     */
    @BeforeEach
    void setUp() {
        RobotType type = new RobotType("bot", 5, 5, 5);
        shooter = new Robot("Shooter", TextWorld.getInstance(), new Position(0, 0),type);
        bullet = new Bullet(new Position(0, 0), Direction.NORTH, 2, shooter);
    }

    /**
     * Tests that the bullet moves correctly in all directions and distance decreases.
     */
    @Test
    void testMoveAllDirections() {
        for (Direction dir : Direction.values()) {
            Bullet b = new Bullet(new Position(0, 0), dir, 1, shooter);
            assertTrue(b.move());
            assertEquals(0, b.getDistanceLeft());
        }
    }

    /**
     * Tests that a bullet with zero distance does not move.
     */
    @Test
    void testMoveWhenStopped() {
        Bullet b = new Bullet(new Position(0, 0), Direction.NORTH, 0, shooter);
        assertFalse(b.move());
    }

    /**
     * Tests the hasStopped method for both stopped and moving bullets.
     */
    @Test
    void testHasStopped() {
        Bullet b = new Bullet(new Position(0, 0), Direction.NORTH, 0, shooter);
        assertTrue(b.hasStopped());
        Bullet b2 = new Bullet(new Position(0, 0), Direction.NORTH, 1, shooter);
        assertFalse(b2.hasStopped());
    }

    /**
     * Tests the string representation of the bullet.
     */
    @Test
    void testToString() {
        String str = bullet.toString();
        assertTrue(str.contains("Bullet{pos="));
        assertTrue(str.contains("dir="));
        assertTrue(str.contains("distLeft="));
    }
}
