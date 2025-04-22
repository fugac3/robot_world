package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public static final Position CENTRE = new Position(0,0);

//    private Position position;
    private Direction currentDirection = Direction.NORTH;
    private String status;
    private final String name;
    private final IWorld world;


    private final List<String> commands;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.commands = new ArrayList<>();
        this.world = new TextWorld();
    }

    public boolean updatePosition(int nrSteps){
        int newY = this.world.getPosition().getY();
        int newX = this.world.getPosition().getX();

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

        if (world.blocksPath(this.world.getPosition(), newPosition)) {
            this.setStatus("Sorry, there's an obstacle in the way.");
            return false;
        }

        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
//            this.position() = newPosition;
            this.world.setPosition(newPosition);
            this.setStatus("Moved forward by " + nrSteps + " steps.");
            return true;
        }
        return false;
    }


    public Position getPosition() {
        return this.world.getPosition();
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
        return "[" + this.world.getPosition().getX() + "," + this.world.getPosition().getY() + "] "
                + this.name + "> " + this.status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
}
