import dungeon.model.Cave;
import dungeon.model.Direction;
import dungeon.model.Location;
import dungeon.model.MonsterDungeonGame;
import dungeon.model.Outcome;
import dungeon.model.Treasure;

import java.util.List;
import java.util.Map;

class MockModel implements MonsterDungeonGame {

  private final StringBuilder log;

  MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void movePlayer(Direction direction) {
    log.append("model - moving player ").append(direction).append("\n");
  }

  @Override
  public void resetGame() {
    log.append("model - resetting game\n");
  }

  @Override
  public void pickUpArrows() {
    log.append("model - picking up arrows\n");
  }

  @Override
  public Outcome.Shot shootArrow(Direction direction, int distance) {
    log.append("model - shooting arrow ").append(direction).append(" ")
            .append("over ").append(distance).append(" caves\n");
    return null;
  }

  @Override
  public void pickTreasure() {
    log.append("model - picking up treasure\n");
  }

  @Override
  public Cave[][] getDungeonState() {
    log.append("model - returning dungeon state\n");
    return new Cave[0][];
  }

  @Override
  public List<Location> getVisited() {
    log.append("model - returning visited\n");
    return null;
  }

  @Override
  public String displayDungeon() {
    log.append("model - displaying dungeon\n");
    return null;
  }

  @Override
  public Location getStartCave() {
    log.append("model - returning start cave\n");
    return null;
  }

  @Override
  public Location getEndCave() {
    log.append("model - returning end cave\n");
    return null;
  }

  @Override
  public Location getPlayerLocation() {
    log.append("model - returning player location\n");
    return null;
  }

  @Override
  public List<Direction> getPossibleMoves() {
    log.append("model - returning possible moves\n");
    return null;
  }

  @Override
  public boolean isGameOver() {
    log.append("model - returning game over\n");
    return false;
  }

  @Override
  public Map<Treasure, Integer> getCaveTreasure() {
    log.append("model - returning cave treasure\n");
    return null;
  }

  @Override
  public Map<Treasure, Integer> getPlayerTreasure() {
    log.append("model - returning player treasure\n");
    return null;
  }

  @Override
  public Outcome.Smell getMonsterSmell() {
    log.append("model - returning monster smell\n");
    return null;
  }

  @Override
  public int getCaveArrows() {
    log.append("model - returning cave arrows\n");
    return 0;
  }

  @Override
  public int getPlayerArrows() {
    log.append("model - returning player arrows\n");
    return 0;
  }

  @Override
  public Outcome.Status getStatus() {
    log.append("model - returning status\n");
    return null;
  }

  @Override
  public int getN() {
    log.append("model - returning n\n");
    return 0;
  }

  @Override
  public int getM() {
    log.append("model - returning m\n");
    return 0;
  }

  @Override
  public int getConnectivity() {
    log.append("model - returning connectivity\n");
    return 0;
  }

  @Override
  public boolean isWrap() {
    log.append("model - returning wrap\n");
    return false;
  }

  @Override
  public int getItemPercent() {
    log.append("model - returning item percent\n");
    return 0;
  }

  @Override
  public int getMonsters() {
    log.append("model - returning monsters\n");
    return 0;
  }

  @Override
  public boolean isTest() {
    log.append("model - returning test\n");
    return false;
  }
}
