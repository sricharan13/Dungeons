package dungeon.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents Treasure in the dungeon. Treasure can be DIAMOND, RUBY or SAPPHIRE.
 */
public enum Treasure {

  DIAMOND, SAPPHIRE, RUBY;

  // package private static method that creates and returns a bag of empty Treasure. The bag
  // contains all the treasures in the enum. Acts as a placeholder for no treasure.
  static Map<Treasure, Integer> emptyTreasure() {
    Map<Treasure, Integer> emptyTreasure = new HashMap<>();
    for (Treasure treasure : Treasure.values()) {
      emptyTreasure.put(treasure, 0);
    }
    return emptyTreasure;
  }

  // package private static method that creates and returns a bag of Treasure. The bag
  // contains all the treasures represented by the enum. Each type of treasure is of count 2.
  static Map<Treasure, Integer> treasure() {
    Map<Treasure, Integer> treasure = new HashMap<>();
    for (Treasure t : Treasure.values()) {
      treasure.put(t, 2);
    }
    return treasure;
  }
}
