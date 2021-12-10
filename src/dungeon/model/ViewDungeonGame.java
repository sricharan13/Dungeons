package dungeon.model;

import static dungeon.model.Outcome.Smell;
import static dungeon.model.Outcome.Status;

import java.util.List;
import java.util.Map;

/**
 * A readonly interface for the DungeonGame model.
 */
public interface ViewDungeonGame {

  /**
   * returns a copy of the current dungeon state.
   * @return - The dungeon.
   */
  Cave[][] getDungeonState();

  /**
   * returns a list of all the caves/tunnels player visited sor far.
   * @return - a list of locations.
   */
  List<Location> getVisited();

  /**
   * Returns a String representation of the dungeon.
   * @return - A string that represents the dungeon.
   */
  String displayDungeon();

  /**
   * returns the location of the start cave in the dungeon.
   * @return - start caves location.
   */
  Location getStartCave();

  /**
   * returns the location of the end cave in the dungeon.
   * @return - end caves location.
   */
  Location getEndCave();

  /**
   * return the current location of the player in the dungeon.
   * @return - player's current location in dungeon.
   */
  Location getPlayerLocation();

  /**
   * returns the directions in which player can move from current cave.
   * @return - a list of directions in which player can move.
   */
  List<Direction> getPossibleMoves();

  /**
   * A boolean indicating the game's status.
   * @return - true if game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * returns the treasure in the location the player is currently in.
   * @return - the treasure in the location in which player is.
   */
  Map<Treasure, Integer> getCaveTreasure();

  /**
   * returns the details of the players treasure.
   * @return - the treasure player currently has.
   */
  Map<Treasure, Integer> getPlayerTreasure();

  /**
   * Returns the smell perceived by the player in the current location.
   * @return - SMELL - PUNGENT, FAINT or NONE.
   */
  Smell getMonsterSmell();

  /**
   * returns the arrows in the location the player is in.
   * @return - the number of arrows in player's current location.
   */
  int getCaveArrows();

  /**
   * returns the number of arrows the player currently has.
   * @return - number of player's arrows.
   */
  int getPlayerArrows();

  /**
   * returns the status of the game.
   * @return - WIN, LOSS or INPROGRESS.
   */
  Status getStatus();

  /**
   * return the number of rows in dungeon.
   * @return - rows in the dungeon grid.
   */
  int getN();

  /**
   * return the number of columns in dungeon.
   * @return - columns in the dungeon grid.
   */
  int getM();

  /**
   * returns the interconnectivity of dungeon.
   * @return - dungeon interconnectivity.
   */
  int getConnectivity();

  /**
   * returns true if dungeon is wrapped.
   * @return - a flag indicating if dungeon is wrapped.
   */
  boolean isWrap();

  /**
   * returns the percentage of Items in dungeon.
   * @return - item percentage in dungeon.
   */
  int getItemPercent();

  /**
   * returns the number of monster in dungeon.
   * @return - total monsters in dungeon.
   */
  int getMonsters();

  /**
   * returns a flag indicating if the game is running in test mode.
   * @return - true if game is in test mode.
   */
  boolean isTest();
}
