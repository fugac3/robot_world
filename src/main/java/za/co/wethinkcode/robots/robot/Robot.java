package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.world.IWorld;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;

public class Robot {

//  top left of where the world end.
    private final Position TOP_LEFT = new Position(-200,100);

//    Bottom right of where the world start.
    private final Position BOTTOM_RIGHT = new Position(100,-200);

// The center of where the world where the robot will start.
    public static final Position CENTRE = new Position(0,0);

//  The direction of where the robot will be facing as a starting point.
    private Direction currentDirection = Direction.NORTH;

//  The status of the position where the robot is facing and its coordinates
    private String status;

//  This is the name of the bot and i set it to final so that its wont be change.
    private final String name;

//  This  attribute inherit from the interface so that the can hiden unessesary details.
    private final IWorld world;

// This attribute is set to list so that it can store the commands
    private final List<String> commands;

    public Robot(String name) {
/*
    This a constractor that can make the attribute reusable
 */
        this.name = name;
        this.status = "Ready";
        this.commands = new ArrayList<>();
        this.world = new TextWorld();
    }

    public boolean updatePosition(int nrSteps){

//      Setting the position according to the starting position in y.
        int newY = this.world.getPosition().getY();

//      Setting the position according to the starting position in x.
        int newX = this.world.getPosition().getX();

//      Making switch to check the give direction do they increase or dicrease according to the command.
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

//      if the position is set correctly from switch then it must be set to the new position
        Position newPosition = new Position(newX,  newY);

//      while the bot is moving even check the blocked position then return the position.
        if (world.blocksPath(this.world.getPosition(), newPosition)) {
            this.setStatus("Sorry, there's an obstacle in the way.");
            return false;
        }

//      Setting the position according to the current position.
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.world.setPosition(newPosition);
            this.setStatus("Moved forward by " + nrSteps + " steps.");
            return true;
        }
        return false;
    }

//  Getting the position of the robot.
    public Position getPosition() {
        return this.world.getPosition();
    }

//  Getting the world where the bot will be moving.
    public IWorld getWorld() {
        return this.world;
    }

//  Getting the status of the robot coordinates.
    public String getStatus() {
        return this.status;
    }

//  Getting the current direction where it is now
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

//  This method checks the command if its true the it will excute the results.
    public boolean handleCommand(Command command) {
        boolean result = command.execute(this);
        addCommand(command.getName() + " " + command.getArgument().trim());
        return result;
    }

//  Adding the command that have been used to keep track of the coordinates.
    public void addCommand(String command) {
        commands.add(command);
    }

//  Storing all the movement of the bot.
    public List<String> getCommands() {
        return commands;
    }

//   Making a bot to be able to turn right.
    public void turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
    }

    //   Making a bot to be able to turn left.
    public void turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
    }


// returning the coordinates as a formate string.
    @Override
    public String toString() {
        return "[" + this.world.getPosition().getX() + "," + this.world.getPosition().getY() + "] "
                + this.name + " > " + this.status;
    }


//  Updating coordinates.
    public void setStatus(String status) {
        this.status = status;
    }
// updating name.
    public String getName() {
        return name;
    }
}
