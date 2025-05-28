package za.co.wethinkcode.TestRobotTypes;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robotTypes.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTypesTest {

    @Test
    void testBunkerRobot(){
        RobotType bunker = new TankRobot();

        assertEquals(5, bunker.getMaxShieldStrength());
        assertEquals(5, bunker.getMaxShots());
        assertEquals(1, bunker.getShootingRange());
    }

    @Test
    void testHeavyRobot(){
        RobotType heavy = new HeavyRobot();

        assertEquals(4, heavy.getMaxShieldStrength());
        assertEquals(4, heavy.getMaxShots());
        assertEquals(2, heavy.getShootingRange());
    }

    @Test
    void testBasicRobot(){
        RobotType basic = new BasicRobot();

        assertEquals(3, basic.getMaxShieldStrength());
        assertEquals(3, basic.getMaxShots());
        assertEquals(3, basic.getShootingRange());
    }

    @Test
    void testScoutRobot(){
        RobotType scout = new ScoutRobot();

        assertEquals(1, scout.getMaxShieldStrength());
        assertEquals(4, scout.getMaxShots());
        assertEquals(2, scout.getShootingRange());
    }

    @Test
    void testSniperRobot(){
        RobotType sniper = new SniperRobot();

        assertEquals(2, sniper.getMaxShieldStrength());
        assertEquals(5, sniper.getMaxShots());
        assertEquals(3, sniper.getShootingRange());
    }

}
