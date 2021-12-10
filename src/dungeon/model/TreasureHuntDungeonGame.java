package dungeon.model;

import static dungeon.model.Outcome.Smell;
import static dungeon.model.Outcome.Smell.NONE;
import static dungeon.model.Outcome.Status.WIN;
import static dungeon.model.Treasure.emptyTreasure;
import static dungeon.model.Treasure.treasure;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * An implementation of Game of Dungeons. The caves of DungeonGame can be wrapped around. The
 * edge caves connect to the caves in opposite direction. The paths in DungeonGame have a
 * interconnectivities between them where an interconnectivity of 0 implies exactly one path
 * between every cave and as the interconnectivity increases the number of paths between caves
 * also increases. The number of paths in the dungeon is V - 1 + c, where V is number of caves
 * and c is interconnectivity. Start and End caves of DungeonGame have a minimum manhattan
 * distance of 5. Only a few percentage caves (not tunnels) of DungeonGame have treasure. The
 * number of caves, the interconnectivity, the percentage of treasure in caves and weather a
 * dungeon should be wrappable or should be specified when creating the dungeon.
 */
public class TreasureHuntDungeonGame extends AbstractDungeonGame implements TreasureDungeonGame {

  private final float itemPercent;

  /**
   * Constructs a dungeon with given specifications.
   * @param n - number of rows of caves.
   * @param m - number of columns of caves.
   * @param connectivity - interconnectivity between caves in dungeon.
   * @param percent - percentage of treasure in caves.
   * @param wrap - whether the dungeon is wrapping or non wrapping.
   * @param test - creates a deterministic dungeon for testing purposes if true.
   */
  public TreasureHuntDungeonGame(int n, int m, int connectivity, float percent, boolean wrap,
                                 boolean test) {
    super(n, m, connectivity, wrap, test);
    if (percent < 0 || percent > 100) {
      throw new IllegalArgumentException("Unexpected specifications");
    }
    // add treasure to given percentage of caves.
    List<Location> caves = getCaves();
    Random random = getRandom();
    DungeonCave[][] dungeon = getDungeon();
    double num = Math.ceil((percent * caves.size()) / 100);
    int counter = 0;
    while (counter < num) {
      int i = random.nextInt(caves.size());
      Location location = caves.get(i);
      caves.remove(i);
      int x = location.getX();
      int y = location.getY();
      dungeon[x][y].setTreasure(treasure());
      counter++;
    }
    setDungeon(dungeon);
    setInitDungeon();
    itemPercent = percent;
  }

  @Override
  DungeonCave newDungeonCave(Map<Direction, Location> neighbours) {
    return new TreasureCave(emptyTreasure(), neighbours);
  }

  @Override
  DungeonCave newDungeonCave(DungeonCave dungeonCave) {
    return new TreasureCave(dungeonCave);
  }

  @Override
  DungeonPlayer newPlayer(Location location) {
    return new ExploringPlayer(location, emptyTreasure());
  }

  @Override
  DungeonPlayer newPlayer(DungeonPlayer player) {
    return new ExploringPlayer(player);
  }

  @Override
  public Map<Treasure, Integer> getPlayerTreasure() {
    return getPlayer().getTreasure();
  }

  @Override
  public Smell getMonsterSmell() {
    return NONE;
  }

  @Override
  public int getCaveArrows() {
    return 0;
  }

  @Override
  public int getPlayerArrows() {
    return 0;
  }

  @Override
  public int getItemPercent() {
    return (int) itemPercent;
  }

  @Override
  public int getMonsters() {
    return 0;
  }

  @Override
  public Map<Treasure, Integer> getCaveTreasure() {
    int x = getPlayerX();
    int y = getPlayerY();
    DungeonCave[][] dungeon = getDungeon();
    return dungeon[x][y].getTreasure();
  }

  @Override
  public void movePlayer(Direction direction) {
    updatePlayerLocation(direction);
    Location end = getEnd();
    if (end.equals(getPlayerLocation())) {
      pickTreasure();
      setGameOver();
      setStatus(WIN);
    }
  }

  @Override
  public void pickTreasure() {
    if (isGameOver()) {
      throw new IllegalArgumentException("game over, cannot pick treasure");
    }
    int x = getPlayerX();
    int y = getPlayerY();
    DungeonPlayer player = getPlayer();
    DungeonCave[][] dungeon = getDungeon();
    player.updateTreasure(dungeon[x][y].getTreasure());
    dungeon[x][y].setTreasure(emptyTreasure());
    setPlayer(player);
    setDungeon(dungeon);
  }

}
