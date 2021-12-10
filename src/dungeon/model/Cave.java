package dungeon.model;

import java.util.Map;

/**
 * Represents a cave. A Cave can have many entrances but is considered a tunnel if it has only 2
 * entrances. A tunnel (cave) acts as a passage between 2 caves. Each cave (not tunnel) is capable
 * of holding treasure. It can hold treasure of various types such as DIAMOND, RUBY and
 * SAPPHIRE and can hold any quantity of each type of treasure.
 */
public interface Cave {

  /**
   * checks and returns a boolean indicating if this cave is a cave or tunnel.
   * @return - true if this cave is a cave, false otherwise.
   */
  boolean isCave();

  /**
   * Returns the number of entrances of this cave.
   * @return - a count of number of entrances of this cave.
   */
  int getEntrances();

  /**
   * Returns the treasure in this cave.
   * @return - A Map of each treasure type and their respective quantities.
   */
  Map<Treasure, Integer> getTreasure();

  /**
   * returns the neighbours of this cave. If the cave does not a neighbour in any cardinal
   * direction then it is its own neighbour.
   * @return - neighbours of this cave.
   */
  Map<Direction, Location> getNeighbours();

  /**
   * checks if cave has monster.
   * @return - true if cave has monster.
   */
  boolean hasMonster();

  /**
   * returns the arrows in cave.
   * @return - arrows in cave.
   */
  int getArrows();
}
