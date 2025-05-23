package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.Bullet;

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
    public static final Position CENTRE = new Position(0, 0);

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
        this.position = CENTRE;
        this.config = ConfigReader.loadConfig();
        TOP_LEFT = config.topLeft;
        BOTTOM_RIGHT = config.bottomRight;
        generateRandomObstacles(config.maxObstacles);

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

    /**
     * Returns a map of visible artefacts in each direction from the current robot position.
     * Currently returns an empty map as placeholder.
     *
     * @return a map of directions to artefacts
     */
    public Map<Direction, Artefact> look() {
        return Map.of();
    }

    /**
     * This method adds a Bullet object to the bullets list. The list holds all bullets currently in the world.
      * @param bullet the bullet to add
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Moves all bullets in the world one step forward.
     * Removes bullets that have stopped moving.
     * Side notes to be removed if needed.***
     */
    public void updateBullets() {
        Iterator<Bullet> it = bullets.iterator(); // Iterator allows for looping
                                                // without causing ConcurrentModificationException.
        while (it.hasNext()) {  // Loops as long as there are more bullets in the list.
            Bullet bullet = it.next(); // Gets the next bullet in the list.
            Position nextPos = bullet.getPosition();

            // Check for obstacle collision
            boolean hitObstacle = false;
            for (Obstacle obstacle : obstacles) {
                if (obstacle.blocksPosition(nextPos)) {
                    System.out.println("Bullet hit obstacle at: " + nextPos);
                    hitObstacle = true;
                    break;
                }
            }

            // Check for robot collision
            boolean hitRobot = false;
            for (Robot robot : getAllRobots()) {
                if (robot == bullet.getShooter()) continue;
                if (robot.getPosition().equals(nextPos)) {
                    System.out.println("Bullet hit robot: " + robot.getName());
                    hitRobot = true;
                    // Optionally update robot status here
                    break;
                }
            }

            if (hitObstacle || hitRobot || !bullet.move()) {
                it.remove();
                System.out.println("Removed bullet: " + bullet);
            }
        }
    }

    public List<Bullet> getBullets() {
        return bullets; // returns the current list of bullets
    }


    public boolean addBulletAndCheckHit(Bullet bullet) {
        bullets.add(bullet);
        // Move the bullet and check for collision with robots
        while (!bullet.hasStopped()) {
            bullet.move();
            for (Robot robot : getAllRobots()) {
                if (!robot.equals(bullet.getShooter()) && robot.getPosition().equals(bullet.getPosition())) {
                    // Apply damage here!
                    robot.applyDamage(10);  // Apply 10 damage, adjust as needed
                    System.out.println("Robot " + robot.getName() + " hit! Shield: " + robot.getCurrentShieldStrength());
                    return true;
                }
            }
        }
        return false;
    }

//==========
}
