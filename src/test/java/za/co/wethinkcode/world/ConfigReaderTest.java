package za.co.wethinkcode.world;

//package za.co.wethinkcode.robots.world;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.world.ConfigReader;
import za.co.wethinkcode.robots.world.TextWorld;
import za.co.wethinkcode.robots.world.WorldConfig;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest {
    @Test
    void testLoadConfigSuccessfully() {
        WorldConfig config = ConfigReader.loadConfig();
        assertNotNull(config);
        assertEquals(1, config.maxObstacles);
    }

    @Test
    void testVisibilityConstraintInWorld() {
        // Test with a custom visibility constraint
        WorldConfig config = new WorldConfig(10, 10,1, 10, 5);
        assertEquals(10, config.visibilityConstraint);
    }

}

