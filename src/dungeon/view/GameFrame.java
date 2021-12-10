package dungeon.view;

import dungeon.controller.DungeonGameViewController;
import dungeon.model.ViewDungeonGame;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

class GameFrame extends JFrame {
  private final DungeonMenuBar dungeonMenuBar;
  private final DungeonPanel dungeonPanel;
  private final DescriptionPanel descriptionPanel;

  GameFrame(ViewDungeonGame game) {
    super("Monster Dungeon Game");
    setLayout(new GridLayout());
    setSize(1000,700);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    dungeonPanel = new DungeonPanel(game);
    descriptionPanel = new DescriptionPanel(game);
    dungeonMenuBar = new DungeonMenuBar(game);
    setJMenuBar(dungeonMenuBar);
    add(new JScrollPane(dungeonPanel));
    add(new JScrollPane(descriptionPanel));
  }

  void addListener(DungeonGameViewController listener) {

    dungeonMenuBar.addActionListener(listener);

    // create the MouseAdapter
    MouseAdapter dungeonMouseAdapter = new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        // arithmetic to convert panel coords to location coords
        int x1 = (e.getY() - dungeonPanel.getYOffSet()) / DungeonPanel.CELL_SIZE;
        int y1 = (e.getX() - dungeonPanel.getXOffSet()) / DungeonPanel.CELL_SIZE;

        int x2 = dungeonPanel.getPlayerLocation().getX();
        int y2 = dungeonPanel.getPlayerLocation().getY();

        if (x2 - 1 == x1 && y2 == y1) {
          listener.handleInput("north");
        }
        else if (x2 + 1 == x1 && y2 == y1) {
          listener.handleInput("south");
        }
        else if (x2 == x1 && y2 + 1 == y1) {
          listener.handleInput("east");
        }
        else if (x2 == x1 && y2 - 1 == y1) {
          listener.handleInput("west");
        }
      }
    };

    KeyAdapter dungeonKeyAdapter = new KeyAdapter() {

      private boolean shootFlag = false;
      private boolean directionFlag = false;

      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        String operation = "";
        int keyCode = e.getKeyCode();
        switch (keyCode) {
          case KeyEvent.VK_UP:
            if (shootFlag) {
              directionFlag = true;
            }
            operation = "north";
            break;
          case KeyEvent.VK_DOWN:
            if (shootFlag) {
              directionFlag = true;
            }
            operation = "south";
            break;
          case KeyEvent.VK_LEFT:
            if (shootFlag) {
              directionFlag = true;
            }
            operation = "west";
            break;
          case KeyEvent.VK_RIGHT:
            if (shootFlag) {
              directionFlag = true;
            }
            operation = "east";
            break;
          case KeyEvent.VK_P:
            if (shootFlag) {
              shootFlag = false;
            }
            operation = "pick";
            break;
          case KeyEvent.VK_S:
            shootFlag = true;
            break;
          default:
            operation = String.valueOf(e.getKeyChar());
            shootFlag = false;
            directionFlag = false;
        }
        if (shootFlag && directionFlag) {
          operation = "shoot" + " " + operation
                  + " " + JOptionPane.showInputDialog("Enter distance:");
          shootFlag = false;
          directionFlag = false;
        }
        if (shootFlag == directionFlag) {
          listener.handleInput(operation);
        }
      }
    };
    dungeonPanel.addKeyListener(dungeonKeyAdapter);
    dungeonPanel.addMouseListener(dungeonMouseAdapter);
    dungeonPanel.setFocusable(true);
  }

  void showMessage(String message) {
    descriptionPanel.showMessage(message);
  }
}
