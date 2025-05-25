package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.Obstacles.Obstacle;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.Combat.Bullet;

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

    /** Central starting position in the world. */
//    public static final Position CENTRE = new Position(0, 0);

    /** Singleton instance of the TextWorld. */
    private static TextWorld instance;

    /** Map of robot names to robot instances. */
    private final Map<String, Robot> robots = new HashMap<>();

    /** A position field (not frequently used in this context). */
    private Position position;

    private WorldConfig config;

    private final List<Bullet> bullets = new ArrayList<>();



    /**
     * Constructs a new TextWorld with the center position and generates random obstacles.
     */
    public TextWorld() {
//        this.position = CENTRE;
        this.config = ConfigReader.loadConfig();
        TOP_LEFT = config.topLeft;
        BOTTOM_RIGHT = config.bottomRight;
        generateRandomObstacles(config.maxObstacles);
    }

    //just for custom sized worlds in tests
    public TextWorld(Position TOP_LEFT,Position BOTTOM_RIGHT) {
//        this.position = CENTRE;
        this.config = ConfigReader.loadConfig();
        TextWorld.TOP_LEFT = TOP_LEFT;
        TextWorld.BOTTOM_RIGHT = BOTTOM_RIGHT;
        generateRandomObstacles(config.maxObstacles);
    }

    public int setVisibilityConstraint(int newVis) {
        return newVis;
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
//            int x = 0;
//            int y = 0;

            pos = new Position(x, y);
        } while (blocksPosition(pos));  // Retry if blocked
        // cant tell if the world is full
        return pos;
    }

    /**
     * Determines whether the given position is blocked by an obstacle or another robot.
     *
     * @param pos the position to check
     * @return {@code true} if the position is occupied, otherwise {@code false}
     */
    public boolean blocksPosition(Position pos) {
        // Check for obstacles
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.blocksPosition(pos)) {
//                System.out.println("Obstacle stuck");
                return true;
            }
        }

        // Check for other robots
        for (Robot robot : getAllRobots()) {
            if (robot.getPosition().equals(pos)) {
//                System.out.println("Robot stuck");
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
     * Determines whether any obstacle blocks the straight-line path between two positions.
     *
     * @param start the starting position
     * @param end the ending position
     * @return {@code true} if the path is blocked, otherwise {@code false}
     */

    public boolean blocksPath(Position start, Position end) {
        for (Obstacle o : obstacles) {
            if (o.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the internal position (not directly used for robot positioning).
     *
     * @param pos the new position
     */
    public void setPosition(Position pos) {
        this.position = pos;
    }

//==========
}
