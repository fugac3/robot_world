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

public class LookCommand extends Command {

    public LookCommand() {
        super("look");
    }

    @Override
    public Response execute(Robot robot) {
        // Get the robot's position
        Position robotPosition = robot.getPosition();

        //Create data structure for the response
        Map<String, Object> data = new HashMap<>();
        //List to store all found obstacles
        List<Map<String, Object>> objects = new ArrayList<>();

        // Checks every direction and every coordinate in that direction up to visibility range
        for (Direction direction : Direction.values()) {

            //skip over the code if up, down, left or right
            if (direction == Direction.UP || direction == Direction.DOWN || direction == Direction.LEFT || direction == Direction.RIGHT) {
                continue;
            }
            int visibilityConstraint = 10; //robot's visibility range
            boolean obstacleFound = false;

            // Check each position in that direction up to visibility range
            for (int distance = 0; distance < visibilityConstraint; distance++) {
                //currentPosition is the current position being checked in the robot's line of view eg. (1,0)/(2,0)
                Position currentPosition = moveInDirection(robotPosition, direction, distance);

                // Check for obstacles
                for (Obstacle obstacle : robot.getWorld().getObstacles()) { //AbstractWorld method to get obstacles
                    if (obstacle.blocksPosition(currentPosition)) {
                        //Create obstacle object/dict
                        Map<String, Object> obstacleObject = new HashMap<>();

                        //OBSTACLE DETAILS IN RIGHT FORMAT
                        obstacleObject.put("direction", direction.toString());
                        //Determine type of obstacle
                        String obstacleType = getObstacleType(obstacle); //method below
                        obstacleObject.put("type", obstacleType); //returns pit/lake/mountain
                        obstacleObject.put("distance", distance);
                        objects.add(obstacleObject); //add the obstacle to objects list

                        //If it's a mountain, we can't see past it
                        if (obstacle instanceof MountainObstacle) {
                            obstacleFound = true; // so that we can end the loop for direction below (because we can't see past mountains) otherwise loop continues till constraint
                            break;
                        }

                        //For lakes and pits, we can see through them, so continue loop till we get to constraint
                    }
                }

                //Only applies for mountains
                if (obstacleFound) {
                    break; // Stop checking further in this direction if we found a mountain
                }

                // Check for world edges
                if (!isInWorld(currentPosition)) { //if currentPos is out of bounds
                    Map<String, Object> edgeObject = new HashMap<>();
                    edgeObject.put("direction", direction.toString());
                    edgeObject.put("type", "EDGE");
                    edgeObject.put("distance", distance);
                    objects.add(edgeObject);

                    obstacleFound = true;
                    break;
                }

                // Check for other robots
                // This would require access to all robots in the world
                if (robot.getWorld() instanceof TextWorld world) {
                    //TextWorld world = (TextWorld) robot.getWorld();

                    for (Robot otherRobot : world.getAllRobots()) {
                        //if robot diff from our robot and its on a position we are looking at, add it to the objects list
                        if (!otherRobot.equals(robot) && otherRobot.getPosition().equals(currentPosition)) {
                            Map<String, Object> robotObject = new HashMap<>();
                            robotObject.put("direction", direction.toString());
                            robotObject.put("type", "ROBOT");
                            robotObject.put("distance", distance);
                            objects.add(robotObject);

                            obstacleFound = true;
                            break;
                        }
                    }
                }

                //stop looking in current direction as you can't see past robot
                if (obstacleFound) {
                    break;
                }
            }

            //If nothing was found in this direction and we at max visibility
            if (!obstacleFound) {
                Map<String, Object> emptyObject = new HashMap<>();
                emptyObject.put("direction", direction.toString());
                emptyObject.put("type", "EMPTY");
                emptyObject.put("distance", visibilityConstraint);
                objects.add(emptyObject);
            }
        }

        // Add objects to data
        data.put("objects", objects);

        // Return the response with the required format
        return new Response("OK", data, robot);
    }

    // Method to get the specific type of obstacle
    private String getObstacleType(Obstacle obstacle) {
        if (obstacle instanceof MountainObstacle) {
            return "MOUNTAIN";
        } else if (obstacle instanceof LakesObstacle) {
            return "LAKE";
        } else if (obstacle instanceof BottomlessPit) {
            return "BOTTOMLESS PIT";
        } else {
            return "OBSTACLE"; // Generic fallback
        }
    }

    //Method to see in direction up to visibility range
    public Position moveInDirection(Position start, Direction direction, int steps){
        //Gets direction from for loop in "look()" and adds/subtracts each no. in visRange from robot's position (eg. (0,0) +- (1-10) for steps to get a new Position
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

    //Method to check if a position is within the world boundaries
    private boolean isInWorld(Position position) {

        Position topLeft = new Position(-200, 100);
        Position bottomRight = new Position(100, -200);
        return position.isIn(topLeft, bottomRight);
    }
}