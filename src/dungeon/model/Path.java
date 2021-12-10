package dungeon.model;

import java.util.Objects;

// represents a direct path between 2 locations in the dungeon. A path is bi-directional, i.e; path
// from A to B signifies path from B to A and vice versa. A path contains a source location,
// destination location, Direction from source to destination and Direction from destination to
// source.
class Path {

  private final Location src;
  private final Location dest;
  private final Direction directionToDest;
  private final Direction directionToSrc;

  // creates a DungeonPath with the given source, destination and their respective directions.
  Path(Location src, Location dest, Direction directionToDest, Direction directionToSrc) {
    this.src = src;
    this.dest = dest;
    this.directionToDest = directionToDest;
    this.directionToSrc = directionToSrc;
  }

  // returns the source location of this path.
  Location getSrc() {
    return src;
  }

  // returns the destination location of this path.
  Location getDest() {
    return dest;
  }

  // returns the direction to destination from source of this path.
  Direction getDirectionToDest() {
    return directionToDest;
  }

  // returns the direction to source from destination of this path.
  Direction getDirectionToSrc() {
    return directionToSrc;
  }

  @Override
  public String toString() {
    return "[" + src + "<-->" + dest + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Path path = (Path) o;
    return (src.equals(path.src) && dest.equals(path.dest))
            || (src.equals(path.dest) && dest.equals(path.src));
  }

  @Override
  public int hashCode() {
    return Objects.hash(src, dest);
  }
}
