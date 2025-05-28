package za.co.wethinkcode.robots.world;

import org.w3c.dom.Text;
import za.co.wethinkcode.robots.obstacles.Obstacle;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.*;

/**
 * The TextWorld class defines a grid-based world for robots to operate in.
 * It extends {@link AbstractWorld} and uses a singleton pattern to ensure
 * only one world instance exists at a time.
 * The world is bounded by a top-left and bottom-right position and supports
 * obstacle generation, position validation, and robot tracking.
 */
public class TextWorld extends AbstractWorld {

    /** Top-left corner of the world boundary. */
    public static Position TOP_LEFT;

    /** Bottom-right corner of the world boundary. */
    public static Position BOTTOM_RIGHT;

    /** Singleton instance of the TextWorld. */
    private static TextWorld instance;

    /** Map of robot names to robot instances. */
    private final Map<String, Robot> robots = new HashMap<>();

    private final WorldConfig config;

    /**
     * Constructs a new TextWorld with the center position and generates random obstacles.
     */
    public TextWorld() {
        this.config = ConfigReader.loadConfig();
        TOP_LEFT = config.topLeft;
        BOTTOM_RIGHT = config.bottomRight;
        generateRandomObstacles(config.maxObstacles);
    }

    //just for custom sized worlds in tests
    public TextWorld(Position TOP_LEFT,Position BOTTOM_RIGHT) {
        this.config = ConfigReader.loadConfig();
        TextWorld.TOP_LEFT = TOP_LEFT;
        TextWorld.BOTTOM_RIGHT = BOTTOM_RIGHT;
        generateRandomObstacles(config.maxObstacles);
    }

    public Robot getRobotByName(String name) {
        return robots.get(name);
    }


    public static Position getTopLeft() {
        return TOP_LEFT;
    }

    public static Position getBottomRight() {
        return BOTTOM_RIGHT;
    }

    /**
     * Returns the singleton instance of the TextWorld.
     * If it doesn't exist yet, it will be created.
     *
     * @return the shared instance of TextWorld
     */
    public static synchronized TextWorld getInstance() {
        if (instance == null) {
            instance = new TextWorld();
        }
        return instance;
    }

    public static synchronized TextWorld getInstance(Position TOP_LEFT,Position BOTTOM_RIGHT) {
        if (instance == null) {
            instance = new TextWorld(TOP_LEFT,BOTTOM_RIGHT);
        }
        return instance;
    }


    /**
     * Clears all robots and obstacles currently in the world.
     * With the option to generate new random obstacles.
     */
    public void reset(boolean withObstacles) {
        this.robots.clear();
        this.obstacles.clear();
        if (withObstacles) {
            generateRandomObstacles(config.maxObstacles);
        }
    }

    /**
     * Returns a random, unoccupied position within the world's boundaries.
     *
     * @return a free {@link Position} where no obstacles or robots are present
     */
    public Position getRandomFreePosition() {
        Random rand = new Random();
        int minX = TOP_LEFT.getX();
        int maxX = BOTTOM_RIGHT.getX();
        int minY = BOTTOM_RIGHT.getY();
        int maxY = TOP_LEFT.getY();

        Position pos;

        do {
            int x = rand.nextInt(maxX - minX + 1) + minX;
            int y = rand.nextInt(maxY - minY + 1) + minY;
            pos = new Position(x, y);
        } while (blocksPosition(pos));  // Retry if blocked
        // cant tell if the world is full
        // will loop infinitely
        return pos;
    }

    /**
     * Determines whether the given position is blocked by an obstacle or another robot.
     * @param pos the position to check
     * @return {@code true} if the position is occupied, otherwise {@code false}
     */
    public boolean blocksPosition(Position pos) {
        // Check for obstacles
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.blocksPosition(pos)) {
                return true;
            }
        }

        // Check for other robots
        for (Robot robot : getAllRobots()) {
            if (robot.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a robot to the world.
     *
     * @param robot the {@link Robot} to add
     */
    public void addRobot(Robot robot) {
        robots.put(robot.getName(), robot);
    }

    /**
     * Returns all robots currently in the world.
     *
     * @return a collection of all {@link Robot} instances
     */
    public Collection<Robot> getAllRobots() {
        return robots.values();
    }

    /**
     * Clears all robots currently in the world.
     */
    public void clearRobots() {
        robots.clear(); // assuming 'robots' is the map of name → Robot
    }

    /**
     * Removes a specific robot from the world.
     */
    public void removeRobot(Robot robot) {
        robots.remove(robot.getName());
    }

    /**
     * Determines whether any obstacle blocks the straight-line path between two positions.
     *
     * @param start the starting position
     * @param end the ending position
     * @return {@code true} if the path is blocked, otherwise {@code false}
     */
    public boolean blocksPath(Position start, Position end) {
        for (Obstacle o : obstacles) {
            if ("BOTTOMLESS_PIT".equalsIgnoreCase(o.getType())) {
                continue; // pits don't block path
            }
            if (o.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }

    public boolean pathContainsPit(Position start, Position end) {
        for (Obstacle o : obstacles) {
            if (!"BOTTOMLESS_PIT".equalsIgnoreCase(o.getType())) {
                continue;
            }
            if (o.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }

    public WorldConfig getConfig() {
        return this.config;
    }
//==========
}
