package dungeon.model;

import java.util.Map;

class MonsterCave extends TreasureCave {

  private final Monster monster;
  private int arrows;

  MonsterCave(Map<Treasure, Integer> treasure, Map<Direction, Location> neighbours, Monster monster,
              int arrows) {
    super(treasure, neighbours);
    this.monster = monster;
    this.arrows = arrows;
  }

  MonsterCave(DungeonCave dungeonCave) {
    super(dungeonCave);
    this.monster = new Otyugh(dungeonCave.getMonster());
    this.arrows = dungeonCave.getArrows();
  }


  @Override
  public Monster getMonster() {
    return monster;
  }

  @Override
  public boolean hasMonster() {
    return monster.getHealth() > 0;
  }

  @Override
  public int getArrows() {
    return arrows;
  }

  @Override
  public void setArrows(int arrows) {
    this.arrows = arrows;
  }

  @Override
  public String displayCave() {
    return String.format("treasure: %s, neighbours: %s, otyugh: %s, arrows: %s",
            getTreasure(), getNeighbours(), hasMonster(), getArrows());
  }
}
