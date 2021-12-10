package dungeon.model;

/**
 * This interface represents a location. Location is denoted in Cartesian coordinates as (x, y).
 * The x and y coordinates of a location are integers.
 */
public interface Location {

  /**
   * Return the x-coordinate of this location.
   * @return x-coordinate.
   */
  int getX();

  /**
   * Return the y-coordinate of this location.
   * @return y-coordinate.
   */
  int getY();

  /**
   * calculates the manhattan distance between this location and the given location.
   * @param location - the location for whcih, manhattan distance should be calculated.
   * @return - manhattan distance.
   */
  int getManhattanDistance(Location location);

  /**
   * Returns the string representation of a location. String is represented in the format (x, y).
   * @return - A string representation of this location.
   */
  @Override
  String toString();

  /**
   * Checks if the given object is equal to this location. Two locations are equal if their x and
   * y coordinates are equal.
   * @param o - the object to be compared for equality.
   * @return - A boolean true if 2 locations are equal, false otherwise.
   */
  @Override
  boolean equals(Object o);

}
