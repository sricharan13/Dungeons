package dungeon.model;

/**
 * Represents a Direction. Direction can be NORTH, SOUTH, EAST or WEST.
 */
public enum Direction {

  NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);

  private final int x;
  private final int y;

  Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  Direction opposite() {
    switch (this) {
      case NORTH: {
        return SOUTH;
      }
      case SOUTH: {
        return NORTH;
      }
      case EAST: {
        return WEST;
      }
      case WEST: {
        return EAST;
      }
      default:
        throw new IllegalArgumentException("Unexpected value: " + this);
    }
  }

}
