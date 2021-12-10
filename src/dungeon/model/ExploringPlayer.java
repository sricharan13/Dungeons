package dungeon.model;

import java.util.HashMap;
import java.util.Map;

// represents a Player in the dungeon. A Player has 2 attributes location and
// treasure. location represents the current Location of player in dungeon. treasure is a
// Map that maps treasure type and the amount/ count of each treasure type.
class ExploringPlayer implements DungeonPlayer {

  private Location location;
  private final Map<Treasure, Integer> treasure;

  // Creates a player with the given location and treasure.
  ExploringPlayer(Location location, Map<Treasure, Integer> treasure) {
    validateLocation(location);
    validateTreasure(treasure);
    this.location = location;
    this.treasure = treasure;
  }

  ExploringPlayer(DungeonPlayer player) {
    this.location = player.getLocation();
    this.treasure = player.getTreasure();
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    return new HashMap<>(treasure);
  }

  @Override
  public void updateTreasure(Map<Treasure, Integer> treasure) {
    validateTreasure(treasure);
    for (Treasure t : treasure.keySet()) {
      this.treasure.put(t, this.treasure.get(t) + treasure.get(t));
    }
  }

  @Override
  public int getArrows() {
    return 0;
  }

  @Override
  public void setArrows(int arrows) {
    // exploring player does not have arrows.
  }

  private void validateTreasure(Map<Treasure, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be null");
    }
  }

  private void validateLocation(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("location cannot be null");
    }
  }
}
