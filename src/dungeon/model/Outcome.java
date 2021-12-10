package dungeon.model;

/**
 * Represents different outcomes of the DungeonGame.
 */
public class Outcome {

  /**
   * Represents game status. WIN - if player wins the game, LOSS - if player looses the game and
   * INPROGRESS if game is not over.
   */
  public enum Status {
    INPROGRESS, WIN, LOSS
  }

  /**
   * Represents Monster's Smell.
   */
  public enum Smell {
    PUNGENT, FAINT, NONE
  }

  /**
   * Represents the Outcome of Shoot. HIT - if arrow hits monster, MISS - if there is no monster in
   * the destination cave, WASTE - if arrow hits a wall, NOEFFECT - out of arrows.
   */
  public enum Shot {
    HIT, MISS, WASTE, NOEFFECT
  }
}
