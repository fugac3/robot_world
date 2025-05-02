package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.IWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public static final Position CENTRE = new Position(0,0);
    private Map<String, Object> state = new HashMap<>();
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

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public Map<String, Object> getState() {
        Map<String, Object> currentState = new HashMap<>();

        Map<String, Integer> position = new HashMap<>();
        position.put("x", this.position.getX());
        position.put("y", this.position.getY());

        currentState.put("position", position);  // Store position as a map of x and y
        currentState.put("direction", this.currentDirection.toString());
        currentState.put("status", this.status);
        currentState.put("name", this.name);
        return currentState;
    }

    public boolean updatePosition(int nrSteps){
        int newY = this.position.getY();
        int newX = this.position.getX();

        switch (currentDirection){
            case NORTH:
                newY += nrSteps;
                break;
            case SOUTH:
                newY -= nrSteps;
                break;
            case EAST:
                newX += nrSteps;
                break;
            case WEST:
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
            setStatus("Moved forward by " + nrSteps + " steps.");
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

    public Response handleCommand(Command command) {
        Response response = command.execute(this);
        addCommand(command.getName() + " " + command.getArgument().trim());
        return response;
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
}
