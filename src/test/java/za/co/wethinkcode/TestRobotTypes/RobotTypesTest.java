package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTypesTest {

    @Test
    void testBunkerRobot(){
        RobotType bunker = new BunkerRobot();

        assertEquals(5, bunker.getMaxShieldStrength());
        assertEquals(2, bunker.getMaxShots());
        assertEquals(1, bunker.getShootingRange());
    }

    @Test
    void testCannonRobot(){
        RobotType cannon = new CannonRobot();

        assertEquals(3, cannon.getMaxShieldStrength());
        assertEquals(2, cannon.getMaxShots());
        assertEquals(5, cannon.getShootingRange());
    }

    @Test
    void testStormcaller(){
        RobotType stormcaller = new StormcallerRobot();

        assertEquals(2, stormcaller.getMaxShieldStrength());
        assertEquals(4, stormcaller.getMaxShots());
        assertEquals(5, stormcaller.getShootingRange());
    }

    @Test
    void testWasp(){
        RobotType wasp = new WaspRobot();

        assertEquals(1, wasp.getMaxShieldStrength());
        assertEquals(4, wasp.getMaxShots());
        assertEquals(2, wasp.getShootingRange());
    }

    @Test
    void testWhiplash(){
        RobotType whiplash = new WhiplashRobot();

        assertEquals(2, whiplash.getMaxShieldStrength());
        assertEquals(5, whiplash.getMaxShots());
        assertEquals(3, whiplash.getShootingRange());
    }

}
