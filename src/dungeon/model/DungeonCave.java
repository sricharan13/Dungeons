package dungeon.model;

import java.util.Map;

// A package private interface that extends Cave. Represents a cave in the dungeon Game.
interface DungeonCave extends Cave {

  /**
   * sets cave treasure to given treasure.
   * @param treasure - treasure to set to cave.
   */
  void setTreasure(Map<Treasure, Integer> treasure);

  /**
   * returns the monster in dungeon.
   * @return - monster in dungeon.
   */
  Monster getMonster();

  /**
   * sets arrows in cave to given arrows..
   * @param arrows - arrows to set.
   */
  void setArrows(int arrows);

  /**
   * Displays the cave.
   */
  String displayCave();
}

