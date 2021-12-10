import dungeon.controller.DungeonGameViewController;
import dungeon.view.DungeonGameGui;

class MockView implements DungeonGameGui {

  private final StringBuilder log;

  MockView(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void addListener(DungeonGameViewController listener) {
    log.append("view - listener added\n");
    //listener.handleInput(input);
  }

  @Override
  public void makeVisible(String visibility) {
    log.append("view - making ").append(visibility).append(" visible\n");
  }

  @Override
  public void refresh() {
    log.append("view - refresh\n");
  }

  @Override
  public void destroy() {
    log.append("view - destroy\n");
  }

  @Override
  public void showOutcomeMessage(String message) {
    log.append("view - showing outcome message: ").append(message).append("\n");
  }

  @Override
  public void showErrorMessage(String message) {
    log.append("view - showing error message: ").append(message).append("\n");
  }
}
