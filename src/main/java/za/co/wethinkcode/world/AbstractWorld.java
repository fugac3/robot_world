package za.co.wethinkcode.world;



import za.co.wethinkcode.Position;
import za.co.wethinkcode.maze.Maze;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractWorld implements World {
    private final Position TOP_LEFT = new Position(-100,200);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    public static final Position CENTRE = new Position(0,0);
    private List<Obstacle> obstacles = new ArrayList<>();
    private final Maze maze;
    private  Direction currentDirection;
    private Position position;


    public AbstractWorld(Maze maze) {
        this.maze = maze;
        this.currentDirection = Direction.NORTH;
        this.position = CENTRE;
    }


    public Maze getMaze(){
        return this.maze;
    }
    public void plotMaze(){}
    public void moveForwardRobot(Position position){}
    public void moveBackRobot(Position position){}
    public void turtleWorldExit(){}




    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();


        if (Direction.NORTH.equals(this.currentDirection) || Direction.UP.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }else if(Direction.RIGHT.equals(this.currentDirection)){
            newX = newX + nrSteps;
        }else  if(Direction.DOWN.equals(this.currentDirection)){
            newY = newY - nrSteps;
        }else if(Direction.LEFT.equals(this.currentDirection)){
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OBSTRUCTED;
    }


    @Override
    public void updateDirection(boolean turnRight) {
        if(Direction.NORTH.equals(this.currentDirection) || Direction.UP.equals(this.currentDirection)){
            if(turnRight) {
                this.currentDirection = Direction.RIGHT;
            }else{
                this.currentDirection = Direction.LEFT;
            }
        }else if(Direction.RIGHT.equals(this.currentDirection)){
            if(turnRight) {
                this.currentDirection = Direction.DOWN;
            }else{
                this.currentDirection = Direction.UP;
            }
        }else  if(Direction.DOWN.equals(this.currentDirection)){
            if(turnRight) {
                this.currentDirection = Direction.LEFT;
            }else{
                this.currentDirection = Direction.RIGHT;
            }
        }else if(Direction.LEFT.equals(this.currentDirection)){
            if(turnRight) {
                this.currentDirection = Direction.UP;
            }else{
                this.currentDirection = Direction.DOWN;
            }
        }
    }


    public boolean isPathBlocked(Position currentPosition,Position destPosition){
        if(position.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            for(Obstacle obstacle: obstacles){
                if(obstacle.blocksPath(currentPosition,destPosition)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        if(position.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            for(Obstacle obstacle: obstacles){
                if(obstacle.blocksPosition(position)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        this.currentDirection = Direction.UP;
        this.position = CENTRE;
    }


    @Override
    public List<Obstacle> getObstacles() {
        this.obstacles = maze.getObstacles();
        return this.obstacles;
    }

    @Override
    public boolean isAtEdge() {
        if(getPosition().getY() == 200 && getPosition().getX() ==0) {
            return true;
        }else if(getPosition().getY() == 0 && getPosition().getX() ==100) {
            return true;
        }else if(getPosition().getY() == -200 && getPosition().getX() ==0) {
            return true;
        }else return getPosition().getY() == 0 && getPosition().getX() == -100;
    }


    @Override
    public void showObstacles() {
        this.getObstacles();
        if(obstacles.size() > 0) {
            System.out.println("There are some obstacles:");
            for(Obstacle obstacle: obstacles ){
                int bottomConnerX = obstacle.getBottomLeftCorner().getX();
                int bottomConnerY = obstacle.getBottomLeftCorner().getY();
                int topConnerX = obstacle.getTopRightConner().getX();
                int topConnerY = obstacle.getTopRightConner().getY();
                System.out.printf("- At position %d,%d (to %d,%d)%n",bottomConnerX,bottomConnerY,topConnerX,topConnerY);
            }
        }
    }

}
