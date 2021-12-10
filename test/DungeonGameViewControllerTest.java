import static org.junit.Assert.assertEquals;

import dungeon.controller.DungeonGameViewController;
import dungeon.controller.MonsterDungeonGameViewController;
import dungeon.model.Direction;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.MonsterHuntDungeonGame;
import dungeon.model.Treasure;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests DungeonGameViewController.
 */
public class DungeonGameViewControllerTest {

  private DungeonGameViewController controller;
  private StringBuilder log;

  /**
   * initializes all fields.
   */
  @Before
  public void setup() {
    log = new StringBuilder();
    MockModel model = new MockModel(log);
    MockView view = new MockView(log);
    controller = new MonsterDungeonGameViewController(model, view);
    controller.playGame();
  }

  /**
   * tests move shoot and pick with the mock model and view.
   */
  @Test
  public void MockTestMoveShootPick() {
    // illegal actions (anything other than move, shoot, pick), move in correct direction,
    // shoot with correct distance and direction,shoot with incorrect direction, shoot with
    // correct direction and incorrect distance.
    String[] inputs = {"north", "direction", "south", "other direction", "east", "west", "shoot",
                       "pick", "other key event", "north", "1", "shoot south 1", "shoot west -1",
                       "shoot east one"};
    for (String input : inputs) {
      controller.handleInput(input);
    }
    String expected = "view - listener added\nview - making game visible\nmodel - moving player "
            + "NORTH\nview - refresh\nview - showing outcome message: Action: Move NORTH\nview -"
            + " refresh\nview - showing outcome message: Action: Invalid\nmodel - moving player "
            + "SOUTH\nview - refresh\nview - showing outcome message: Action: Move SOUTH\nview -"
            + " refresh\nview - showing outcome message: Action: Invalid\nmodel - moving player "
            + "EAST\nview - refresh\nview - showing outcome message: Action: Move EAST\nmodel - "
            + "moving player WEST\nview - refresh\nview - showing outcome message: Action: Move "
            + "WEST\nview - showing error message: Error: Enter valid inputs\nmodel - picking up"
            + " treasure\nmodel - picking up arrows\nview - refresh\nview - showing outcome "
            + "message: Action: Pick Items\nview - refresh\nview - showing outcome message: "
            + "Action: Invalid\nmodel - moving player NORTH\nview - refresh\nview - showing "
            + "outcome message: Action: Move NORTH\nview - refresh\nview - showing outcome "
            + "message: Action: Invalid\nmodel - shooting arrow SOUTH over 1 caves\nview - "
            + "refresh\nview - showing outcome message: Action: Shoot SOUTH, Distance: 1. "
            + "Outcome is null\nmodel - shooting arrow WEST over -1 caves\nview - refresh\nview "
            + "- showing outcome message: Action: Shoot WEST, Distance: -1. Outcome is "
            + "null\nview - showing error message: Error: For input string: \"one\"\n";
    assertEquals(expected, log.toString());
  }

  /**
   * tests new game with mock view and model.
   */
  @Test
  public void testMockNewGame() {
    // new game without any model configurations.
    controller.handleInput("new");
    String expected = "view - listener added\nview - making game visible\nview - showing error "
            + "message: Error: Enter valid inputs\n";
    assertEquals(expected, log.toString());

    setup();
    // new game with illegal configuration specs.
    controller.handleInput("new 4 six 3 30 false 3 true");
    expected = "view - listener added\nview - making game visible\nview - showing error message: "
            + "Error: For input string: \"six\"\n";
    assertEquals(expected, log.toString());
  }

  /**
   * tests new game with mock view and model.
   */
  @Test
  public void testMockReuseConfiguration() {
    // reuse game configurations.
    controller.handleInput("reuse");
    String expected = "view - listener added\n"
            + "view - making game visible\n"
            + "model - returning n\n"
            + "model - returning m\n"
            + "model - returning connectivity\n"
            + "model - returning item percent\n"
            + "model - returning wrap\n"
            + "model - returning monsters\n"
            + "model - returning test\n"
            + "view - showing error message: Error: Unexpected specifications\n";
    assertEquals(expected, log.toString());
  }

  /**
   * tests restart game with mock view and model.
   */
  @Test
  public void testMockRestartGame() {
    controller.handleInput("restart");
    String expected = "view - listener added\nview - making game visible\n" + "model - resetting"
            + " game\nview - refresh\n" + "view - showing outcome message: Game Reset\n";
    assertEquals(expected, log.toString());
  }

  /**
   * test illegal arguments to controller constructor.
   */
  @Test
  public void testControllerArguments() {
    try {
      new MonsterDungeonGameViewController(null, null);
    }
    catch (IllegalArgumentException iae) {
      assertEquals("model and view cannot be null", iae.getMessage());
    }
  }

  /**
   * test to check getVisited works correctly.
   */
  @Test
  public void testGetVisited() {
    MonsterDungeonGame game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
    assertEquals("[(1, 4)]", game.getVisited().toString());
    game.movePlayer(Direction.NORTH);
    assertEquals("[(1, 4), (0, 4)]", game.getVisited().toString());
    game.movePlayer(Direction.EAST);
    assertEquals("[(1, 4), (0, 4), (0, 5)]", game.getVisited().toString());
    game.movePlayer(Direction.WEST);
    assertEquals("[(1, 4), (0, 4), (0, 5)]", game.getVisited().toString());
    game.movePlayer(Direction.SOUTH);
    assertEquals("[(1, 4), (0, 4), (0, 5)]", game.getVisited().toString());
  }

  /**
   * test restart game works correctly.
   */
  @Test
  public void testRestartGame() {
    MonsterDungeonGame game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
    game.shootArrow(Direction.NORTH, 1);
    game.shootArrow(Direction.NORTH, 1);
    game.shootArrow(Direction.NORTH, 1);
    assertEquals(0, game.getPlayerArrows());
    game.movePlayer(Direction.NORTH);
    assertEquals(1, game.getCaveArrows());
    game.pickUpArrows();
    assertEquals(0, game.getCaveArrows());
    game.movePlayer(Direction.EAST);
    assertEquals(1, game.getCaveArrows());
    for (Treasure treasure : game.getCaveTreasure().keySet()) {
      assertEquals(2, game.getCaveTreasure().get(treasure).intValue());
    }
    game.pickTreasure();
    game.pickUpArrows();
    assertEquals(0, game.getCaveArrows());
    for (Treasure treasure : game.getCaveTreasure().keySet()) {
      assertEquals(0, game.getCaveTreasure().get(treasure).intValue());
    }
    assertEquals(2, game.getPlayerArrows());
    for (Treasure treasure : game.getPlayerTreasure().keySet()) {
      assertEquals(2, game.getPlayerTreasure().get(treasure).intValue());
    }
    // reset game
    game.resetGame();
    assertEquals(3, game.getPlayerArrows());
    for (Treasure treasure : game.getPlayerTreasure().keySet()) {
      assertEquals(0, game.getPlayerTreasure().get(treasure).intValue());
    }
    game.movePlayer(Direction.NORTH);
    assertEquals(1, game.getCaveArrows());
    game.movePlayer(Direction.EAST);
    assertEquals(1, game.getCaveArrows());
    for (Treasure treasure : game.getCaveTreasure().keySet()) {
      assertEquals(2, game.getCaveTreasure().get(treasure).intValue());
    }
  }
}
