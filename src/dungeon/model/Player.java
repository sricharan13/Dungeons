package dungeon.model;

import java.util.Map;

interface Player {

  /**
   * returns a copy of the players treasure.
   * @return - player's treasure and count.
   */
  Map<Treasure, Integer> getTreasure();

  /**
   * returns the player's current location.
   * @return - player's location.
   */
  Location getLocation();

  /**
   * returns the arrows a player has.
   * @return - count of player's arrows.
   */
  int getArrows();
}
