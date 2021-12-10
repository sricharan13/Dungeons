package dungeon.model;

import java.util.HashMap;
import java.util.Map;

class TreasureCave implements DungeonCave {

  private Map<Treasure, Integer> treasure;
  private final Map<Direction, Location> neighbours;

  // creates a cave with given treasure, neighbours and entrances.
  TreasureCave(Map<Treasure, Integer> treasure, Map<Direction, Location> neighbours) {
    this.treasure = treasure;
    this.neighbours = neighbours;
  }

  // creates a new cave similar to the given cave.
  TreasureCave(DungeonCave cave) {
    this.treasure = cave.getTreasure();
    this.neighbours = cave.getNeighbours();
  }

  @Override
  public boolean isCave() {
    return getEntrances() != 2;
  }

  @Override
  public int getEntrances() {
    return neighbours.size();
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    return new HashMap<>(treasure);
  }

  @Override
  public Map<Direction, Location> getNeighbours() {
    return new HashMap<>(neighbours);
  }

  @Override
  public void setTreasure(Map<Treasure, Integer> treasure) {
    this.treasure = treasure;
  }

  @Override
  public Monster getMonster() {
    throw new UnsupportedOperationException("Treasure Cave does not have Otyugh");
  }

  @Override
  public boolean hasMonster() {
    throw new UnsupportedOperationException("Treasure Cave does not have Otyugh");
  }

  @Override
  public int getArrows() {
    throw new UnsupportedOperationException("Treasure Cave does not have arrows");
  }

  @Override
  public void setArrows(int arrows) {
    throw new UnsupportedOperationException("Treasure Cave does not have arrows");
  }

  @Override
  public String displayCave() {
    return String.format("treasure: %s, neighbours: %s", treasure, neighbours);
  }

  @Override
  public String toString() {
    return "";
  }
}
