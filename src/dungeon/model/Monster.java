package dungeon.model;

interface Monster {

  /**
   * returns the health of a Monster.
   * @return - Monster's health.
   */
  int getHealth();

  /**
   * sets the Monster's health to given health.
   * @param health - health of the Monster.
   */
  void setHealth(int health);

}
