package dungeon.model;

import static dungeon.model.Outcome.Status;
import static dungeon.model.Outcome.Status.INPROGRESS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// AbstractDungeonGame provides the common functionality for DungeonGame.
abstract class AbstractDungeonGame implements DungeonGame {

  private final int n;
  private final int m;
  private final int connectivity;
  private final boolean wrap;
  private final boolean test;
  private final List<Location> caves;
  private final List<Location> tunnels;
  private final Location start;
  private final Location end;
  private final Random random;
  private DungeonCave[][] dungeon;
  private DungeonCave[][] initDungeon;
  private DungeonPlayer player;
  private boolean gameOver;
  private List<Location> visited;
  private Status status;

  AbstractDungeonGame(int n, int m, int connectivity, boolean wrap, boolean test) {
    if (n < 1 || m < 1 || connectivity < 0) {
      throw new IllegalArgumentException("Unexpected specifications");
    }
    Random random = new Random();
    if (test) {
      random.setSeed(0);
    }
    Map<Location, Map<Direction, Location>> paths;
    paths = PathGenerator.generatePaths(n, m, connectivity, wrap, random);
    List<Location> caves = new ArrayList<>();
    List<Location> tunnels = new ArrayList<>();
    DungeonCave[][] dungeon = new DungeonCave[n][m];
    for (Location location : paths.keySet()) {
      // determine if a location is cave or tunnel
      Map<Direction, Location> neighbours = paths.get(location);
      int entrances = neighbours.size();
      if (entrances == 2) {
        tunnels.add(location);
      }
      else {
        caves.add(location);
      }
      dungeon[location.getX()][location.getY()] = newDungeonCave(neighbours);
    }
    if (caves.size() < 2) {
      throw new IllegalArgumentException("Not enough caves in dungeon");
    }
    Map<Location, List<Location>> startEndMap = new HashMap<>();
    for (Location start : caves) {
      List<Location> endCaves = new ArrayList<>();
      for (Location end : caves) {
        if (start.getManhattanDistance(end) >= 5) {
          endCaves.add(end);
        }
      }
      if (endCaves.size() > 0) {
        startEndMap.put(start, endCaves);
      }
    }
    if (startEndMap.size() < 1) {
      throw new IllegalArgumentException("Insufficient dimensions");
    }
    this.n = n;
    this.m = m;
    this.connectivity = connectivity;
    this.wrap = wrap;
    this.dungeon = dungeon;
    setInitDungeon();
    this.caves = caves;
    this.tunnels = tunnels;
    List<Location> startLocations = new ArrayList<>(startEndMap.keySet());
    this.start = startLocations.get(random.nextInt(startLocations.size()));
    List<Location> endLocations = startEndMap.get(start);
    this.end = endLocations.get(random.nextInt(endLocations.size()));
    this.player = newPlayer(start);
    this.visited = new ArrayList<>(List.of(start));
    this.random = random;
    this.gameOver = false;
    this.status = INPROGRESS;
    this.test = test;
  }

  public int getConnectivity() {
    return connectivity;
  }

  public boolean isWrap() {
    return wrap;
  }

  void addToVisited(Location location) {
    if (!visited.contains(location)) {
      visited.add(location);
    }
  }

  @Override
  public boolean isTest() {
    return test;
  }

  @Override
  public Status getStatus() {
    return status;
  }

  void setStatus(Status status) {
    this.status = status;
  }

  void validateLocation(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("location cannot be null");
    }
  }

  void validateDirection(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("direction cannot be null");
    }
  }

  @Override
  public int getN() {
    return n;
  }

  @Override
  public int getM() {
    return m;
  }

  DungeonCave[][] getDungeon() {
    DungeonCave[][] dungeonCopy = new DungeonCave[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        dungeonCopy[i][j] = newDungeonCave(dungeon[i][j]);
      }
    }
    return dungeonCopy;
  }

  void setInitDungeon() {
    this.initDungeon = getDungeon();
  }

  void setDungeon(DungeonCave[][] dungeon) {
    this.dungeon = dungeon;
  }

  List<Location> getCaves() {
    return new ArrayList<>(caves);
  }

  List<Location> getTunnels() {
    return new ArrayList<>(tunnels);
  }

  Location getEnd() {
    return end;
  }

  DungeonPlayer getPlayer() {
    return newPlayer(player);
  }

  void setPlayer(DungeonPlayer player) {
    this.player = player;
  }

  Random getRandom() {
    return random;
  }

  void setGameOver() {
    this.gameOver = true;
  }

  abstract DungeonCave newDungeonCave(Map<Direction, Location> neighbours);

  abstract DungeonCave newDungeonCave(DungeonCave dungeonCave);

  abstract DungeonPlayer newPlayer(Location location);

  abstract DungeonPlayer newPlayer(DungeonPlayer player);

  int getPlayerX() {
    return getPlayerLocation().getX();
  }

  int getPlayerY() {
    return getPlayerLocation().getY();
  }

  // for the given location, returns the directions in which neighbours exist.
  List<Direction> getNeighbourDirections(Location location) {
    validateLocation(location);
    int x = location.getX();
    int y = location.getY();
    return new ArrayList<>(dungeon[x][y].getNeighbours().keySet());
  }

  // for the given location, returns the neighbour cave's location in given direction.
  // if there is no neighbour cave in given direction, returns itself.
  Location getNeighbourLocation(Location location, Direction direction) {
    validateLocation(location);
    validateDirection(direction);
    int x = location.getX();
    int y = location.getY();
    Map<Direction, Location> neighbours = dungeon[x][y].getNeighbours();
    if (neighbours.containsKey(direction)) {
      return neighbours.get(direction);
    }
    return location;
  }

  // updates player location by moving player in the given direction.
  void updatePlayerLocation(Direction direction) {
    validateDirection(direction);
    if (gameOver) {
      throw new IllegalArgumentException("game over, cannot move player");
    }
    Location destination = getNeighbourLocation(getPlayerLocation(), direction);
    player.setLocation(destination);
    addToVisited(destination);
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public Location getStartCave() {
    return start;
  }

  @Override
  public Location getEndCave() {
    return end;
  }

  @Override
  public Location getPlayerLocation() {
    return player.getLocation();
  }

  @Override
  public List<Direction> getPossibleMoves() {
    return getNeighbourDirections(getPlayerLocation());
  }

  @Override
  public String displayDungeon() {
    DungeonCave[][] dungeon = getDungeon();
    StringBuilder dungeonString = new StringBuilder();
    for (int i = 0; i < getN(); i++) {
      for (int j = 0; j < getM(); j++) {
        dungeonString.append(String.format("(%s, %s): %s\n", i, j, dungeon[i][j].displayCave()));
      }
    }
    return dungeonString.toString();
  }

  @Override
  public Cave[][] getDungeonState() {
    return getDungeon();
  }

  @Override
  public List<Location> getVisited() {
    return new ArrayList<>(visited);
  }

  @Override
  public void resetGame() {
    dungeon = initDungeon;
    setInitDungeon();
    player = newPlayer(start);
    status = INPROGRESS;
    gameOver = false;
    visited = new ArrayList<>(List.of(start));
  }
}

