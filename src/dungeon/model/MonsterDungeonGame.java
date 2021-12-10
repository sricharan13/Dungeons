package dungeon.model;

import static dungeon.model.Outcome.Shot;

/**
 * A MonsterDungeonGame is a TreasureDungeonGame with Monsters. The Dungeon in MonsterDungeonGame
 * contains Monsters that will kill Players if they enter the Cave the Monster is in. The
 * Dungeons in MonsterDungeonGame have arrows. Monsters can be identified by their unique smell.
 * A single Monster which is 1 cave away (or) multiple Monster's 2 caves away from player's
 * current position will give a PUNGENT SMELL while a single Monster 2 caves away will give a
 * FAINT SMELL.
 */
public interface MonsterDungeonGame extends TreasureDungeonGame {

  /**
   * Player picks up the arrows in the current location.
   */
  void pickUpArrows();

  /**
   * Player shoots an arrow in the specified direction and distance.
   * @param direction - the direction in which arrow should be shot.
   * @param distance - the distance the arrow should travel.
   * @return - the OUTCOME of shooting the arrow.
   */
  Shot shootArrow(Direction direction, int distance);
}
