package dungeon.model;

import static dungeon.model.Outcome.Shot;
import static dungeon.model.Outcome.Shot.HIT;
import static dungeon.model.Outcome.Shot.MISS;
import static dungeon.model.Outcome.Shot.NOEFFECT;
import static dungeon.model.Outcome.Shot.WASTE;
import static dungeon.model.Outcome.Smell;
import static dungeon.model.Outcome.Smell.FAINT;
import static dungeon.model.Outcome.Smell.NONE;
import static dungeon.model.Outcome.Smell.PUNGENT;
import static dungeon.model.Outcome.Status.LOSS;
import static dungeon.model.Outcome.Status.WIN;
import static dungeon.model.Treasure.emptyTreasure;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A MonsterHuntDungeonGame is a MonsterDungeonGame in which Players can hunt monsters.
 * Player can kill Monsters by shooting arrows in a direction over a certain distance. Distance
 * is measured as the number of caves. Arrows will curve if they are in a tunnel but will go in a
 * straight direction in caves.
 */
public class MonsterHuntDungeonGame extends TreasureHuntDungeonGame implements MonsterDungeonGame {

  private static final int MAX_HEALTH = 100;
  private static final int DAMAGE = 50;
  private static final int MIN_HEALTH = 0;
  private static final double ESCAPE_THRESHOLD = 0.5;
  private static final int START_ARROWS = 3;
  private static final int ADDITIONAL_ARROWS = 1;

  private final int monsters;

  /**
   * Constructs a dungeon with given specifications.
   *
   * @param n            - number of rows of caves.
   * @param m            - number of columns of caves.
   * @param connectivity - interconnectivity between caves in dungeon.
   * @param percent      - percentage of treasure in caves.
   * @param wrap         - whether the dungeon is wrapping or non wrapping.
   * @param monsters     - number of otyughs in the dungeon
   * @param test         - creates a deterministic dungeon for testing purposes if true.
   */
  public MonsterHuntDungeonGame(int n, int m, int connectivity, float percent, boolean wrap,
                                int monsters, boolean test) {
    super(n, m, connectivity, percent, wrap, test);
    if (monsters < 1) {
      throw new IllegalArgumentException("dungeon should have at least 1 otyugh");
    }

    List<Location> caves = getCaves();
    List<Location> locations = getTunnels();
    locations.addAll(caves);

    if (caves.size() < monsters - 1) {
      throw new IllegalArgumentException("insufficient caves to add " + monsters + " Otyughs");
    }
    Random random = getRandom();
    DungeonCave[][] dungeon = getDungeon();
    Location end = getEnd();
    dungeon[end.getX()][end.getY()].getMonster().setHealth(MAX_HEALTH);
    caves.remove(end);
    // randomly select caves to which otyughs should be added.
    for (int i = 0; i < monsters - 1; i++) {
      int index = random.nextInt(caves.size());
      Location location = caves.get(index);
      caves.remove(index);
      if (!location.equals(getStartCave())) {
        dungeon[location.getX()][location.getY()].getMonster().setHealth(MAX_HEALTH);
      }
    }

    // randomly select locations (caves + tunnels) to which arrows should be added.
    double num = Math.ceil((percent * n * m) / 100);
    for (int i = 0; i < num; i++) {
      int index = random.nextInt(locations.size());
      Location location = locations.get(index);
      locations.remove(index);
      dungeon[location.getX()][location.getY()].setArrows(ADDITIONAL_ARROWS);
    }
    setDungeon(dungeon);
    setInitDungeon();
    this.monsters = monsters;
  }

  @Override
  DungeonCave newDungeonCave(Map<Direction, Location> neighbours) {
    return new MonsterCave(emptyTreasure(), neighbours, new Otyugh(0), 0);
  }

  @Override
  DungeonCave newDungeonCave(DungeonCave dungeonCave) {
    return new MonsterCave(dungeonCave);
  }

  @Override
  DungeonPlayer newPlayer(Location location) {
    return new SlayingPlayer(location, emptyTreasure(), START_ARROWS);
  }

  @Override
  DungeonPlayer newPlayer(DungeonPlayer player) {
    return new SlayingPlayer(player);
  }

  @Override
  public void movePlayer(Direction direction) {

    updatePlayerLocation(direction);

    // check if the destination cave has otyugh and end game if necessary.
    int x = getPlayerX();
    int y = getPlayerY();
    Random random = getRandom();
    Location end = getEnd();
    DungeonCave[][] dungeon = getDungeon();
    int health = dungeon[x][y].getMonster().getHealth();
    if (MIN_HEALTH < health && health < MAX_HEALTH) {
      if (random.nextDouble() < ESCAPE_THRESHOLD) {
        setGameOver();
        setStatus(LOSS);
      }
    }
    else if (health == MAX_HEALTH) {
      setGameOver();
      setStatus(LOSS);
    }
    else if (end.equals(getPlayerLocation())) {
      pickTreasure();
      pickUpArrows();
      setGameOver();
      setStatus(WIN);
    }
  }

  @Override
  public Shot shootArrow(Direction direction, int distance) {
    if (direction == null || distance < 1) {
      throw new IllegalArgumentException("Illegal direction/distance");
    }
    if (isGameOver()) {
      throw new IllegalArgumentException("game over, cannot shoot arrow");
    }
    DungeonPlayer player = getPlayer();
    int arrows = player.getArrows();
    if (arrows < 1) {
      return NOEFFECT;
    }
    DungeonCave[][] dungeon = getDungeon();
    player.setArrows(arrows - 1);
    setPlayer(player);
    Location arrowLocation = getPlayerLocation();
    if (dungeon[getPlayerX()][getPlayerY()].isCave()) {
      distance += 1;
    }
    while (true) {
      int x = arrowLocation.getX();
      int y = arrowLocation.getY();
      if (dungeon[x][y].isCave()) { // location cave (check if arrow can travel further)
        distance -= 1;
        if (distance == 0) {
          break;
        }
        Location destination = getNeighbourLocation(arrowLocation, direction);
        if (arrowLocation.equals(destination)) {
          return WASTE;
        }
        else {
          arrowLocation = destination;
        }
      }
      else { // location is tunnel (curve the arrow)
        if (arrowLocation.equals(getPlayerLocation())) {
          arrowLocation = getNeighbourLocation(arrowLocation, direction);
        }
        else {
          List<Direction> neighbours = getNeighbourDirections(arrowLocation);
          neighbours.remove(direction.opposite());
          direction = neighbours.get(0);
          arrowLocation = getNeighbourLocation(arrowLocation, direction);
        }
      }
    }
    int x = arrowLocation.getX();
    int y = arrowLocation.getY();
    int health = dungeon[x][y].getMonster().getHealth();
    if (health > MIN_HEALTH) {
      dungeon[x][y].getMonster().setHealth(health - DAMAGE);
      setDungeon(dungeon);
      return HIT;
    }
    return MISS;
  }

  @Override
  public void pickUpArrows() {
    if (isGameOver()) {
      throw new IllegalArgumentException("game over, cannot pickup arrows");
    }
    int x = getPlayerX();
    int y = getPlayerY();
    DungeonCave[][] dungeon = getDungeon();
    DungeonPlayer player = getPlayer();
    player.setArrows(player.getArrows() + dungeon[x][y].getArrows());
    dungeon[x][y].setArrows(0);
    setDungeon(dungeon);
    setPlayer(player);
  }

  @Override
  public Smell getMonsterSmell() {
    int count = 0;
    DungeonCave[][] dungeon = getDungeon();
    // if player enters the cave of a monster and escapes then smell is pungent.
    if (dungeon[getPlayerX()][getPlayerY()].hasMonster()) {
      return PUNGENT;
    }
    // single Otyugh, 1 position from the player's current location
    for (Direction d1 : getNeighbourDirections(getPlayerLocation())) {
      Location l1 = getNeighbourLocation(getPlayerLocation(), d1);
      if (dungeon[l1.getX()][l1.getY()].hasMonster()) {
        return PUNGENT;
      }
      // multiple Otyughs within 2 positions from the player's current location
      for (Direction d2 : getNeighbourDirections(l1)) {
        Location l2 = getNeighbourLocation(l1, d2);
        if (dungeon[l2.getX()][l2.getY()].hasMonster()) {
          count += 1;
        }
      }
    }
    if (count > 1) {
      return PUNGENT;
    }
    else if (count == 1) {
      return FAINT;
    }
    else {
      return NONE;
    }
  }

  @Override
  public int getPlayerArrows() {
    return getPlayer().getArrows();
  }

  @Override
  public int getCaveArrows() {
    int x = getPlayerX();
    int y = getPlayerY();
    DungeonCave[][] dungeon = getDungeon();
    return dungeon[x][y].getArrows();
  }

  @Override
  public String displayDungeon() {
    return "This a Text Adventure Game, displaying the dungeon will spoil the fun!!!";
  }

  @Override
  public int getMonsters() {
    return monsters;
  }
}
