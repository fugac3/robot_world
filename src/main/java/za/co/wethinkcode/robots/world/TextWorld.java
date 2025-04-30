package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
//import za.co.wethinkcode.robot.position;


public class TextWorld extends AbstractWorld{

//    private static TextWorld instance = new TextWorld();
    private static TextWorld instance;

    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    public static final Position CENTRE = new Position(0,0);

    //    private final ConcurrentMap<String, Robot> robots = new ConcurrentHashMap<>();
    private final Map<String, Robot> robots = new HashMap<>();
    private Position position;

    private TextWorld() {
        this.position = CENTRE;
        generateRandomObstacles(TOP_LEFT,BOTTOM_RIGHT);
    }

//    public static TextWorld getInstance(){
//        return instance;
//    }
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


//    public boolean registerRobot(Robot robot) {
//        // returns false if a robot with that name already exists
//        return robots.putIfAbsent(robot.getName(), robot) == null;
//    }

//    public Robot getRobotByName(String name) {
//        return robots.get(name);
//    }
//
//    public int getRobotCount() {
//        return robots.size();
//    }
//
//    public Map<String, Robot> getRobots() {
//        return Collections.unmodifiableMap(robots);
//    }





    @Override
    public boolean blocksPath(Position start, Position end) {
        for (Obstacle o : obstacles) {
            if (o.blocksPath(start, end)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Updates the position of your robot in the world by moving the nrSteps in the robots current direction.

     //     * @param nrSteps steps to move in current direction
     //     * @return true if this does not take the robot over the world's limits, or into an obstacle.
     */
    @Override
    public boolean updatePosition(int nrSteps) {
        return false;
    }



    /**
     * Updates the current direction your robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     *
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    @Override
    public void updateDirection(boolean turnRight) {


    }




    public void setPosition(Position pos) {
        this.position = pos;
    }

    @Override
    public Map<za.co.wethinkcode.robots.commands.Direction, Artefact> look() {
        return Map.of();
    }

    /**
     * Gets the current direction the robot is facing in relation to a world edge.
     *
     * @return Direction.UP, RIGHT, DOWN, or LEFT
     */
    @Override
    public Direction getCurrentDirection() {
        return Direction.NORTH;
    }

    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     *
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    @Override
    public boolean isNewPositionAllowed(Position position) {

//        if(!position.equals(getObstacles())){
//            return true;
//        }
        return false;
    }

    /**
     * Checks if the robot is at one of the edges of the world
     *
     * @return true if the robot's current is on one of the 4 edges of the world
     */
    @Override
    public boolean isAtEdge() {
        return false;
    }

    /**
     * Reset the world by:
     * - moving current robot position to center 0,0 coordinate
     * - removing all obstacles
     * - setting current direction to UP
     */
    @Override
    public void reset() {

    }




//==========
}
