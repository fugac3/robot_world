package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.BunkerRobot;
import za.co.wethinkcode.robots.robotTypes.CannonRobot;
import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.robotTypes.StormcallerRobot;

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

}
