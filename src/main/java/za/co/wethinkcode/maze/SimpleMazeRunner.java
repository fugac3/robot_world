package za.co.wethinkcode.maze;



import za.co.wethinkcode.*;
import za.co.wethinkcode.world.World;
import za.co.wethinkcode.world.Obstacle;
import za.co.wethinkcode.*;

import java.util.*;

public class SimpleMazeRunner implements  MazeRunner{
    private final ArrayList<Position> routeToEdge = new ArrayList<>();
    private final ArrayList<String> path = new ArrayList<>();
    private static int stepCount = 0;
    private final ArrayList<String> message = new ArrayList<>();
    private Maze maze;
    private final HashSet<String> visited = new HashSet<>();
    private final LinkedList<Position> frontier = new LinkedList<>();
    private final HashMap<Position,Position> solution = new HashMap<>();





    @Override
    public int getMazeRunCost() {
        return stepCount;
    }


    public void setMaze(Maze maze){
        this.maze = maze;
    }

    public Maze getMaze(){
        return this.maze;
    }


    private void search(Position position){
        frontier.add(position);
        solution.put(position,position);

        while(frontier.size() > 0){
            Position positionXY = frontier.pollFirst();
            int x = positionXY.getX();
            int y = positionXY.getY();
            if(path.contains(String.format("(%d,%d)",x-4,y)) && !visited.contains(String.format("(%d,%d)",x-4,y))){
                Position newPosition = new Position(x-4,y);
                solution.put(newPosition,new Position(x,y));
                frontier.add(newPosition);
                visited.add(String.format("(%d,%d)",x-4,y));
            }
            if(path.contains(String.format("(%d,%d)",x,y-4)) && !visited.contains(String.format("(%d,%d)",x,y-4))){
                Position newPosition = new Position(x,y-4);
                solution.put(newPosition,new Position(x,y));
                frontier.add(newPosition);
                visited.add(String.format("(%d,%d)",x,y-4));

            }
            if(path.contains(String.format("(%d,%d)",x+4,y)) && !visited.contains(String.format("(%d,%d)",x+4,y))){
                Position newPosition = new Position(x+4,y);
                solution.put(newPosition,new Position(x,y));
                frontier.add(newPosition);
                visited.add(String.format("(%d,%d)",x+4,y));

            }
            if(path.contains(String.format("(%d,%d)",x,y+4)) && !visited.contains(String.format("(%d,%d)",x,y+4))){
                Position newPosition = new Position(x,y+4);
                solution.put(newPosition,new Position(x,y));
                frontier.add(newPosition);
                visited.add(String.format("(%d,%d)",x,y+4));

            }

        }

    }
    @Override
    public boolean mazeRun(Robot target, IWorld.Direction edgeDirection) {
        stepCount = 0;
        String m = String.format("  > {%s} starting maze run..",target.getName());
        System.out.println(m);

        List<Obstacle> listOfPathObstacles = maze.getObstaclePath();

        for(Obstacle obstacle: listOfPathObstacles) {
            path.add(String.format("(%d,%d)", obstacle.getBottomLeftCorner().getX(), obstacle.getBottomLeftCorner().getY()));
        }


        search(verifyLocation(target.getPosition()));


        if(edgeDirection.equals(World.Direction.UP)){
            backTracker(new Position(0, 200), target.getPosition());
        }else if(edgeDirection.equals(World.Direction.DOWN)){
            backTracker(new Position(0, -192), target.getPosition());
        }else if(edgeDirection.equals(World.Direction.RIGHT)){
            backTracker(new Position(96, 0), target.getPosition());
        }else if(edgeDirection.equals(World.Direction.LEFT)){
            backTracker(new Position(-100, 0), target.getPosition());
        }

        robotController(target);
        return false;
    }

    private void backTracker(Position edge, Position currentPosition){
        routeToEdge.add(edge);
        while(!String.format("(%d,%d)",edge.getX(),edge.getY()).equalsIgnoreCase(String.format("(%d,%d)",currentPosition.getX(),currentPosition.getY()))){
            for(Position keys : solution.keySet()) {
                if(String.format("(%d,%d)",keys.getX(),keys.getY()).equalsIgnoreCase(String.format("(%d,%d)",edge.getX(),edge.getY()))){
                    edge = solution.get(keys);
                    routeToEdge.add(edge);
                    break;
                }
            }

        }
    }


    private void moveForward(Robot robot){
        for(int step = 1; step <= 4; step++) {
            stepCount++;
            String instruction = "forward " +1;
            Command command = Command.create(instruction);
            robot.handleCommand(command);
            int x = robot.getPosition().getX();
            int y = robot.getPosition().getY();
            String position = "[" + x + "," + y + "] ";
            message.add(position + robot.getName() + "> " + robot.getStatus());
        }
        Play.display(message);
    }


    private void turnRight(Robot robot){
        stepCount++;
        String instruction = "right";
        Command command = Command.create(instruction);
        robot.handleCommand(command);
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        String  position = "[" + x + "," + y + "] ";
        message.add(position + robot.getName() + "> " + robot.getStatus());
        Play.display(message);
    }


    private void turnLeft(Robot robot){
        stepCount++;
        String instruction = "left";
        Command command = Command.create(instruction);
        robot.handleCommand(command);
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        String  position = "[" + x + "," + y + "] ";

        message.add(position + robot.getName() + "> " + robot.getStatus());
        Play.display(message);
    }

    private void robotController(Robot robot){

        for(int i = routeToEdge.size()-1; i > -1; i--){
            Position position = routeToEdge.get(i);

            int startX = robot.getPosition().getX();
            int startY = robot.getPosition().getY();

            if(position.getY() > startY){
                if(robot.getCurrentDirection().equals(Direction.UP) || robot.getCurrentDirection().equals(Direction.NORTH)){
                    moveForward(robot);
                }else  if(robot.getCurrentDirection().equals(Direction.RIGHT)){
                    turnLeft(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.DOWN)){
                    turnLeft(robot);
                    turnLeft(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.LEFT)){
                    turnRight(robot);
                    moveForward(robot);
                }
            }
            else if(position.getY() < startY){

                if(robot.getCurrentDirection().equals(Direction.DOWN)){
                    moveForward(robot);
                }else  if(robot.getCurrentDirection().equals(Direction.RIGHT)){
                    turnRight(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.UP) || robot.getCurrentDirection().equals(Direction.NORTH)){
                    turnLeft(robot);
                    turnLeft(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.LEFT)){
                    turnLeft(robot);
                    moveForward(robot);
                }
            }
            else if(position.getX() > startX){

                if(robot.getCurrentDirection().equals(Direction.RIGHT)){
                    moveForward(robot);
                }else  if(robot.getCurrentDirection().equals(Direction.UP) || robot.getCurrentDirection().equals(Direction.NORTH)){
                    turnRight(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.LEFT)){
                    turnRight(robot);
                    turnRight(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.DOWN)){
                    turnLeft(robot);
                    moveForward(robot);
                }
            }
            else if(position.getX() < startX){

                if(robot.getCurrentDirection().equals(Direction.LEFT)){
                    moveForward(robot);
                }else  if(robot.getCurrentDirection().equals(Direction.UP) || robot.getCurrentDirection().equals(Direction.NORTH)){
                    turnLeft(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.RIGHT)){
                    turnLeft(robot);
                    turnLeft(robot);
                    moveForward(robot);
                }else if(robot.getCurrentDirection().equals(Direction.DOWN)){
                    turnRight(robot);
                    moveForward(robot);
                }
            }
        }


    }

    private Position verifyLocation(Position position){
        int startx = position.getX();
        int starty = position.getY();

        if(startx % 4 !=0){
            startx = startx - startx % 4;
        }
        if(starty % 4 !=0){
            starty = starty - starty % 4;
        }

        return new Position(startx,starty);
    }

}

