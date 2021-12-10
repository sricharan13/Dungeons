package dungeon.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class ImageReader {

  private static final BufferedImage N;
  private static final BufferedImage S;
  private static final BufferedImage E;
  private static final BufferedImage W;
  private static final BufferedImage NS;
  private static final BufferedImage NE;
  private static final BufferedImage NW;
  private static final BufferedImage SE;
  private static final BufferedImage SW;
  private static final BufferedImage EW;
  private static final BufferedImage NSE;
  private static final BufferedImage NSW;
  private static final BufferedImage NEW;
  private static final BufferedImage SEW;
  private static final BufferedImage NSEW;
  private static final BufferedImage ARROW;
  private static final BufferedImage DIAMOND;
  private static final BufferedImage SAPPHIRE;
  private static final BufferedImage RUBY;
  private static final BufferedImage OTYUGH;
  private static final BufferedImage PLAYER;
  private static final BufferedImage FAINT;
  private static final BufferedImage PUNGENT;

  static {
    try {
      N = ImageIO.read(ClassLoader.getSystemResource("images/N.png"));
      S = ImageIO.read(ClassLoader.getSystemResource("images/S.png"));
      E = ImageIO.read(ClassLoader.getSystemResource("images/E.png"));
      W = ImageIO.read(ClassLoader.getSystemResource("images/W.png"));
      NS = ImageIO.read(ClassLoader.getSystemResource("images/NS.png"));
      NE = ImageIO.read(ClassLoader.getSystemResource("images/NE.png"));
      NW = ImageIO.read(ClassLoader.getSystemResource("images/NW.png"));
      SE = ImageIO.read(ClassLoader.getSystemResource("images/SE.png"));
      SW = ImageIO.read(ClassLoader.getSystemResource("images/SW.png"));
      EW = ImageIO.read(ClassLoader.getSystemResource("images/EW.png"));
      NSE = ImageIO.read(ClassLoader.getSystemResource("images/NSE.png"));
      NSW = ImageIO.read(ClassLoader.getSystemResource("images/NSW.png"));
      NEW = ImageIO.read(ClassLoader.getSystemResource("images/NEW.png"));
      SEW = ImageIO.read(ClassLoader.getSystemResource("images/SEW.png"));
      NSEW = ImageIO.read(ClassLoader.getSystemResource("images/NSEW.png"));
      ARROW = resizeImage(ImageIO.read(ClassLoader.getSystemResource("images/arrow-white.png")));
      DIAMOND = resizeImage(ImageIO.read(ClassLoader.getSystemResource("images/diamond.png")));
      SAPPHIRE = resizeImage(ImageIO.read(ClassLoader.getSystemResource("images/sapphire.png")));
      RUBY = resizeImage(ImageIO.read(ClassLoader.getSystemResource("images/ruby.png")));
      OTYUGH = ImageIO.read(ClassLoader.getSystemResource("images/otyugh.png"));
      FAINT = ImageIO.read(ClassLoader.getSystemResource("images/stench01.png"));
      PUNGENT = ImageIO.read(ClassLoader.getSystemResource("images/stench02.png"));
      PLAYER = resizeImage(resizeImage(resizeImage(ImageIO.read(
              ClassLoader.getSystemResource("images/player.png")))));
    } catch (IOException e) {
      throw new IllegalArgumentException("image source file not found");
    }
  }

  static BufferedImage resizeImage(BufferedImage original) {
    int newWidth = original.getWidth() / 2;
    int newHeight = original.getHeight() / 2;
    BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = resized.createGraphics();
    graphics2D.drawImage(original, 0, 0, newWidth, newHeight, null);
    graphics2D.dispose();
    return resized;
  }

  static BufferedImage getImage(String name) {
    switch (name) {
      case "N":
        return N;
      case "S":
        return S;
      case "E":
        return E;
      case "W":
        return W;
      case "NS":
        return NS;
      case "NE":
        return NE;
      case "NW":
        return NW;
      case "SE":
        return SE;
      case "SW":
        return SW;
      case "EW":
        return EW;
      case "NSE":
        return NSE;
      case "NSW":
        return NSW;
      case "NEW":
        return NEW;
      case "SEW":
        return SEW;
      case "NSEW":
        return NSEW;
      case "PUNGENT":
        return PUNGENT;
      case "FAINT":
        return FAINT;
      case "DIAMOND":
        return DIAMOND;
      case "RUBY":
        return RUBY;
      case "SAPPHIRE":
        return SAPPHIRE;
      case "ARROW":
        return ARROW;
      case "PLAYER":
        return PLAYER;
      case "OTYUGH":
        return OTYUGH;
      default:
        throw new IllegalArgumentException("no such image found");
    }
  }
}
