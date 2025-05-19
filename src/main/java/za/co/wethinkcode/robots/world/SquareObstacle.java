package za.co.wethinkcode.robots.world;

import za.co.wethinkcode.robots.robot.Position;

/**
 * Represents a square-shaped obstacle in the robot world.
 * A SquareObstacle has a fixed size and blocks robot movement and path traversal if encountered.
 */
public class SquareObstacle implements Obstacle {

    /** X-coordinate of the bottom-left corner of the square. */
    private final int bottomLeftX;

    /** Y-coordinate of the bottom-left corner of the square. */
    private final int bottomLeftY;

    /** Fixed size (length of one side) of the square. */
    private final int size = 5;

    /** Flags to potentially categorize the type of obstacle (currently unused). */
    private boolean mountain = false;
    private boolean lake = false;
    private boolean bottomlessPit = false;

    /**
     * Constructs a SquareObstacle with the given bottom-left position.
     *
     * @param bottomLeftX the X-coordinate of the bottom-left corner
     * @param bottomLeftY the Y-coordinate of the bottom-left corner
     */
    public SquareObstacle(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
    }

    /**
     * Gets the X-coordinate of the bottom-left corner.
     *
     * @return the bottom-left X-coordinate
     */
    @Override
    public int getBottomLeftX() {
        return bottomLeftX;
    }

    /**
     * Gets the Y-coordinate of the bottom-left corner.
     *
     * @return the bottom-left Y-coordinate
     */
    @Override
    public int getBottomLeftY() {
        return bottomLeftY;
    }

    /**
     * Gets the X-coordinate of the top-right corner.
     *
     * @return the top-right X-coordinate
     */
    public int getTopRightX() {
        return bottomLeftX + size - 1;
    }

    /**
     * Gets the Y-coordinate of the top-right corner.
     *
     * @return the top-right Y-coordinate
     */
    public int getTopRightY() {
        return bottomLeftY + size - 1;
    }

    /**
     * Gets the size (area) of the square obstacle.
     *
     * @return size (area), calculated as side × side
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Checks whether a given position is within this obstacle.
     *
     * @param pos the position to check
     * @return {@code true} if the position is inside the square, otherwise {@code false}
     */
    @Override
    public boolean blocksPosition(Position pos) {
        return pos.getX() >= bottomLeftX && pos.getX() < bottomLeftX + size &&
                pos.getY() >= bottomLeftY && pos.getY() < bottomLeftY + size;
    }

    /**
     * Checks whether this obstacle blocks the straight-line path between two positions.
     * Only vertical or horizontal paths are supported.
     *
     * @param a the starting position
     * @param b the ending position
     * @return {@code true} if any point along the path intersects the obstacle
     */
    @Override
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
     * Indicates whether this obstacle contains the given position.
     * (Currently unimplemented, always returns false.)
     *
     * @param targetPosition the position to check
     * @return {@code false}
     */
    @Override
    public boolean contains(Position targetPosition) {
        return false;
    }

    /**
     * Returns the type of the obstacle.
     * (Currently returns the `mountain` flag for testing; this should be improved.)
     *
     * @return object indicating obstacle type (currently returns a boolean)
     */
    public Object getType() {
        return mountain;
    }
}
