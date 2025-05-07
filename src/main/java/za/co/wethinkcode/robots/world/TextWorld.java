package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.*;
//import za.co.wethinkcode.robot.position;


public class TextWorld extends AbstractWorld{

    public static final Position TOP_LEFT = new Position(-200,100);
    public static final Position BOTTOM_RIGHT = new Position(100,-200);
    private static TextWorld instance;

    public static final Position CENTRE = new Position(0,0);

    private final Map<String, Robot> robots = new HashMap<>();
    private Position position;

    private TextWorld() {
        this.position = CENTRE;
        generateRandomObstacles();
    }

    public static synchronized TextWorld getInstance() {
        if (instance == null) {
            instance = new TextWorld();
        }
        return instance;
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
