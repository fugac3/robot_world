package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

public class RectangleObstacle implements Obstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int height = 5;
    private final int width = 7;

    public RectangleObstacle(int bottomLeftX, int bottomLeftY) {
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
        return bottomLeftX + width - 1;
    }

    public int getTopRightY() {
        return bottomLeftY + height - 1;
    }

    //    Checks if anything moves within its boundaries
    @Override
    public boolean blocksPosition(Position pos) {
        //After or before x/y-axis
        return pos.getX() >= bottomLeftX && pos.getX() < bottomLeftX + width &&
                pos.getY() >= bottomLeftY && pos.getY() < bottomLeftY + height;
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
            if (x != b.getX()) x += dx;
            if (y != b.getY()) y += dy;
        }
        return blocksPosition(b);
    }

    @Override
    public boolean contains(Position targetPosition) {
        return false;
    }


    @Override
    public int getSize() {
//        Random random = new Random();
//
//        int X = 10;
//        int Y = 10;
//
//        while (true) {
//            int height = random.nextInt(X);
//            int width = random.nextInt(Y - Y + 1) + Y;
//
//            if (height == width){
//                return size = hw
//            }
//        }


        return width*height;
    }





//========
}
