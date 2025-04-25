package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

import java.util.List;

public class SquareObstacle implements Obstacle {

    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int size = 5;
    private boolean mountain = false;
    private boolean lake = false;
    private boolean bottomlessPit = false;



    public SquareObstacle(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;

    }

    @Override
    public int getBottomLeftX() {
        return bottomLeftX;
    }

    @Override
    public int getBottomLeftY() {
        return bottomLeftY;
    }

    public int getTopRightX() {
        return bottomLeftX + size - 1;
    }

    public int getTopRightY() {
        return bottomLeftY + size - 1;
    }

//    public boolean getMountain(){
//        return mountain;
//    }
//
//    public boolean getLake(){
//        return lake;
//    }
//
//    public boolean getBottomlessPit(){
//        return bottomlessPit;
//    }

    @Override
    public int getSize() {
        return size;
    }

    //    Checks if anything moves within its boundaries
    @Override
    public boolean blocksPosition(Position pos) {
        //After or before x/y-axis
        return pos.getX() >= bottomLeftX && pos.getX() < bottomLeftX + size &&
                pos.getY() >= bottomLeftY && pos.getY() < bottomLeftY + size;
    }

    /**
     * Checks if this obstacle blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *
     * @param a           first position
     * @param b           second position

     * @return `true` if this obstacle is in the way
     */

    @Override
    public boolean blocksPath(Position a, Position b) {
        int dx = Integer.compare(b.getX(), a.getX());
        int dy = Integer.compare(b.getY(), a.getY());

        int x = a.getX();
        int y = a.getY();

        while (x != b.getX() || y != b.getY()) {
            Position current = new Position(x, y);

            // Check for collisions with square obstacle
            if (blocksPosition(current)) {
                return true;
            }

            // Check for collisions with other robots
//            for (Robot r : allRobots) {
//                if (!r.equals(movingRobot) && r.getPosition().equals(current)) {
//                    return true;
//                }
//            }

            if (x != b.getX()) x += dx;
            if (y != b.getY()) y += dy;
        }

        // Check destination
//        for (Robot r : allRobots) {
//            if (!r.equals(movingRobot) && r.getPosition().equals(b)) {
//                return true;
//            }
//        }

        return blocksPosition(b);
    }

//    @Override
//    public Boolean getTypeObsticle() {
//        return false;
//    }

//    public Object getType() {
//        return mountain;
//    }


//========
}
