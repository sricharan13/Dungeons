package dungeon.view;

import dungeon.model.Outcome;
import dungeon.model.Treasure;
import dungeon.model.ViewDungeonGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class DescriptionPanel extends JPanel {

  private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 25);
  private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 20);
  private static final Font CONTENT_FONT = new Font("Arial", Font.BOLD, 15);
  private static final Color TEXT_COLOR = new Color(175,166,145);

  private final ViewDungeonGame game;
  private final JLabel message;
  private final JLabel status;
  private final JLabel playerArrows;
  private final JLabel playerDiamonds;
  private final JLabel playerRubies;
  private final JLabel playerSapphires;
  private final JLabel caveArrows;
  private final JLabel caveDiamonds;
  private final JLabel caveRubies;
  private final JLabel caveSapphires;
  private final JLabel caveStench;
  private final JLabel caveNeighbours;

  DescriptionPanel(ViewDungeonGame game) {
    this.game = game;
    setBackground(Color.BLACK);
    setLayout(new GridLayout(6, 0));
    setBorder(new EmptyBorder(100, 10, 100, 10));

    JLabel title = new JLabel();
    title.setText("MONSTER HUNT GAME");
    title.setFont(TITLE_FONT);
    title.setForeground(TEXT_COLOR);
    title.setHorizontalAlignment(JLabel.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);
    add(title);

    JLabel moveInstruction = new JLabel();
    moveInstruction.setText("Press 'arrow keys'/ use 'mouse clicks' to move the player");
    moveInstruction.setFont(CONTENT_FONT);
    moveInstruction.setForeground(TEXT_COLOR);

    JLabel pickInstruction = new JLabel();
    pickInstruction.setText("Press 'p' to pick up items from a cave/tunnel");
    pickInstruction.setFont(CONTENT_FONT);
    pickInstruction.setForeground(TEXT_COLOR);

    JLabel shootInstruction = new JLabel();
    shootInstruction.setText("Press 's' and an 'arrow key' to shoot an arrow in desired "
            + "direction");
    shootInstruction.setFont(CONTENT_FONT);
    shootInstruction.setForeground(TEXT_COLOR);

    JPanel instructionsPanel = new JPanel();
    instructionsPanel.setBackground(Color.BLACK);
    instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
    instructionsPanel.add(moveInstruction);
    instructionsPanel.add(pickInstruction);
    instructionsPanel.add(shootInstruction);
    add(instructionsPanel);

    message = new JLabel();
    message.setText("Safe Hunting!!!");
    message.setFont(HEADER_FONT);
    message.setForeground(TEXT_COLOR);
    message.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    status = new JLabel();
    status.setFont(TITLE_FONT);
    status.setForeground(TEXT_COLOR);
    status.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
    messagePanel.setBackground(Color.BLACK);
    messagePanel.add(message);
    messagePanel.add(status);
    add(messagePanel);

    playerArrows = new JLabel();
    playerArrows.setIcon(new ImageIcon(ImageReader.getImage("ARROW")));
    playerArrows.setFont(CONTENT_FONT);
    playerArrows.setForeground(TEXT_COLOR);

    playerDiamonds = new JLabel();
    playerDiamonds.setIcon(new ImageIcon(ImageReader.getImage("DIAMOND")));
    playerDiamonds.setFont(CONTENT_FONT);
    playerDiamonds.setForeground(TEXT_COLOR);

    playerRubies = new JLabel();
    playerRubies.setIcon(new ImageIcon(ImageReader.getImage("RUBY")));
    playerRubies.setFont(CONTENT_FONT);
    playerRubies.setForeground(TEXT_COLOR);

    playerSapphires = new JLabel();
    playerSapphires.setIcon(new ImageIcon(ImageReader.getImage("SAPPHIRE")));
    playerSapphires.setFont(CONTENT_FONT);
    playerSapphires.setForeground(TEXT_COLOR);

    JLabel playerHeader = new JLabel();
    playerHeader.setText("Player Details");
    playerHeader.setFont(HEADER_FONT);
    playerHeader.setForeground(TEXT_COLOR);

    JPanel playerPanel = new JPanel();
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    playerPanel.setBackground(Color.BLACK);
    playerPanel.add(playerHeader);
    playerPanel.add(playerArrows);
    playerPanel.add(playerDiamonds);
    playerPanel.add(playerRubies);
    playerPanel.add(playerSapphires);
    add(playerPanel);

    caveArrows = new JLabel();
    caveArrows.setIcon(new ImageIcon(ImageReader.getImage("ARROW")));
    caveArrows.setFont(CONTENT_FONT);
    caveArrows.setForeground(TEXT_COLOR);

    caveDiamonds = new JLabel();
    caveDiamonds.setIcon(new ImageIcon(ImageReader.getImage("DIAMOND")));
    caveDiamonds.setFont(CONTENT_FONT);
    caveDiamonds.setForeground(TEXT_COLOR);

    caveRubies = new JLabel();
    caveRubies.setIcon(new ImageIcon(ImageReader.getImage("RUBY")));
    caveRubies.setFont(CONTENT_FONT);
    caveRubies.setForeground(TEXT_COLOR);

    caveSapphires = new JLabel();
    caveSapphires.setIcon(new ImageIcon(ImageReader.getImage("SAPPHIRE")));
    caveSapphires.setFont(CONTENT_FONT);
    caveSapphires.setForeground(TEXT_COLOR);

    caveStench = new JLabel();
    caveStench.setFont(CONTENT_FONT);
    caveStench.setForeground(TEXT_COLOR);

    caveNeighbours = new JLabel();
    caveNeighbours.setFont(CONTENT_FONT);
    caveNeighbours.setForeground(TEXT_COLOR);

    JLabel locationHeader = new JLabel();
    locationHeader.setText("Location Details");
    locationHeader.setFont(HEADER_FONT);
    locationHeader.setForeground(TEXT_COLOR);

    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
    locationPanel.setBackground(Color.BLACK);
    locationPanel.add(locationHeader);
    locationPanel.add(caveArrows);
    locationPanel.add(caveDiamonds);
    locationPanel.add(caveRubies);
    locationPanel.add(caveSapphires);
    locationPanel.add(caveStench);
    locationPanel.add(caveNeighbours);

    add(locationPanel);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    playerArrows.setText("x" + game.getPlayerArrows());
    playerSapphires.setText("x" + game.getPlayerTreasure().get(Treasure.SAPPHIRE));
    playerRubies.setText("x" + game.getPlayerTreasure().get(Treasure.RUBY));
    playerDiamonds.setText("x" + game.getPlayerTreasure().get(Treasure.DIAMOND));
    caveArrows.setText("x" + game.getCaveArrows());
    caveSapphires.setText("x" + game.getCaveTreasure().get(Treasure.SAPPHIRE));
    caveRubies.setText("x" + game.getCaveTreasure().get(Treasure.RUBY));
    caveDiamonds.setText("x" + game.getCaveTreasure().get(Treasure.DIAMOND));
    Outcome.Smell smell = game.getMonsterSmell();
    caveNeighbours.setText("You can move " + game.getPossibleMoves());
    if (smell != Outcome.Smell.NONE) {
      caveStench.setText("You smell a stench, it's " + smell);
    } else {
      caveStench.setText("");
    }
    if (game.getStatus() == Outcome.Status.WIN) {
      status.setText("Game Over! YOU WIN:)");
    }
    if (game.getStatus() == Outcome.Status.LOSS) {
      status.setText("Game Over! YOU LOOSE:(");
    }
    else if (game.getStatus() == Outcome.Status.INPROGRESS) {
      status.setText("");
    }
  }

  void showMessage(String message) {
    this.message.setText(message);
  }
}
