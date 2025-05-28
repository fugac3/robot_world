package za.co.wethinkcode.robots.world;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    public static WorldConfig loadConfig() {
        //load all key=value pairs from the file into memory.
        //A subclass of Hashtable designed for string key-value pairs
        Properties props = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Could not find config.properties");
            }
            props.load(input);

            int length = Integer.parseInt(props.getProperty("world.length"));
            int height = Integer.parseInt(props.getProperty("world.height"));
            int maxObstacles = Integer.parseInt(props.getProperty("max.obstacles"));
            int visibilityConstraint = Integer.parseInt(props.getProperty("visibility.constraint", "5"));

            return new WorldConfig(length, height, maxObstacles, visibilityConstraint);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }

    }
}
