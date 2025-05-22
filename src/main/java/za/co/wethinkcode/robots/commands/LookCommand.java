package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.*;
import za.co.wethinkcode.robots.commands.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command to look around in the world.
 * This command allows the robot to see obstacles, other robots, and the edge of the world
 * within its visibility range in all four directions.
 */
public class LookCommand extends Command {

    /**
     * Creates a new LookCommand instance.
     */
    public LookCommand() {
        super("look");
    }

    /**
     * Executes the look command, returning information about what the robot can see.
     * The robot looks in all four directions (NORTH, EAST, SOUTH, WEST) up to its visibility range.
     * It can see obstacles, other robots, and the edge of the world.
     * Mountains block vision, but lakes and pits do not.
     *
     * @param robot The robot that is looking around
     * @return A response containing information about what the robot can see
     */
    @Override
    public Response execute(Robot robot) {

        // Get the robot's position
        Position robotPosition = robot.getPosition();
        //Create data structure for the response
        Map<String, Object> data = new HashMap<>(); //data = "data":{}
        //List to store all found obstacles
        List<Map<String, Object>> objects = new ArrayList<>(); //"objects": [{},{},{},{}]
        // Checks every direction and every coordinate in that direction up to visibility range
        for (Direction direction : Direction.values()) {

            int visibilityConstraint = 10; //robot's visibility range
            boolean robotFound = false;
            boolean edgeFound = false;
            boolean obstacleFound = false;

            // Check each position in that direction up to visibility range
            for (int distance = 1; distance <= visibilityConstraint; distance++) {
                //currentPosition is the current position being checked in the robot's line of view eg. (1,0)/(2,0)
                Position currentPosition = moveInDirection(robotPosition, direction, distance);

                robotFound = checkForOtherRobot(currentPosition, direction, distance, robot, objects);
                //use method to look for obstacles
                if (checkForEdge(currentPosition, direction, distance, objects)) {
                    edgeFound = true;
                    break;
                }
                else if (checkForObstacle(currentPosition, direction, distance, robot, objects)) {
                    obstacleFound = true;
                    break; // Stop checking further in this direction if we found a mountain
                }
            }
            //If nothing was found in this direction and we at max visibility
            if (!obstacleFound && !edgeFound && !robotFound) {
                emptyDirection(direction, visibilityConstraint, objects);
            }
        }
        // Add objects to data
        data.put("objects", objects);
        // Return the response with the required format
        return new Response("OK", data, robot);
    }

    /**
     * Gets the specific type of obstacle.
     *
     * @param obstacle The obstacle to identify
     * @return A string representing the type of obstacle
     */
    private VisibleObjectType getObstacleType(Obstacle obstacle) {
        if (obstacle instanceof MountainObstacle) {
            return VisibleObjectType.MOUNTAIN;
        } else if (obstacle instanceof LakesObstacle) {
            return VisibleObjectType.LAKE;
        } else if (obstacle instanceof BottomlessPit) {
            return VisibleObjectType.BOTTOMLESS_PIT;
        } else {
            return null;
        }
    }

    /**
     * Calculates a position by moving in a specific direction from robot's starting position.
     *
     * @param start The starting position
     * @param direction The direction to move
     * @param steps The number of steps to move
     * @return The new position after moving
     */
    public Position moveInDirection(Position start, Direction direction, int steps){
        //Gets direction from for loop in "execute()" and adds/subtracts each no. in visRange from robot's position (eg. (0,0) +- (1-10) for steps to get a new Position
        switch (direction) {
            case NORTH:
                return new Position(start.getX(), start.getY() + steps);
            case EAST:
                return new Position(start.getX() + steps, start.getY());
            case SOUTH:
                return new Position(start.getX(), start.getY() - steps);
            case WEST:
                return new Position(start.getX() - steps, start.getY());
            default:
                return start;
        }
    }

    /**
     * Checks if a position is within the world boundaries.
     *
     * @param position The position to check
     * @return true if the position is within the world, false otherwise
     */
    private boolean isInWorld(Position position) {
        Position topLeft = TextWorld.TOP_LEFT;
        Position bottomRight = TextWorld.BOTTOM_RIGHT;
        return position.isIn(topLeft, bottomRight);
    }

    //Helper to Build the types of obstacles in each detection method
    private Map<String, Object> makeObject(String type, Direction direction, int distance) {
        Map<String, Object> object = new HashMap<>();
        object.put("direction", direction.toString());
        object.put("type", type);
        object.put("distance", distance);
        return object;
    }

    //Obstacle detection methods

    private void emptyDirection(Direction direction, int visibilityConstraint,List<Map<String, Object>> objects) {
        objects.add(makeObject(VisibleObjectType.EMPTY.name(), direction, visibilityConstraint));
    }

    // Check for obstacles
    private boolean checkForObstacle(Position position, Direction direction, int distance, Robot robot, List<Map<String, Object>> objects) {
        for (Obstacle obstacle : robot.getWorld().getObstacles()) {
            if (obstacle.blocksPosition(position)) {
                VisibleObjectType obstacleType = getObstacleType(obstacle);
                objects.add(makeObject(obstacleType.name(), direction, distance));
                // Return true only if vision is blocked (mountain), can't see past it
                return obstacle instanceof MountainObstacle;
            }
        }
        return false;
    }

    // Check for world edges
    private boolean checkForEdge(Position currentPosition, Direction direction, int distance, List<Map<String, Object>> objects) {
        if (!isInWorld(currentPosition)) {
            objects.add(makeObject(VisibleObjectType.EDGE.name(), direction, distance));
            return true; // Stop checking this direction
        }
        return false;
    }

    // Check for other robots
    // This would require access to all robots in the world
    private boolean checkForOtherRobot(Position currentPosition, Direction direction, int distance, Robot robot, List<Map<String, Object>> objects) {
        if (robot.getWorld() instanceof TextWorld world) {
            for (Robot otherRobot : world.getAllRobots()) {
                //if robot diff from our robot and its on a position we are looking at, add it to the objects list
                if (!otherRobot.equals(robot) && otherRobot.getPosition().equals(currentPosition)) {
                    objects.add(makeObject(VisibleObjectType.ROBOT.name(), direction, distance));
                    return true; // Stop checking this direction
                }
            }
        }
        return false;
    }

}
