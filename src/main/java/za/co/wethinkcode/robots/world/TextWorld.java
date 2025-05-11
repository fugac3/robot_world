package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.*;
//import za.co.wethinkcode.robot.position;


public class TextWorld extends AbstractWorld{

    public static Position TOP_LEFT;
    public static Position BOTTOM_RIGHT;
//    public static final Position TOP_LEFT = new Position(0,5);
//    public static final Position BOTTOM_RIGHT = new Position(5,0);
    private static TextWorld instance;
    private final WorldConfig config;

    public static final Position CENTRE = new Position(0,0);

    private final Map<String, Robot> robots = new HashMap<>();
    private Position position;

    public TextWorld() {
        this.position = CENTRE;
        this.config = ConfigReader.loadConfig();
        TOP_LEFT = config.topLeft;
        BOTTOM_RIGHT = config.bottomRight;
        generateRandomObstacles(config.maxObstacles);
    }

    public static synchronized TextWorld getInstance() {
        if (instance == null) {
            instance = new TextWorld();
        }
        return instance;
    }

//    public Position getNonRandomPosition() {
//        Position pos = new Position(0, 0);
//        System.out.println(blocksPosition(pos));
//        return pos;
//    }

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


    public boolean blocksPosition(Position pos) {
        // Check obstacles
        for (Obstacle obstacle : this.obstacles) {
            if (obstacle.blocksPosition(pos)) {
                System.out.println("Obstacle stuck");
                return true;
            }
        }

        // Check other robots
        for (Robot robot : getAllRobots()) {
            if (robot.getPosition().equals(pos)) {
                System.out.println("Robot stuck");
                return true;
            }
        }

        return false;
    }


    public void addRobot(Robot robot) {

        robots.put(robot.getName(), robot);
    }

    public Collection<Robot> getAllRobots() {
        return robots.values();
    }

    @Override
    public boolean blocksPath(Position start, Position end) {
        for (Obstacle o : obstacles) {
            if (o.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    @Override
    public Map<za.co.wethinkcode.robots.commands.Direction, Artefact> look() {
        return Map.of();
    }
//==========
}
