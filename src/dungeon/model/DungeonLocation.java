package dungeon.model;

import java.util.Objects;

/**
 * represents a location in the dungeon, where a dungeon is a nxm grid.
 */
public class DungeonLocation implements Location {

  private final int x;
  private final int y;

  /**
   * constructs a new location with given coordinates.
   */
  public DungeonLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public int getManhattanDistance(Location location) {
    return Math.abs(x - location.getX()) + Math.abs(y - location.getY());
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DungeonLocation location = (DungeonLocation) o;
    return getX() == location.getX() && getY() == location.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY());
  }
}


