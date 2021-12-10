import dungeon.controller.DungeonGameController;
import dungeon.controller.DungeonGameViewController;
import dungeon.controller.MonsterDungeonGameController;
import dungeon.controller.MonsterDungeonGameViewController;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.MonsterHuntDungeonGame;
import dungeon.view.DungeonGameGui;
import dungeon.view.MonsterDungeonGameGui;

import java.io.InputStreamReader;

/**
 * Driver for GUI interface. starts GUI interface if no command line args are given or start a
 * text adventure game if command line args are given.
 */
public class GuiDriver {

  /**
   * main method for the GUI game.
   * @param args - command line args.
   */
  public static void main(String[] args) {

    MonsterDungeonGame game;
    DungeonGameController controller;
    DungeonGameViewController viewController;
    DungeonGameGui view;
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    if (args.length == 0) {
      game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
      view = new MonsterDungeonGameGui(game);
      viewController = new MonsterDungeonGameViewController(game, view);
      viewController.playGame();
    }
    else {

      // args = [int n, int m, int connectivity, int percent, boolean wrap]
      int n = Integer.parseInt(args[0]);
      int m = Integer.parseInt(args[1]);
      int connectivity = Integer.parseInt(args[2]);
      int percent = Integer.parseInt(args[3]);
      boolean wrap = Boolean.parseBoolean(args[4]);
      int otyughs = Integer.parseInt(args[5]);

      System.out.println("\n************************ Interactive Run ************************\n");

      try {
        game = new MonsterHuntDungeonGame(n, m, connectivity, percent, wrap, otyughs, false);
        controller = new MonsterDungeonGameController(game, input, output);
        controller.playGame();
      }
      catch (IllegalArgumentException iae) {
        System.out.println("Exception Message: " + iae.getMessage());
      }
    }
  }
}
