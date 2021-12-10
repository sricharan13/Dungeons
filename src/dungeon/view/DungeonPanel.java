package dungeon.view;

import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;
import static dungeon.model.Outcome.Smell.FAINT;
import static dungeon.model.Outcome.Smell.PUNGENT;
import static dungeon.model.Treasure.DIAMOND;
import static dungeon.model.Treasure.RUBY;
import static dungeon.model.Treasure.SAPPHIRE;

import dungeon.model.Cave;
import dungeon.model.Direction;
import dungeon.model.Location;
import dungeon.model.Treasure;
import dungeon.model.ViewDungeonGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;

class DungeonPanel extends JPanel {

  static final int CELL_SIZE = 64;

  private final ViewDungeonGame game;

  DungeonPanel(ViewDungeonGame game) {
    this.game = game;
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(CELL_SIZE + game.getM() * CELL_SIZE,
            CELL_SIZE + game.getN() * CELL_SIZE));
  }

  int getXOffSet() {
    return (getWidth() - CELL_SIZE * game.getM()) / 2;
  }

  int getYOffSet() {
    return (getHeight() - CELL_SIZE * game.getN()) / 2;
  }

  private BufferedImage getLocationImage(Set<Direction> directions) {
    StringBuilder locationString = new StringBuilder();
    if (directions.contains(NORTH)) {
      locationString.append("N");
    }
    if (directions.contains(SOUTH)) {
      locationString.append("S");
    }
    if (directions.contains(EAST)) {
      locationString.append("E");
    }
    if (directions.contains(WEST)) {
      locationString.append("W");
    }
    return ImageReader.getImage(locationString.toString());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Cave[][] dungeon = game.getDungeonState();
    // calculate offset from boundaries.
    int xOffset = getXOffSet();
    int yOffset = getYOffSet();

    // draw visited caves.
    for (Location location : game.getVisited()) {
      int x = location.getX();
      int y = location.getY();

      BufferedImage locationImage = getLocationImage(dungeon[x][y].getNeighbours().keySet());

      // check if location has player and if there is stench in the location. add player and
      // stench to location.
      if (location.equals(game.getPlayerLocation())) {

        if (game.getMonsterSmell() == PUNGENT) {
          locationImage = imageOverlay(locationImage, ImageReader.getImage("PUNGENT"), 0, 0);
        }
        else if (game.getMonsterSmell() == FAINT) {
          locationImage = imageOverlay(locationImage, ImageReader.getImage("FAINT"), 0, 0);
        }

        locationImage = imageOverlay(locationImage, ImageReader.getImage("PLAYER"), CELL_SIZE / 3,
                CELL_SIZE / 3);
      }

      // check if location has treasure and add treasure to location.
      Map<Treasure, Integer> treasure = dungeon[x][y].getTreasure();
      if (treasure.get(RUBY) > 0) {
        locationImage = imageOverlay(locationImage, ImageReader.getImage("RUBY"), CELL_SIZE / 3,
                CELL_SIZE / 2);
      }
      if (treasure.get(SAPPHIRE) > 0) {
        locationImage = imageOverlay(locationImage, ImageReader.getImage("SAPPHIRE"),
                CELL_SIZE / 3 + ImageReader.getImage("RUBY").getWidth(), CELL_SIZE / 2);
      }
      if (treasure.get(DIAMOND) > 0) {
        locationImage = imageOverlay(locationImage, ImageReader.getImage("DIAMOND"),
                CELL_SIZE / 3 + ImageReader.getImage("RUBY").getWidth() + ImageReader.getImage(
                        "SAPPHIRE").getWidth(),
                CELL_SIZE / 2);
      }

      // check if location has arrows and add arrows to location.
      if (dungeon[x][y].getArrows() > 0) {
        locationImage = imageOverlay(locationImage, ImageReader.getImage("ARROW"), CELL_SIZE / 3,
                CELL_SIZE / 3);
      }

      // check if location has otyugh
      if (dungeon[x][y].hasMonster()) {
        locationImage = imageOverlay(locationImage, ImageReader.getImage("OTYUGH"), 0, 0);
      }

      g.drawImage(locationImage, xOffset + y * CELL_SIZE, yOffset + x * CELL_SIZE, null);
    }
  }

  Location getPlayerLocation() {
    return game.getPlayerLocation();
  }

  // Creating the final image (RGB + Alpha) of width and height that will match the final image.
  private static BufferedImage imageOverlay(BufferedImage base, BufferedImage top, int x, int y) {
    int width = base.getWidth();
    int height = base.getHeight();
    BufferedImage overlay = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics finalImageGraphics = overlay.getGraphics();
    finalImageGraphics.drawImage(base, 0, 0, null);
    finalImageGraphics.drawImage(top, x, y, null);
    return overlay;
  }
}
