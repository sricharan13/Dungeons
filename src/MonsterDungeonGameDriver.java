package dungeon.driver;

import dungeon.controller.DungeonGameController;
import dungeon.controller.MonsterDungeonGameController;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.MonsterHuntDungeonGame;

import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Driver class to display the working of MonsterDungeonGame.
 */
public class MonsterDungeonGameDriver {

  /**
   * Main method that runs the MonsterDungeonGame controller.
   * @param args - command line arguments specifying the configuration of dungeon.
   */
  public static void main(String[] args) {

    // Example Runs:

    // dungeon created for example runs:
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

    // Example Run 1:
    System.out.println("\n--------------------------- Example Run 1 ---------------------------\n");

    MonsterDungeonGame game;
    Readable input;
    DungeonGameController controller;
    Appendable output = System.out;

    game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
    input = new StringReader("m n p m e p m w m s m w m n");
    controller = new MonsterDungeonGameController(game, input, output);
    controller.playGame();

    System.out.println("\n--------------------------- Example Run 2 ---------------------------\n");

    game = new MonsterHuntDungeonGame(4, 6, 3, 30, false, 3, true);
    input = new StringReader("m w m w m w p m n p s w 1 s w 1 m w");
    controller = new MonsterDungeonGameController(game, input, output);
    controller.playGame();

    // args = [int n, int m, int connectivity, int percent, boolean wrap]
    int n = Integer.parseInt(args[0]);
    int m = Integer.parseInt(args[1]);
    int connectivity = Integer.parseInt(args[2]);
    int percent = Integer.parseInt(args[3]);
    boolean wrap = Boolean.parseBoolean(args[4]);
    int otyughs = Integer.parseInt(args[5]);

    System.out.println("\n************************** Interactive Run **************************\n");

    try {
      game = new MonsterHuntDungeonGame(n, m, connectivity, percent, wrap, otyughs, false);
      input = new InputStreamReader(System.in);
      controller = new MonsterDungeonGameController(game, input, output);
      controller.playGame();
    }
    catch (IllegalArgumentException iae) {
      System.out.println("Exception Message: " + iae.getMessage());
    }
  }
}
