package dungeon.model;

/**
 * A Game of Dungeons. The Game is a nxm grid of caves. These caves are collectively called
 * Dungeon. Caves in a dungeon are capable of holding treasure and are connected with the
 * other caves. Each dungeon has a start and end cave. Players can enter the dungeon at start
 * cave and navigate the dungeon to reach the end cave. They can also pick up treasure from caves.
 * The game provides a snapshot of the dungeon along with details of the player such as player's
 * location, player's treasure, the possible directions in which a player can move from current
 * location and the treasure in the cave the player is in.
 */
public interface TreasureDungeonGame extends DungeonGame {

  /**
   * player picks up the treasure in the cave they are in.
   */
  void pickTreasure();
}
