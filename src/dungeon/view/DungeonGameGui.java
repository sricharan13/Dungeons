package dungeon.view;

import dungeon.controller.DungeonGameViewController;

/**
 * A view for DungeonGame. displays the dungeon and provides a visual interface for users.
 */
public interface DungeonGameGui {

  /**
   * Set up the controller to handle click events and key press events in this view.
   *
   * @param listener the controller
   */
  void addListener(DungeonGameViewController listener);

  /**
   * Set the view's visibility. Visibility tells the view what to display in the view.
   */
  void makeVisible(String visibility);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * destroys this view.
   */
  void destroy();

  /**
   * Transmit a message to the view.
   */
  void showOutcomeMessage(String message);

  /**
   * Transmit error message to the view.
   */
  void showErrorMessage(String message);
}
