package dungeon.controller;

import java.awt.event.ActionListener;

/**
 * A DungeonGameController for GUIs. Reads input from user and outputs the results using a GUI.
 */
public interface DungeonGameViewController extends DungeonGameController, ActionListener {

  /**
   * Takes the user input fom the view and executes the input command.
   * @param input - a readable form of user input.
   */
  void handleInput(String input);

}
