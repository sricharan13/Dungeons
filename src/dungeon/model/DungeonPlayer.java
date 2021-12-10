package dungeon.model;

import java.util.Map;

// Provides all the functionalities of a player in DungeonGame.
interface DungeonPlayer extends Player {

  // updates the player's location.
  void setLocation(Location location);

  // updates the players treasure by adding the given treasure with the treasure the player
  // already has.
  void updateTreasure(Map<Treasure, Integer> treasure);

  // sets the number of arrows a player has.
  void setArrows(int arrows);
}
