package dungeon.model;

/**
 * A Game of Dungeons. The Game is a nxm grid of caves. These caves are collectively called
 * Dungeon. Caves are connected with other caves in the dungeon. Each dungeon has a start and end
 * cave. Players can enter the dungeon at start cave and navigate the dungeon to reach the end cave.
 * The game provides a snapshot of the dungeon along with details of the player such as player's
 * location, the possible directions in which a player can move from current location. The Game
 * can provide details of the dungeon such as its interconnectivity, wrappability, number of
 * caves in the dungeon.
 */
interface DungeonGame extends ViewDungeonGame {

  /**
   * moves the player in given direction. If the direction is invalid then player stays in same
   * cave.
   * @param direction - the direction which player should move.
   */
  void movePlayer(Direction direction);

  /**
   * resets the game to initial setting.
   */
  void resetGame();
}
