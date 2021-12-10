package dungeon.controller;

import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;
import static dungeon.model.Outcome.Shot;

import dungeon.model.Direction;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.Treasure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A controller for MonsterHuntDungeonGame. This controller plays the text based game by scanning
 * input from the Readable and outputs the result to Appendable.
 */
public class MonsterDungeonGameController implements DungeonGameController {

  private final MonsterDungeonGame game;
  private final Readable input;
  private final Appendable output;

  /**
   * Constructs a new Controller with the model, Readable and Appendable.
   * @param game - MonsterHuntDungeonGame model.
   * @param input - the input stream.
   * @param output - an Appendable output.
   */
  public MonsterDungeonGameController(MonsterDungeonGame game, Readable input, Appendable output) {
    this.game = game;
    this.input = input;
    this.output = output;
  }

  private static Direction getDirection(String direction) {
    switch (direction) {
      case "n":
        return NORTH;
      case "s":
        return SOUTH;
      case "w":
        return WEST;
      case "e":
        return EAST;
      default:
        throw new IllegalArgumentException();
    }
  }

  private static String sortTreasure(Map<Treasure, Integer> treasure) {
    StringBuilder sb = new StringBuilder();
    List<Treasure> keys = new ArrayList<>(treasure.keySet());
    Collections.sort(keys);
    for (Treasure t : keys) {
      sb.append(String.format("%s: %s, ", t, treasure.get(t)));
    }
    return sb.substring(0, sb.length() - 2);
  }

  @Override
  public void playGame() {
    try {
      output.append("\nPlayer can perform 3 actions: Move, Pick and Shoot\n");
      output.append("\nPress the following keys to control player actions:\n");
      output.append("'m' - Move, 'p' - Pick, 's' - Shoot\n");
      output.append("\nPress the following keys to select directions:\n");
      output.append("'n' - NORTH, 'w' - WEST, 'e' - EAST, 's' - SOUTH\n");
      output.append("\nPress 'q' to quit the game.\n");
      Scanner scanner = new Scanner(input);
      while (!game.isGameOver()) {
        output.append("\n----------------------------------------------------------------\n");
        output.append("\nPlayer Description:\n");
        output.append(String.format("Location: %s\n", game.getPlayerLocation()));
        output.append(String.format("Arrows: %s\n", game.getPlayerArrows()));
        output.append(String.format("Treasure: %s\n", sortTreasure(game.getPlayerTreasure())));
        output.append("\nLocation Description:\n");
        List<Direction> directions = game.getPossibleMoves();
        Collections.sort(directions);
        output.append(String.format("Moves: %s\n", directions));

        output.append(String.format("Arrows: %s\n", game.getCaveArrows()));
        output.append(String.format("Treasure: %s\n", sortTreasure(game.getCaveTreasure())));
        output.append(String.format("Smell: %s\n", game.getMonsterSmell()));
        boolean next = false;
        try {
          while (!next) {
            output.append("\nSelect player action:\n");
            switch (scanner.next()) {
              case "m": {
                while (true) {
                  output.append("Choose the direction in which player should move:\n");
                  try {
                    Direction direction = getDirection(scanner.next());
                    output.append(String.format("\nSelected Action - Move, Direction - %s\n",
                            direction));
                    game.movePlayer(direction);
                    break;
                  }
                  catch (IllegalArgumentException iae) {
                    output.append("\nInvalid direction selected. Try again.\n\n");
                  }
                }
                next = true;
                break;
              }
              case "p": {
                output.append("\nSelected Action - Pick\n");
                game.pickTreasure();
                game.pickUpArrows();
                next = true;
                break;
              }
              case "s": {
                Direction direction;
                int distance;
                while (true) {
                  output.append("Enter the direction in which arrow should be shot:\n");
                  try {
                    direction = getDirection(scanner.next());
                    break;
                  }
                  catch (IllegalArgumentException iae) {
                    output.append("\nInvalid direction selected. Try again.\n\n");
                  }
                }
                while (true) {
                  output.append("Enter the distance the arrow should travel:\n");
                  try {
                    distance = Integer.parseInt(scanner.next());
                    break;
                  }
                  catch (NumberFormatException nfe) {
                    output.append("\nDistance should be a number. Try again.\n\n");
                  }
                }
                output.append(String.format("\nSelected Action - Shoot, Direction - %s, Distance - "
                        + "%s\n", direction, distance));
                Shot shot = game.shootArrow(direction, distance);
                output.append(String.format("Shot: %s\n", shot));
                next = true;
                break;
              }
              case "q": {
                output.append("\nGame Quit!!!\n");
                return;
              }
              default: {
                output.append("\nUnsupported Operation, Try again.\n");
              }
            }
          }
        }
        catch (IllegalArgumentException iae) {
          output.append("\nException Occurred: ").append(iae.getMessage()).append("\n");
        }
        catch (NoSuchElementException nse) {
          output.append("\nRan out of inputs, Quitting game.\n");
          return;
        }
      }
      output.append(String.format("\nGame Over, Status: %s\n", game.getStatus()));
    }
    catch (IOException ioe) {
      throw new IllegalStateException("An error occurred appending to output");
    }
  }

}
