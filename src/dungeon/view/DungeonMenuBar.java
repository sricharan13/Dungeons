package dungeon.view;

import dungeon.controller.DungeonGameViewController;
import dungeon.model.ViewDungeonGame;

import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class DungeonMenuBar extends JMenuBar {

  private final JMenuItem restartGame;
  private final JMenuItem reuseConfig;
  private final JMenuItem newGame;

  DungeonMenuBar(ViewDungeonGame game) {
    JMenu dungeonConfig = new JMenu("Configuration");
    dungeonConfig.add("Rows: " + game.getN());
    dungeonConfig.add("Columns: " + game.getM());
    dungeonConfig.add("Connectivity: " + game.getConnectivity());
    dungeonConfig.add("Wrap: " + game.isWrap());
    dungeonConfig.add("Item Percentage: " + game.getItemPercent());
    dungeonConfig.add("Monsters: " + game.getMonsters());
    dungeonConfig.add("Test: " + game.isTest());
    add(dungeonConfig);
    JMenu gameSettings = new JMenu("Game");
    restartGame = new JMenuItem("Restart Game");
    gameSettings.add(restartGame);
    reuseConfig = new JMenuItem("Reuse Configuration");
    gameSettings.add(reuseConfig);
    newGame = new JMenuItem("New Game");
    gameSettings.add(newGame);
    JMenuItem quit = new JMenuItem("Quit");
    quit.addActionListener((ActionEvent e) -> System.exit(0));
    gameSettings.add(quit);
    add(gameSettings);
  }

  void addActionListener(DungeonGameViewController listener) {
    restartGame.addActionListener(listener);
    reuseConfig.addActionListener(listener);
    newGame.addActionListener(listener);
  }


}
