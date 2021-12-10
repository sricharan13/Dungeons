package dungeon.view;

import dungeon.controller.DungeonGameViewController;
import dungeon.model.ViewDungeonGame;

import javax.swing.JOptionPane;

/**
 * Implements the DungeonGameGui. Provides an implementation using Java swing. Reads user input
 * using Click events and key press events. Translates these events to a controller Readable
 * input and passes the inputs to controller for processing. Displays the entire dungeon state
 * and output for each action. Reads dungeon configuration from user and passes it to controller.
 */
public class MonsterDungeonGameGui implements DungeonGameGui {

  private final GameFrame gameFrame;
  private final ConfigurationFrame configurationFrame;

  /**
   * creates a new DungeonGameGui with the given readonly model and displays the game state to user.
   * @param game - readonly model.
   */
  public MonsterDungeonGameGui(ViewDungeonGame game) {
    if (game == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    gameFrame = new GameFrame(game);
    configurationFrame = new ConfigurationFrame();
  }

  @Override
  public void addListener(DungeonGameViewController listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    gameFrame.addListener(listener);
    configurationFrame.addListener(listener);
  }

  @Override
  public void refresh() {
    gameFrame.repaint();
  }

  @Override
  public void destroy() {
    gameFrame.dispose();
    configurationFrame.dispose();
  }

  @Override
  public void showOutcomeMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("message cannot be null");
    }
    gameFrame.showMessage(message);
  }

  @Override
  public void showErrorMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("message cannot be null");
    }
    if (gameFrame.isVisible()) {
      JOptionPane.showMessageDialog(gameFrame, message,"Error",
              JOptionPane.ERROR_MESSAGE);
    } else if (configurationFrame.isVisible()) {
      JOptionPane.showMessageDialog(configurationFrame, message,"Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void makeVisible(String visibility) {
    if (visibility == null) {
      throw new IllegalArgumentException("message cannot be null");
    }
    if (visibility.equals("game")) {
      gameFrame.setVisible(true);
      configurationFrame.setVisible(false);
    }
    else if (visibility.equals("configuration")) {
      configurationFrame.setVisible(true);
      gameFrame.setVisible(false);
    }
  }

}
