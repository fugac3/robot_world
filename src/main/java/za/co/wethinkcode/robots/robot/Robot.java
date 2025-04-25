package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.world.Artefact;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public static final Position CENTRE = new Position(0,0);
    protected List<Obstacle> obstacles = new ArrayList<>();

    //    private Position position;
    private Direction currentDirection = Direction.NORTH;
    private String status;
    private final String name;
    private final IWorld world;
    private Position position;



    private final List<String> commands;

    //    public Robot(String name) {
    public Robot(String name,IWorld world) {
        this.name = name;
        this.status = "Ready";
        this.commands = new ArrayList<>();
        this.world = world;
        this.position = new Position(0, 0); // start at center
//        this.world = new TextWorld();
    }

    public boolean updatePosition(int nrSteps){
        int newY = this.position.getY();
        int newX = this.position.getX();

        switch (currentDirection){
            case Direction.NORTH:
                newY += nrSteps;
                break;
            case Direction.SOUTH:
                newY -= nrSteps;
                break;
            case Direction.EAST:
                newX += nrSteps;
                break;
            case Direction.WEST:
                newX -= nrSteps;
                break;
            default:
                status = "Invalid direction" + currentDirection;
        }

        Position newPosition = new Position(newX,  newY);

        if (world.blocksPath(this.position, newPosition)) {
            this.setStatus("Sorry, there's an obstacle in the way.");
            return false;
        }

        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
//            this.world.setPosition(newPosition);
            this.setStatus("Moved forward by " + nrSteps + " steps.");
            return true;
        }
        return false;
    }


    public Position getPosition() {
        return this.position;
    }


    public IWorld getWorld() {
        return this.world;
    }


    public String getStatus() {
        return this.status;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public boolean handleCommand(Command command) {
        boolean result = command.execute(this);
        addCommand(command.getName() + " " + command.getArgument().trim());
        return result;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public List<String> getCommands() {
        return commands;
    }

    public void turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
    }

    public void turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
    }



    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Map<IWorld.Direction, Artefact> lookAround() {
        Map<Direction, Artefact> view = world.look();
        System.out.println("Robot looks around and sees:");
        for (Map.Entry<Direction, Artefact> entry : view.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
        return new HashMap<>();
    }

    public Map<IWorld.Direction, Artefact> look(Position robotPosition) {
        Map<IWorld.Direction, Artefact> visibleArea = new HashMap<>();

        // Check each direction (NORTH, SOUTH, EAST, WEST)
        if (isObstacleInDirection(robotPosition, IWorld.Direction.NORTH)) {
            visibleArea.put(IWorld.Direction.NORTH, Artefact.OBSTACLE);
        } else {
            visibleArea.put(IWorld.Direction.NORTH, Artefact.EMPTY); // No obstacle, empty space
        }

        if (isObstacleInDirection(robotPosition, IWorld.Direction.SOUTH)) {
            visibleArea.put(IWorld.Direction.SOUTH, Artefact.OBSTACLE);
        } else {
            visibleArea.put(IWorld.Direction.SOUTH, Artefact.EMPTY); // No obstacle, empty space
        }

        if (isObstacleInDirection(robotPosition, IWorld.Direction.EAST)) {
            visibleArea.put(IWorld.Direction.EAST, Artefact.OBSTACLE);
        } else {
            visibleArea.put(IWorld.Direction.EAST, Artefact.EMPTY); // No obstacle, empty space
        }

        if (isObstacleInDirection(robotPosition, IWorld.Direction.WEST)) {
            visibleArea.put(IWorld.Direction.WEST, Artefact.OBSTACLE);
        } else {
            visibleArea.put(IWorld.Direction.WEST, Artefact.EMPTY); // No obstacle, empty space
        }

        return visibleArea;
    }

    private boolean isObstacleInDirection(Position robotPosition, IWorld.Direction direction) {
        Position targetPosition = getTargetPositionInDirection(robotPosition, direction);

        // Check if there's an obstacle at the target position
        for (Obstacle obstacle : obstacles) {
            if (obstacle.contains(targetPosition)) {
                return true; // There's an obstacle in this direction
            }
        }
        return false; // No obstacle
    }

    private Position getTargetPositionInDirection(Position robotPosition, IWorld.Direction direction) {
        return robotPosition;
    }



}
