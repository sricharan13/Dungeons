package dungeon.model;

import java.util.Map;

// SlayingPlayer is an ExploringPlayer that can shoot arrows and kill monsters.
class SlayingPlayer extends ExploringPlayer {

  private int arrows;

  SlayingPlayer(Location location, Map<Treasure, Integer> treasure, int arrows) {
    super(location, treasure);
    this.arrows = arrows;
  }

  SlayingPlayer(DungeonPlayer player) {
    super(player);
    this.arrows = player.getArrows();
  }

  @Override
  public int getArrows() {
    return arrows;
  }

  @Override
  public void setArrows(int arrows) {
    this.arrows = arrows;
  }
}
