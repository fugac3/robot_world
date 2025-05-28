package za.co.wethinkcode.robots.obstacles;

import za.co.wethinkcode.robots.robot.Position;

/**
 * Represents a rectangular obstacle in the robot world.
 * rectangle that can block robot movement and pathfinding.
 */
public abstract class Obstacle {

    public abstract String getType();

    /** The X-coordinate of the bottom-left corner of the rectangle. */
    private final int bottomLeftX;

    /** The Y-coordinate of the bottom-left corner of the rectangle. */
    private final int bottomLeftY;

    //default for tests 7,5
    /** The fixed height of the rectangle. */
    private final int height = 2;

    /** The fixed width of the rectangle. */
    private final int width = 2;

    /**
     * Constructs a RectangleObstacle with a given bottom-left corner.
     *
     * @param bottomLeftX the X-coordinate of the bottom-left corner
     * @param bottomLeftY the Y-coordinate of the bottom-left corner
     */
    public Obstacle(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
    }

    /**
     * Gets the X-coordinate of the bottom-left corner.
     *
     * @return the X-coordinate
     */
    
    public int getBottomLeftX() {
        return bottomLeftX;
    }

    /**
     * Gets the Y-coordinate of the bottom-left corner.
     *
     * @return the Y-coordinate
     */
    
    public int getBottomLeftY() {
        return bottomLeftY;
    }

    /**
     * Gets the X-coordinate of the top-right corner.
     *
     * @return the top-right X-coordinate
     */
    public int getTopRightX() {
        return bottomLeftX + width - 1;
    }

    /**
     * Gets the Y-coordinate of the top-right corner.
     *
     * @return the top-right Y-coordinate
     */
    public int getTopRightY() {
        return bottomLeftY + height - 1;
    }

    public Position ObstacleBottomLeft() {
        return new Position(getBottomLeftX(),getBottomLeftY());
    }

    public Position ObstacleTopRight() {
        return new Position(getTopRightX(),getTopRightY());
    }

    /**
     * Checks if a given position is within the obstacle.
     *
     * @param pos the position to check
     * @return {@code true} if the position is inside the obstacle, {@code false} otherwise
     */
    
    public boolean blocksPosition(Position pos) {
        return pos.getX() >= bottomLeftX && pos.getX() < bottomLeftX + width &&
                pos.getY() >= bottomLeftY && pos.getY() < bottomLeftY + height;
    }

    /**
     * Checks whether this obstacle blocks the straight-line path between two positions.
     * Only horizontal or vertical paths are supported.
     *
     * @param a the starting position
     * @param b the ending position
     * @return {@code true} if any part of the path crosses the obstacle
     */
    
    public boolean blocksPath(Position a, Position b) {
        int dx = Integer.compare(b.getX(), a.getX());
        int dy = Integer.compare(b.getY(), a.getY());

        int x = a.getX();
        int y = a.getY();

        while (x != b.getX() || y != b.getY()) {
            Position current = new Position(x, y);
            if (blocksPosition(current)) {
                return true;
            }
            if (x != b.getX()) x += dx;
            if (y != b.getY()) y += dy;
        }
        return blocksPosition(b);
    }

    /**
     * Checks whether the obstacle contains a given position.
     * Currently always returns {@code false}.
     *
     * @param targetPosition the position to check
     * @return {@code false}
     */
    
    public boolean contains(Position targetPosition) {
        return false;
    }

    /**
     * Returns the size of the obstacle (area = width × height).
     *
     * @return the total number of grid cells covered by this obstacle
     */
    
    public int getSize() {
        return width * height;
    }
}
