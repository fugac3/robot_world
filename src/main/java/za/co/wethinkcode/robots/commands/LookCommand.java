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
        Position robotPosition = robot.getPosition();
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> objects = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN || direction == Direction.LEFT || direction == Direction.RIGHT) {
                continue;
            }

            int visibilityConstraint = 10;
            boolean obstacleFound = false;

            for (int distance = 0; distance < visibilityConstraint; distance++) {
                Position currentPosition = moveInDirection(robotPosition, direction, distance);

                for (Obstacle obstacle : robot.getWorld().getObstacles()) {
                    if (obstacle.blocksPosition(currentPosition)) {
                        Map<String, Object> obstacleObject = new HashMap<>();
                        obstacleObject.put("direction", direction.toString());
                        String obstacleType = getObstacleType(obstacle);
                        obstacleObject.put("type", obstacleType);
                        obstacleObject.put("distance", distance);
                        objects.add(obstacleObject);

                        // Add message for obstacle
                        if (obstacle instanceof MountainObstacle) {
                            messages.add("Cannot move or see past a mountain to the " + direction.toString());
                            obstacleFound = true;
                            break;
                        } else {
                            messages.add("There is a " + obstacleType.toLowerCase() + " to the " + direction.toString());
                        }
                    }
                }

                if (obstacleFound) break;

                if (!isInWorld(currentPosition)) {
                    Map<String, Object> edgeObject = new HashMap<>();
                    edgeObject.put("direction", direction.toString());
                    edgeObject.put("type", "EDGE");
                    edgeObject.put("distance", distance);
                    objects.add(edgeObject);
                    messages.add("World edge reached to the " + direction.toString());
                    obstacleFound = true;
                    break;
                }

                if (robot.getWorld() instanceof TextWorld world) {
                    for (Robot otherRobot : world.getAllRobots()) {
                        if (!otherRobot.equals(robot) && otherRobot.getPosition().equals(currentPosition)) {
                            Map<String, Object> robotObject = new HashMap<>();
                            robotObject.put("direction", direction.toString());
                            robotObject.put("type", "ROBOT");
                            robotObject.put("distance", distance);
                            objects.add(robotObject);
                            messages.add("Another robot is blocking the way to the " + direction.toString());
                            obstacleFound = true;
                            break;
                        }
                    }
                }

                if (obstacleFound) break;
            }

            if (!obstacleFound) {
                Map<String, Object> emptyObject = new HashMap<>();
                emptyObject.put("direction", direction.toString());
                emptyObject.put("type", "EMPTY");
                emptyObject.put("distance", visibilityConstraint);
                objects.add(emptyObject);
            }
        }

        data.put("objects", objects);
        data.put("messages", messages);
        return new Response("OK", data, robot);
    }

    private String getObstacleType(Obstacle obstacle) {
        if (obstacle instanceof MountainObstacle) {
            return "MOUNTAIN";
        } else if (obstacle instanceof LakesObstacle) {
            return "LAKE";
        } else if (obstacle instanceof BottomlessPit) {
            return "BOTTOMLESS PIT";
        } else {
            return "OBSTACLE";
        }
    }

    public Position moveInDirection(Position start, Direction direction, int steps){
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

    private boolean isInWorld(Position position) {
        Position topLeft = new Position(-200, 100);
        Position bottomRight = new Position(100, -200);
        return position.isIn(topLeft, bottomRight);
    }
}
