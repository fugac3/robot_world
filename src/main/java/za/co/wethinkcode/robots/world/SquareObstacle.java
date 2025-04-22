package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

public class SquareObstacle implements Obstacle {

    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int size = 5;

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

    @Override
    public boolean blocksPath(Position a, Position b) {
        // simulate movement from a to b and check if any point intersects the obstacle
        // d(x/y) will be 1 if moving right, -1 if moving left, 0 if not moving in vertices
        // checks all comparison conditions >,<,=
        int dx = Integer.compare(b.getX(), a.getX());
        int dy = Integer.compare(b.getY(), a.getY());

        int x = a.getX();
        int y = a.getY();

        // Walk from start to just before the destination
        while (x != b.getX() || y != b.getY()) {
            if (blocksPosition(new Position(x, y))) {
                return true;
            }
            x += dx;
            y += dy;
        }

        // Also check the final position
        return blocksPosition(b);
    }

//========
}
