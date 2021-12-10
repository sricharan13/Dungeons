import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;
import static dungeon.model.Outcome.Shot;
import static dungeon.model.Outcome.Shot.HIT;
import static dungeon.model.Outcome.Shot.MISS;
import static dungeon.model.Outcome.Shot.NOEFFECT;
import static dungeon.model.Outcome.Shot.WASTE;
import static dungeon.model.Outcome.Smell.FAINT;
import static dungeon.model.Outcome.Smell.NONE;
import static dungeon.model.Outcome.Smell.PUNGENT;
import static dungeon.model.Outcome.Status.INPROGRESS;
import static dungeon.model.Outcome.Status.LOSS;
import static dungeon.model.Outcome.Status.WIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.model.Cave;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.MonsterHuntDungeonGame;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests MonsterDungeonGame.
 */
public class MonsterDungeonGameTest {

  private MonsterDungeonGame game;

  /**
   * creates a MonsterHuntDungeonGame.
   */
  @Before
  public void setup() {
    game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
  }

  // dungeon created for testing:
  // n: 4, m: 6, connectivity: 3, percent: 30, wrap: false, otyughs: 3, test: true

  //     Expected dungeon layout:
  //     (0, 0) --- (0, 1) --- (0, 2)     (0, 3)     (0, 4) --- (0, 5)
  //                  |                     |          |
  //     (1, 0)     (1, 1) --- (1, 2) --- (1, 3) --- (1, 4) --- (1, 5)
  //       |          |          |                     |          |
  //     (2, 0) --- (2, 1) --- (2, 2)     (2, 3) --- (2, 4)     (2, 5)
  //                  |          |          |                     |
  //     (3, 0) --- (3, 1)     (3, 2) --- (3, 3) --- (3, 4) --- (3, 5)

  // Number of paths: 24 - 1 + 3 = 26
  // Number of treasure caves: (24 - 10 tunnels) * 30% = 14 * (30 / 100) = 4.2 = 5
  // Start Cave: (1, 4)
  // End Cave: (0, 0)
  // Arrow Caves: (0, 0), (0, 2), (0, 4), (0, 5), (2, 1), (2, 3), (3, 1), (3, 4)
  // Otyugh Caves: (0, 0), (0, 3), (3, 0)
  // Treasure Caves: (0, 0), (0, 1), (0, 5), (1, 1), (2, 1)
  // Tunnels: (0, 4), (1, 5), (2, 0), (2, 3), (2, 4), (2, 5), (3, 1), (3, 2), (3, 4), (3, 5)

  /**
   * tests arrows in Game.
   */
  @Test
  public void testArrows() {
    // initial player arrows
    assertEquals(3, game.getPlayerArrows());
    // check number of additional arrows in dungeon. dungeon should have 8 caves with additional
    // arrows.
    // Arrow Caves: (0, 0), (0, 2), (0, 4), (0, 5), (2, 1), (2, 3), (3, 1), (3, 4)
    int count = 0;
    Cave[][] dungeon = game.getDungeonState();
    for (Cave[] caves : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        if (caves[j].getArrows() > 0) {
          count += 1;
        }
      }
    }
    assertEquals(8, count);

    // check if arrows are in expected caves.
    int[] row = new int[] {0, 0, 0, 0, 2, 2, 3, 3};
    int[]column = new int[] {0, 2, 4, 5, 1, 3, 1, 4};
    for (int i = 0; i < 8; i++) {
      assertTrue(dungeon[row[i]][column[i]].getArrows() > 0);
    }

    // check if picking up arrows changes player arrow count.
    game.movePlayer(NORTH);
    assertEquals("(0, 4)", game.getPlayerLocation().toString());
    game.pickUpArrows();
    assertEquals(4, game.getPlayerArrows());
    // check that only copy of the dungeon was returned by game.
    assertEquals(1, dungeon[0][4].getArrows());
    // get dungeon again to check that dungeon updated.
    dungeon = game.getDungeonState();
    // check arrow is not in cave after player picked it up
    assertEquals(0, dungeon[0][4].getArrows());
  }

  /**
   * test for otyughs in dungeon.
   */
  @Test
  public void testOtyughs() {
    // check dungeon can't be created without enough caves.
    try {
      game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 100, true);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("insufficient caves to add 100 Otyughs", iae.getMessage());
    }

    Cave[][] dungeon = game.getDungeonState();

    // Start Cave: (1, 4)
    // check start cave does not have otyugh
    assertFalse(dungeon[1][4].hasMonster());

    // check dungeon has exactly 3 Otyughs
    int count = 0;
    for (Cave[] caves : dungeon) {
      for (Cave cave : caves) {
        if (cave.hasMonster()) {
          count += 1;
        }
      }
    }
    assertEquals(3, count);

    // check expected caves have otyughs.
    // check otyughs are in caves.
    // check end gave has otyugh.
    // Otyugh Caves: (0, 0), (0, 3), (3, 0)
    // End Cave: (0, 0)
    int[] row = new int[] {0, 0, 3};
    int[]column = new int[] {0, 3, 0};
    for (int i = 0; i < 3; i++) {
      assertTrue(dungeon[row[i]][column[i]].hasMonster());
      assertTrue(dungeon[row[i]][column[i]].isCave());
    }
  }

  /**
   * test for otyughs smell in dungeon.
   */
  @Test
  public void testSmell() {

    // The following caves should get a PUNGENT smell:
    // (1, 3), (3, 1), (0, 1)
    // The following caves should get a FAINT smell:
    // (1, 2), (1, 4), (2, 1), (1, 1)
    assertEquals("(1, 4)", game.getPlayerLocation().toString());
    assertEquals(FAINT, game.getMonsterSmell());
    game.movePlayer(WEST);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());
    assertEquals(PUNGENT, game.getMonsterSmell());
    game.movePlayer(WEST);
    assertEquals("(1, 2)", game.getPlayerLocation().toString());
    assertEquals(FAINT, game.getMonsterSmell());
    game.movePlayer(WEST);
    assertEquals("(1, 1)", game.getPlayerLocation().toString());
    assertEquals(FAINT, game.getMonsterSmell());
    game.movePlayer(SOUTH);
    assertEquals("(2, 1)", game.getPlayerLocation().toString());
    assertEquals(FAINT, game.getMonsterSmell());
    game.movePlayer(SOUTH);
    assertEquals("(3, 1)", game.getPlayerLocation().toString());
    assertEquals(PUNGENT, game.getMonsterSmell());
    game.movePlayer(NORTH);
    game.movePlayer(NORTH);
    game.movePlayer(NORTH);
    assertEquals("(0, 1)", game.getPlayerLocation().toString());
    assertEquals(PUNGENT, game.getMonsterSmell());
    game.movePlayer(EAST);
    assertEquals("(0, 2)", game.getPlayerLocation().toString());
    assertEquals(FAINT, game.getMonsterSmell());
  }

  /**
   * test for shooting in dungeon game.
   */
  @Test
  public void testShoot() {
    // WASTE, HIT, NOEFFECT, MISS, curving arrow in tunnel, arrow travels straight in cave.
    game.movePlayer(WEST);
    game.movePlayer(WEST);
    game.movePlayer(WEST);
    assertEquals("(1, 1)", game.getPlayerLocation().toString());
    // arrow travels from (1, 1) -> (2, 1) cave -> (3, 1) tunnel -> (3, 0) otyugh cave
    // direction = SOUTH, distance = 2, outcome should be HIT
    Shot outcome = game.shootArrow(SOUTH, 2);
    assertEquals(HIT, outcome);
    // arrow travels from (1, 1) -> (1, 2) cave -> (1, 3) tunnel -> (1, 4) cave
    // direction = EAST, distance = 3, outcome should be MISS
    outcome = game.shootArrow(EAST, 3);
    assertEquals(MISS, outcome);
    // arrow travels from (1, 1) -> (0, 1) cave and gets wasted.
    // direction = NORTH, distance = 2, outcome should be WASTE
    outcome = game.shootArrow(NORTH, 2);
    assertEquals(WASTE, outcome);
    // out of arrows so outcome should be NOEFFECT
    outcome = game.shootArrow(NORTH, 1);
    assertEquals(NOEFFECT, outcome);
  }

  /**
   * test for loosing - eaten by otyugh.
   */
  @Test
  public void testPlayerLoss() {
    assertEquals("(1, 4)", game.getPlayerLocation().toString());
    game.movePlayer(WEST);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());
    // moving into a dungeon with otyugh and dying.
    game.movePlayer(NORTH);
    assertEquals(LOSS, game.getStatus());
    // assert that game is over.
    assertTrue(game.isGameOver());
    // make actions after game over.
    try {
      game.movePlayer(SOUTH);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot move player", iae.getMessage());
    }
    try {
      game.shootArrow(SOUTH, 1);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot shoot arrow", iae.getMessage());
    }
    try {
      game.pickUpArrows();
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot pickup arrows", iae.getMessage());
    }
    try {
      game.pickTreasure();
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot pick treasure", iae.getMessage());
    }
  }

  /**
   * test killing otyugh and winning.
   */
  @Test
  public void testPlayerWin() {
    assertEquals("(1, 4)", game.getPlayerLocation().toString());
    game.movePlayer(NORTH);
    assertEquals("(0, 4)", game.getPlayerLocation().toString());
    game.pickUpArrows();
    assertEquals(4, game.getPlayerArrows());
    game.movePlayer(SOUTH);
    assertEquals("(1, 4)", game.getPlayerLocation().toString());
    game.movePlayer(WEST);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());
    assertEquals(PUNGENT, game.getMonsterSmell());
    Shot outcome = game.shootArrow(NORTH, 1);
    assertEquals(HIT, outcome);
    outcome = game.shootArrow(NORTH, 1);
    assertEquals(HIT, outcome);
    // moving into a cave with otyugh that's dead.
    assertEquals(NONE, game.getMonsterSmell());
    game.movePlayer(NORTH);
    assertEquals("(0, 3)", game.getPlayerLocation().toString());
    assertEquals(INPROGRESS, game.getStatus());
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(WEST);
    game.movePlayer(NORTH);
    outcome = game.shootArrow(WEST, 1);
    assertEquals(HIT, outcome);
    outcome = game.shootArrow(WEST, 1);
    assertEquals(HIT, outcome);
    // move to end cave
    game.movePlayer(WEST);
    // assert game won
    assertEquals(WIN, game.getStatus());
    // assert that game is over.
    assertTrue(game.isGameOver());
    // make actions after game over.
    try {
      game.movePlayer(SOUTH);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot move player", iae.getMessage());
    }
    try {
      game.shootArrow(SOUTH, 1);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot shoot arrow", iae.getMessage());
    }
    try {
      game.pickUpArrows();
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot pickup arrows", iae.getMessage());
    }
    try {
      game.pickTreasure();
    }
    catch (IllegalArgumentException iae) {
      assertEquals("game over, cannot pick treasure", iae.getMessage());
    }

  }

  /**
   * test for player escaping.
   */
  @Test
  public void testEscaping() {

    game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 4, true);

    // dungeon created for testing:
    // n: 4, m: 6, connectivity: 3, percent: 30, wrap: false, otyughs: 4, test: true

    //     Expected dungeon layout:
    //     (0, 0) --- (0, 1) --- (0, 2)     (0, 3)     (0, 4) --- (0, 5)
    //                  |                     |          |
    //     (1, 0)     (1, 1) --- (1, 2) --- (1, 3) --- (1, 4) --- (1, 5)
    //       |          |          |                     |          |
    //     (2, 0) --- (2, 1) --- (2, 2)     (2, 3) --- (2, 4)     (2, 5)
    //                  |          |          |                     |
    //     (3, 0) --- (3, 1)     (3, 2) --- (3, 3) --- (3, 4) --- (3, 5)

    // Number of paths: 24 - 1 + 3 = 26
    // Number of treasure caves: (24 - 10 tunnels) * 30% = 14 * (30 / 100) = 4.2 = 5
    // Start Cave: (1, 4)
    // End Cave: (0, 0)
    // Arrow Caves: (0, 1), (0, 5), (1, 0), (1, 2), (1, 5), (2, 0), (3, 0), (3, 3)
    // Otyugh Caves: (0, 0), (0, 3), (1, 1), (3, 0)
    // Treasure Caves: (0, 0), (0, 1), (0, 5), (1, 1), (2, 1)
    // Tunnels: (0, 4), (1, 5), (2, 0), (2, 3), (2, 4), (2, 5), (3, 1), (3, 2), (3, 4), (3, 5)

    assertEquals("(1, 4)", game.getPlayerLocation().toString());
    game.movePlayer(WEST);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());
    assertEquals(PUNGENT, game.getMonsterSmell());
    // assert that the monster was hit (damaged otyugh).
    assertEquals(HIT, game.shootArrow(NORTH, 1));

    // move into the cave with damaged otyugh.
    game.movePlayer(NORTH);
    assertEquals("(0, 3)", game.getPlayerLocation().toString());
    // player escapes with 50% probability
    assertEquals(INPROGRESS, game.getStatus());
    // move the player out of danger cave.
    game.movePlayer(SOUTH);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());

    // move the player back to damaged otyugh cave.
    game.movePlayer(NORTH);
    assertEquals("(0, 3)", game.getPlayerLocation().toString());
    // player escapes with 50% probability
    assertEquals(INPROGRESS, game.getStatus());
    // move the player out of danger cave.
    game.movePlayer(SOUTH);
    assertEquals("(1, 3)", game.getPlayerLocation().toString());

    // move the player back to damaged otyugh cave.
    game.movePlayer(NORTH);
    assertEquals("(0, 3)", game.getPlayerLocation().toString());
    // player dies.
    assertEquals(LOSS, game.getStatus());
  }

}