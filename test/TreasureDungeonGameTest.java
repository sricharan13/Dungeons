import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;
import static dungeon.model.Treasure.DIAMOND;
import static dungeon.model.Treasure.RUBY;
import static dungeon.model.Treasure.SAPPHIRE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import dungeon.model.Cave;
import dungeon.model.Direction;
import dungeon.model.Location;
import dungeon.model.Treasure;
import dungeon.model.TreasureDungeonGame;
import dungeon.model.TreasureHuntDungeonGame;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Tests the dungeon game.
 */
public class TreasureDungeonGameTest {

  TreasureDungeonGame dungeonGame;

  private void initializeGame(int n, int m, int connectivity, int percent, boolean wrap) {
    dungeonGame = new TreasureHuntDungeonGame(n, m, connectivity, percent, wrap, true);
  }

  private Location getPlayerLocation() {
    return dungeonGame.getPlayerLocation();
  }

  private Map<Treasure, Integer> getPlayerTreasure() {
    return dungeonGame.getPlayerTreasure();
  }

  private void pickTreasure() {
    dungeonGame.pickTreasure();
  }

  private void movePlayer(Direction direction) {
    dungeonGame.movePlayer(direction);
  }

  private boolean isGameOver() {
    return dungeonGame.isGameOver();
  }

  private List<Direction> getPossibleMoves() {
    return dungeonGame.getPossibleMoves();
  }

  private Map<Treasure, Integer> getCaveTreasure() {
    return dungeonGame.getCaveTreasure();
  }

  private Location getEndCave() {
    return dungeonGame.getEndCave();
  }

  private Location getStartCave() {
    return dungeonGame.getStartCave();
  }

  /**
   * test creation of dungeon.
   */
  @Test
  public void testDungeonCreation() {
    // tests illegal dimensions of dungeon.
    try {
      initializeGame(-1, 4, 3, 10, true);
      fail("test should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Unexpected specifications", e.getMessage());
    }
    // tests illegal dimensions of dungeon.
    try {
      initializeGame(4, -1, 3, 10, true);
      fail("test should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Unexpected specifications", e.getMessage());
    }
    // tests illegal interconnectivity of dungeon.
    try {
      initializeGame(4, 4, -1, 10, true);
      fail("test should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Unexpected specifications", e.getMessage());
    }
    // tests illegal interconnectivity of dungeon.
    try {
      initializeGame(4, 4, 2, -1, true);
      fail("test should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Unexpected specifications", e.getMessage());
    }
    // tests the minimum distance rule between start and end cave.
    try {
      initializeGame(1, 1, 0, 0, false);
      fail("test should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough caves in dungeon", e.getMessage());
    }
  }

  /**
   * tests a wrapping dungeon.
   */
  @Test
  public void testWrapDungeon() {
    initializeGame(4, 6, 8, 20, true);

    // Expected dungeon layout:
    //       |          |                                |          |
    // --- (0, 0) --- (0, 1) --- (0, 2)     (0, 3) --- (0, 4)     (0, 5) ---
    //                                        |          |          |
    // --- (1, 0)     (1, 1) --- (1, 2)     (1, 3) --- (1, 4)     (1, 5) ---
    //       |          |                     |          |          |
    // --- (2, 0)     (2, 1) --- (2, 2) --- (2, 3)     (2, 4) --- (2, 5) ---
    //       |                                |          |
    //     (3, 0) --- (3, 1) --- (3, 2) --- (3, 3) --- (3, 4) --- (3, 5)
    //       |          |                                |          |

    // Number of Paths: 24 - 1 + 8 = 31
    // Start Cave: (0, 5)
    // End Cave: (0, 0)
    // Treasure Caves: (0, 0), (0, 4), (1, 2), (1, 5)
    // tunnel: (0, 3), (1, 0), (1, 1), (2, 1), (2, 2), (3, 2), (3, 5)
    // treasure caves: (24 - 7 tunnels) * 20% = 17 * (20 / 100) = 3.4 = 4

    Cave[][] dungeon = dungeonGame.getDungeonState();

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    // check number of paths(interconnectivity) in dungeon are correct.
    int paths = 0;
    for (Cave[] value : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        paths += value[j].getEntrances();
      }
    }
    assertEquals(31, paths / 2);

    Map<Treasure, Integer> treasureBag;

    // check if 20% of the caves have treasure.
    int count = 0;
    for (Cave[] caves : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        treasureBag = caves[j].getTreasure();
        if (treasureBag.values().toString().equals("[2, 2, 2]")) {
          count += 1;
        }
      }
    }
    assertEquals(4, count);

    // test treasure is in the expected caves and these caves are not tunnels.
    // also test that there are 3 types of treasure in the dungeon.
    // Treasure Caves: (0, 0), (0, 4), (1, 2), (1, 5)
    int[] row = new int[] {0, 0, 1, 1};
    int[]column = new int[] {0, 4, 2, 5};
    for (int index = 0 ; index < 4; index++) {
      assertNotEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertTrue(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      assertTrue(treasureBag.containsKey(DIAMOND));
      assertTrue(treasureBag.containsKey(SAPPHIRE));
      assertTrue(treasureBag.containsKey(RUBY));
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(2, treasureBag.get(treasure).intValue());
      }
    }

    // test tunnels - tunnels have 2 entrances and tunnels don't have treasure and tunnel is not
    // a cave.
    // tunnels: (0, 3), (1, 0), (1, 1), (2, 1), (2, 2), (3, 2), (3, 5)
    row = new int[] {0, 1, 1, 2, 2, 3, 3};
    column = new int[] {3, 0, 1, 1, 2, 2, 5};
    for (int index = 0 ; index < 7; index++) {
      assertEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertFalse(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(0, treasureBag.get(treasure).intValue());
      }
    }

    Location location;

    // test to check start cave of dungeon is (0, 5).
    location = getStartCave();
    assertEquals("(0, 5)", location.toString());

    // test to check if player's initial location is (0, 5).
    location = getPlayerLocation();
    assertEquals("(0, 5)", location.toString());

    // test to check end cave of dungeon is (0, 0).
    location = getEndCave();
    assertEquals("(0, 0)", location.toString());

    // test to check distance between start and end cave is greater than or equal to 5
    int x1 = getStartCave().getX();
    int y1 = getStartCave().getY();
    int x2 = getEndCave().getX();
    int y2 = getEndCave().getY();
    assertTrue(Math.abs(x2 - x1) + Math.abs(y2 - y1) >= 5);

    // test to check player initially has 0 treasure.
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check cave (0, 5) does not have treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check picking treasure in a cave with no treasure does not alter player's treasure.
    pickTreasure();
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    List<Direction> expectedDirections;

    // test to check if player's possible moves are correct.
    expectedDirections = Arrays.asList(NORTH, SOUTH, EAST);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    // test to check moving a player in wrong direction does not change player's location.
    movePlayer(WEST);
    assertEquals("(0, 5)", getPlayerLocation().toString());

    // test to check moving a player in correct direction changes player's location.
    movePlayer(SOUTH);
    assertEquals("(1, 5)", getPlayerLocation().toString());

    // test to check if treasure cave has treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    // check if player treasure is zero.
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check picking up treasure changes players treasure.
    pickTreasure();
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    // test to check there is no treasure in cave after player picked up treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check if player's possible moves are correct.
    expectedDirections = Arrays.asList(NORTH, SOUTH, EAST);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    movePlayer(SOUTH);
    movePlayer(EAST);
    movePlayer(NORTH);

    // test to check moving a player in correct direction changes player's location.
    movePlayer(EAST);
    assertEquals("(1, 0)", getPlayerLocation().toString());

    // test to check if tunnel has treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check if wrapping caves work correctly.
    movePlayer(SOUTH);
    assertEquals("(2, 0)", getPlayerLocation().toString());

    movePlayer(WEST);
    movePlayer(NORTH);

    // test to check moving a player in correct direction changes player's location.
    movePlayer(NORTH);
    assertEquals("(0, 5)", getPlayerLocation().toString());

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    movePlayer(EAST);
    assertTrue(dungeonGame.isGameOver());
    assertEquals("(0, 0)", getPlayerLocation().toString());

    // test to see trying to move player does not work after game ends.
    try {
      movePlayer(EAST);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("game over, cannot move player", e.getMessage());
    }
  }

  /**
   * tests a wrapping dungeon.
   */
  @Test
  public void testNonWrapDungeon() {
    initializeGame(4, 6, 3, 30, false);

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
    // Normal Caves: (0, 2), (0, 3), (1, 0), (1, 2), (1, 3), (1, 4), (2, 2), (3, 0), (3, 3)
    // Treasure Caves: (0, 0), (0, 1), (0, 5), (1, 1), (2, 1)
    // Tunnels: (0, 4), (1, 5), (2, 0), (2, 3), (2, 4), (2, 5), (3, 1), (3, 2), (3, 4), (3, 5)

    Cave[][] dungeon = dungeonGame.getDungeonState();

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    // check number of paths in dungeon are correct.
    int paths = 0;
    for (Cave[] value : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        paths += value[j].getEntrances();
      }
    }
    assertEquals(26, paths / 2);

    Map<Treasure, Integer> treasureBag;

    // check if 30% of the caves gave treasure.
    int count = 0;
    for (Cave[] caves : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        treasureBag = caves[j].getTreasure();
        if (treasureBag.values().toString().equals("[2, 2, 2]")) {
          count += 1;
        }
      }
    }
    assertEquals(5, count);

    // test treasure is in the expected caves and these caves are not tunnels.
    // Treasure Caves: (0, 0), (0, 1), (0, 5), (1, 1), (2, 1)
    int[] row = new int[] {0, 0, 0, 1, 2};
    int[] column = new int[] {0, 1, 5, 1, 1};
    for (int index = 0 ; index < 4; index++) {
      assertTrue(dungeon[row[index]][column[index]].isCave());
      assertNotEquals(2, dungeon[row[index]][column[index]].getEntrances());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      assertTrue(treasureBag.containsKey(DIAMOND));
      assertTrue(treasureBag.containsKey(SAPPHIRE));
      assertTrue(treasureBag.containsKey(RUBY));
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(2, treasureBag.get(treasure).intValue());
      }
    }

    // test all remaining caves are caves and that they do not have treasure.
    // Normal Caves: (0, 2), (0, 3), (1, 0), (1, 2), (1, 3), (1, 4), (2, 2), (3, 0), (3, 3)
    row = new int[] {0, 0, 1, 1, 1, 1, 2, 3, 3};
    column = new int[] {2, 3, 0, 2, 3, 4, 2, 0, 3};
    for (int index = 0 ; index < 9; index++) {
      assertNotEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertTrue(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(0, treasureBag.get(treasure).intValue());
      }
    }

    // test tunnels - tunnels have 2 entrances and tunnels don't have treasure and tunnel is not
    // a cave.
    // Tunnels: (0, 4), (1, 5), (2, 0), (2, 3), (2, 4), (2, 5), (3, 1), (3, 2), (3, 4), (3, 5)
    row = new int[] {0, 1, 2, 2, 2, 2, 3, 3, 3, 3};
    column = new int[] {4, 5, 0, 3, 4, 5, 1, 2, 4, 5};
    for (int index = 0 ; index < 10; index++) {
      assertEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertFalse(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(0, treasureBag.get(treasure).intValue());
      }
    }

    Location location;

    // test to check start cave of dungeon is (1, 4).
    location = getStartCave();
    assertEquals("(1, 4)", location.toString());

    // test to check if player's initial location is (1, 4).
    location = getPlayerLocation();
    assertEquals("(1, 4)", location.toString());

    // test to check end cave of dungeon is (0, 0).
    location = getEndCave();
    assertEquals("(0, 0)", location.toString());

    // test to check distance between start and end cave is greater than or equal to 5
    int x1 = getStartCave().getX();
    int y1 = getStartCave().getY();
    int x2 = getEndCave().getX();
    int y2 = getEndCave().getY();
    assertTrue(Math.abs(x2 - x1) + Math.abs(y2 - y1) >= 5);

    // test to check player initially has 0 treasure.
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check cave (0, 0) does not have treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    //test to check picking treasure in a cave with no treasure does not alter player's treasure.
    pickTreasure();
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    List<Direction> expectedDirections;

    // test to check if player's possible moves are correct.
    expectedDirections = List.of(EAST, WEST, NORTH, SOUTH);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    // test to check moving a player in correct direction changes player's location.
    movePlayer(NORTH);
    assertEquals("(0, 4)", getPlayerLocation().toString());

    movePlayer(EAST);
    assertEquals("(0, 5)", getPlayerLocation().toString());

    // test to check if player's possible moves are correct.
    expectedDirections = List.of(WEST);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    // test to check if treasure cave has treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    // test to check moving a player in correct direction changes player's location.
    movePlayer(WEST);
    assertEquals("(0, 4)", getPlayerLocation().toString());

    // test to check moving a player in correct direction changes player's location.
    movePlayer(SOUTH);
    assertEquals("(1, 4)", getPlayerLocation().toString());

    movePlayer(WEST);
    movePlayer(WEST);
    movePlayer(WEST);
    // test to check if treasure cave has treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    // check if player treasure is zero.
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check picking up treasure changes players treasure.
    pickTreasure();
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    // test to check there is no treasure in cave after player picked up treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }


    // test to check if game ends after reaching end cave.
    movePlayer(NORTH);

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    movePlayer(WEST);
    assertTrue(dungeonGame.isGameOver());
    assertEquals("(0, 0)", getPlayerLocation().toString());

    // test to see trying to move player does not work after game ends.
    try {
      movePlayer(EAST);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("game over, cannot move player", e.getMessage());
    }
  }

  /**
   * tests dungeon with 0 interconnectivity.
   */
  @Test
  public void testZeroInterconnectivity() {
    initializeGame(4, 4, 0, 40, false);

    //     Expected dungeon layout:
    //     (0, 0) --- (0, 1) --- (0, 2) --- (0, 3)
    //       |          |
    //     (1, 0)     (1, 1) --- (1, 2) --- (1, 3)
    //                  |                     |
    //     (2, 0)     (2, 1) --- (2, 2)     (2, 3)
    //       |                     |
    //     (3, 0) --- (3, 1) --- (3, 2) --- (3, 3)

    // Number of paths: 16 - 1 + 0 = 15
    // Number of treasure caves: (16 caves - 8 tunnels) * 40% = 8 * (40 / 100) = 3.2 = 3
    // Start Cave: (3, 3)
    // End Cave: (1, 0)
    // Normal Caves: (0, 3), (1, 1), (2, 0), (3, 2)
    // Treasure Caves: (0, 1), (1, 1), (2, 3), (3, 3)
    // Tunnels: (0, 0), (0, 2), (1, 2), (1, 3), (2, 1), (2, 2), (3, 0), (3, 1)

    Cave[][] dungeon = dungeonGame.getDungeonState();

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    // check number of paths in dungeon are correct.
    int paths = 0;
    for (Cave[] value : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        paths += value[j].getEntrances();
      }
    }
    assertEquals(15, paths / 2);

    Map<Treasure, Integer> treasureBag;

    // check if 30% of the caves gave treasure.
    int count = 0;
    for (Cave[] caves : dungeon) {
      for (int j = 0; j < dungeon[0].length; j++) {
        treasureBag = caves[j].getTreasure();
        if (treasureBag.values().toString().equals("[2, 2, 2]")) {
          count += 1;
        }
      }
    }
    assertEquals(4, count);

    // test treasure is in the expected caves and these caves are not tunnels.
    // Treasure Caves: (0, 1), (1, 0), (2, 3), (3, 3)
    int[] row = new int[] {0, 1, 2, 3};
    int[] column = new int[] {1, 1, 3, 3};
    for (int index = 0 ; index < 3; index++) {
      assertTrue(dungeon[row[index]][column[index]].isCave());
      assertNotEquals(2, dungeon[row[index]][column[index]].getEntrances());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      assertTrue(treasureBag.containsKey(DIAMOND));
      assertTrue(treasureBag.containsKey(SAPPHIRE));
      assertTrue(treasureBag.containsKey(RUBY));
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(2, treasureBag.get(treasure).intValue());
      }
    }

    // test all remaining caves are caves and that they do not have treasure.
    // Normal Caves: (0, 3), (1, 1), (2, 0), (3, 2)
    row = new int[] {0, 1, 2, 3};
    column = new int[] {3, 0, 0, 2};
    for (int index = 0 ; index < 3; index++) {
      assertNotEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertTrue(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(0, treasureBag.get(treasure).intValue());
      }
    }

    // test tunnels - tunnels have 2 entrances and tunnels don't have treasure and tunnel is not
    // a cave.
    // Tunnels: (0, 0), (0, 2), (1, 2), (1, 3), (2, 1), (2, 2), (3, 0), (3, 1)
    row = new int[] {0, 0, 1, 1, 2, 2, 3, 3};
    column = new int[] {0, 2, 2, 3, 1, 2, 0, 1};
    for (int index = 0 ; index < 8; index++) {
      assertEquals(2, dungeon[row[index]][column[index]].getEntrances());
      assertFalse(dungeon[row[index]][column[index]].isCave());
      treasureBag = dungeon[row[index]][column[index]].getTreasure();
      for (Treasure treasure : treasureBag.keySet()) {
        assertEquals(0, treasureBag.get(treasure).intValue());
      }
    }

    Location location;

    // test to check start cave of dungeon is (3, 3).
    location = getStartCave();
    assertEquals("(3, 3)", location.toString());

    // test to check if player's initial location is (2, 0).
    location = getPlayerLocation();
    assertEquals("(3, 3)", location.toString());

    // test to check end cave of dungeon is (1, 0).
    location = getEndCave();
    assertEquals("(1, 0)", location.toString());

    // test to check distance between start and end cave is greater than or equal to 5
    int x1 = getStartCave().getX();
    int y1 = getStartCave().getY();
    int x2 = getEndCave().getX();
    int y2 = getEndCave().getY();
    assertTrue(Math.abs(x2 - x1) + Math.abs(y2 - y1) >= 5);

    // test to check player initially has 0 treasure.
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check cave (3, 3) has treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    //test to check picking treasure in a cave alters player's treasure.
    pickTreasure();
    treasureBag = getPlayerTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(2, treasureBag.get(treasure).intValue());
    }

    List<Direction> expectedDirections;

    // test to check if player's possible moves are correct.
    expectedDirections = List.of(WEST);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    // test to check moving a player in wrong direction does not change player's location.
    movePlayer(SOUTH);
    assertEquals("(3, 3)", getPlayerLocation().toString());

    // test to check moving a player in correct direction changes player's location.
    movePlayer(WEST);
    assertEquals("(3, 2)", getPlayerLocation().toString());

    // test to check moving a player in correct direction changes player's location.
    movePlayer(NORTH);
    assertEquals("(2, 2)", getPlayerLocation().toString());

    // test to check if cave does not have treasure.
    treasureBag = getCaveTreasure();
    for (Treasure treasure : Treasure.values()) {
      assertEquals(0, treasureBag.get(treasure).intValue());
    }

    // test to check if player's possible moves are correct.
    expectedDirections = Arrays.asList(SOUTH, WEST);
    for (Direction direction : getPossibleMoves()) {
      assertTrue(expectedDirections.contains(direction));
    }

    // test to check moving a player in correct direction changes player's location.
    movePlayer(WEST);
    assertEquals("(2, 1)", getPlayerLocation().toString());

    // test to check moving a player in correct direction changes player's location.
    movePlayer(NORTH);
    assertEquals("(1, 1)", getPlayerLocation().toString());
    movePlayer(NORTH);
    assertEquals("(0, 1)", getPlayerLocation().toString());
    movePlayer(WEST);
    assertEquals("(0, 0)", getPlayerLocation().toString());

    // assert game is not over
    assertFalse(dungeonGame.isGameOver());

    movePlayer(SOUTH);
    assertEquals("(1, 0)", getPlayerLocation().toString());

    // test to check if game ends after reaching end cave.
    assertTrue(dungeonGame.isGameOver());
    assertEquals("(1, 0)", getPlayerLocation().toString());

    // test to see trying to move player does not work after game ends.
    try {
      movePlayer(NORTH);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("game over, cannot move player", e.getMessage());
    }
  }
}