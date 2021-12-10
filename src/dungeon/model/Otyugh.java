package dungeon.model;

class Otyugh implements Monster {

  private int health;

  Otyugh(int health) {
    this.health = health;
  }

  Otyugh(Monster otyugh) {
    this.health = otyugh.getHealth();
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void setHealth(int health) {
    this.health = health;
  }
}
