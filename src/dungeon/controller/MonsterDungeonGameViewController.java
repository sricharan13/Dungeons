package dungeon.controller;

import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;
import static dungeon.model.Outcome.Shot;

import dungeon.model.Direction;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.MonsterHuntDungeonGame;
import dungeon.view.DungeonGameGui;
import dungeon.view.MonsterDungeonGameGui;

import java.awt.event.ActionEvent;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A DungeonGameViewController that uses JavaSwing read and output the model to user. Handles
 * different user inputs such as move, pick shoot, reset, restart and reuse. Passes the actions
 * performed and their outcomes to user.
 */
public class MonsterDungeonGameViewController implements DungeonGameViewController {

  private MonsterDungeonGame model;
  private DungeonGameGui view;

  /**
   * Constructs a new DungeonGameViewController with the given model and view.
   * @param model - The model with which game is played.
   * @param view - The view to and from which outputs are displayed and inputs are read.
   */
  public MonsterDungeonGameViewController(MonsterDungeonGame model, DungeonGameGui view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model and view cannot be null");
    }
    this.model = model;
    this.view = view;
  }

  private void newGame(int n, int m, int connectivity, float percent, boolean wrap,
                       int monsters, boolean test) {
    model = new MonsterHuntDungeonGame(n, m, connectivity, percent, wrap, monsters, test);
    view.destroy();
    view = new MonsterDungeonGameGui(model);
    playGame();
  }

  @Override
  public void playGame() {
    view.addListener(this);
    view.makeVisible("game");
  }

  @Override
  public void handleInput(String input) {
    Scanner sc = new Scanner(input);
    String message = "Action: ";
    try {
      switch (sc.next()) {
        case "north":
          model.movePlayer(NORTH);
          message = message + "Move NORTH";
          break;
        case "south":
          model.movePlayer(SOUTH);
          message = message + "Move SOUTH";
          break;
        case "west":
          model.movePlayer(WEST);
          message = message + "Move WEST";
          break;
        case "east":
          model.movePlayer(EAST);
          message = message + "Move EAST";
          break;
        case "pick":
          model.pickTreasure();
          model.pickUpArrows();
          message = message + "Pick Items";
          break;
        case "shoot":
          Direction direction;
          switch (sc.next()) {
            case "north":
              direction = NORTH;
              break;
            case "south":
              direction = SOUTH;
              break;
            case "west":
              direction = WEST;
              break;
            case "east":
              direction = EAST;
              break;
            default:
              throw new IllegalArgumentException("Enter a valid shoot direction");
          }
          int distance = Integer.parseInt(sc.next());
          Shot shot = model.shootArrow(direction, distance);
          message =  message + "Shoot " + direction + ", Distance: " + distance + ". Outcome is"
                + " " + shot;
          break;
        case "restart":
          model.resetGame();
          message = "Game Reset";
          break;
        case "reuse":
          newGame(model.getN(), model.getM(), model.getConnectivity(), model.getItemPercent(),
                  model.isWrap(), model.getMonsters(), model.isTest());
          message = "New game with previous configuration";
          break;
        case "new":
          int n = Integer.parseInt(sc.next());
          int m = Integer.parseInt(sc.next());
          int connectivity = Integer.parseInt(sc.next());
          int percent = Integer.parseInt(sc.next());
          boolean wrap = Boolean.parseBoolean(sc.next());
          int monsters = Integer.parseInt(sc.next());
          boolean test = Boolean.parseBoolean(sc.next());
          newGame(n, m, connectivity, percent, wrap, monsters, test);
          message = "New Game";
          break;
        default:
          message = message + "Invalid";
      }
      view.refresh();
      view.showOutcomeMessage(message);
    }
    catch (NoSuchElementException nse) {
      view.showErrorMessage("Error: Enter valid inputs");
    }
    catch (IllegalArgumentException iae) {
      view.showErrorMessage("Error: "  + iae.getMessage());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Restart Game")) {
      handleInput("restart");
    }
    else if (e.getActionCommand().equals("Reuse Configuration")) {
      handleInput("reuse");
    }
    else if (e.getActionCommand().equals("New Game")) {
      view.makeVisible("configuration");
    }
  }
}
