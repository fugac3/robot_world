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

            int topLeftX = Integer.parseInt(props.getProperty("topleft.x"));
            int topLeftY = Integer.parseInt(props.getProperty("topleft.y"));
            int bottomRightX = Integer.parseInt(props.getProperty("bottomright.x"));
            int bottomRightY = Integer.parseInt(props.getProperty("bottomright.y"));
            int maxObstacles = Integer.parseInt(props.getProperty("max.obstacles"));

            return new WorldConfig(topLeftX, topLeftY, bottomRightX, bottomRightY, maxObstacles);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }
    }
}
