package dungeon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// a package private class that generates a dungeon with given configuration.
class PathGenerator {

  // takes the dungeon configuration and controls the creation of dungeon.
  static Map<Location, Map<Direction, Location>> generatePaths(int n, int m, int connectivity,
                                                               boolean wrap, Random random) {
    // creates a list of locations nxm (x, y) coordinates. each coordinate represents (i, j)th
    // cave in dungeon.
    List<Location> locations = getAllLocations(n, m);
    // creates a list of potential paths between caves.
    List<Path> allPaths = getAllPaths(n, m, locations, wrap);
    if (allPaths.size() < (locations.size() - 1) + connectivity) {
      throw new IllegalArgumentException("Dungeon not possible, insufficient paths");
    }
    // uses modified Kruskal's Algorithm to generate paths between caves with given
    // interconnectivity.
    return getDungeonPaths(locations, allPaths, connectivity, random);
  }

  // Helper class used in the creation of dungeon paths. This class performs Union-Find operations.
  private static class UnionFind {
    Map<Location, Location> parent = new HashMap<>();

    // creates disjoint sets of locations.
    void makeSet(List<Location> locations) {
      for (Location location : locations) {
        parent.put(location, location);
      }
    }

    // finds the set of a location.
    private Location find(Location location) {
      if (parent.get(location) == location) {
        return location;
      }
      return find(parent.get(location));
    }

    // combines two disjoint sets.
    private void union(Location a, Location b) {
      Location x = find(a);
      Location y = find(b);
      parent.put(x, y);
    }
  }

  private static void add(Map<Location, Map<Direction, Location>> dungeonPaths,
                          Location l1, Direction direction, Location l2) {
    Map<Direction, Location> value;
    if (dungeonPaths.containsKey(l1)) {
      value = dungeonPaths.get(l1);
    }
    else {
      value = new HashMap<>();
    }
    value.put(direction, l2);
    dungeonPaths.put(l1, value);
  }

  // helper method to create dungeon paths. Follows a modified Kruskal's Algorithm to create
  // connectivity between caves in the dungeon.
  private static Map<Location, Map<Direction, Location>>
      getDungeonPaths(List<Location> locations, List<Path> allPaths, int connectivity,
                     Random random) {
    // paths between caves in dungeon.
    List<Path> paths = new ArrayList<>();
    Map<Location, Map<Direction, Location>> dungeonPaths = new HashMap<>();
    // a list leftover paths that will be used to increase interconnectivity.
    List<Path> leftOverPaths = new ArrayList<>();

    UnionFind unionFind = new UnionFind();
    unionFind.makeSet(locations);

    while (paths.size() != locations.size() - 1) {

      // randomly pick a path from the potential paths.
      int i = random.nextInt(allPaths.size());
      Path path = allPaths.get(i);
      allPaths.remove(i);

      // find the parents of path's source and destination.
      Location x = unionFind.find(path.getSrc());
      Location y = unionFind.find(path.getDest());

      // if parents are different, add path to dungeon.
      if (!x.equals(y)) {
        paths.add(path);
        add(dungeonPaths, path.getSrc(), path.getDirectionToDest(), path.getDest());
        add(dungeonPaths, path.getDest(), path.getDirectionToSrc(), path.getSrc());
        unionFind.union(x, y);
      }
      // else add to leftover paths (will be used to increase interconnectivity)
      else {
        leftOverPaths.add(path);
      }
    }
    leftOverPaths.addAll(allPaths);

    // add paths from leftover paths to increase the interconnectivity if interconnectivity > 0
    if (connectivity > 0) {
      int counter = 0;
      while (counter < connectivity) {
        int i = random.nextInt(leftOverPaths.size());
        Path path = leftOverPaths.get(i);
        leftOverPaths.remove(i);
        paths.add(path);
        add(dungeonPaths, path.getSrc(), path.getDirectionToDest(), path.getDest());
        add(dungeonPaths, path.getDest(), path.getDirectionToSrc(), path.getSrc());
        counter++;
      }
    }
    return dungeonPaths;
  }

  private static List<Location> getAllLocations(int n, int m) {
    List<Location> locations = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        locations.add(new DungeonLocation(i, j));
      }
    }
    return locations;
  }

  private static boolean isValid(int n, int m, Location location) {
    return location.getX() > -1 && location.getX() < n
            && location.getY() > -1 && location.getY() < m;
  }

  private static DungeonLocation wrapLocation(int n, int m, DungeonLocation location) {
    int x = location.getX();
    int y = location.getY();
    if (x == -1) {
      x = n - 1;
    }
    if (x == n) {
      x = 0;
    }
    if (y == -1) {
      y = m - 1;
    }
    if (y == m) {
      y = 0;
    }
    return new DungeonLocation(x, y);
  }

  private static List<Path> getValidPaths(int n, int m, Location src, boolean wrap) {
    List<Path> validPaths = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      DungeonLocation dest;
      dest = new DungeonLocation(src.getX() + direction.getX(), src.getY() + direction.getY());
      if (wrap) {
        dest = wrapLocation(n, m, dest);
      }
      if (isValid(n, m, dest)) {
        validPaths.add(new Path(src, dest, direction, direction.opposite()));
      }
    }
    return validPaths;
  }

  // creates a list of all possible paths in the dungeon based on wrapping or non wrapping
  // specification.
  private static List<Path> getAllPaths(int n, int m, List<Location> locations,
                                        boolean wrap) {
    List<Path> potentialPaths = new ArrayList<>();
    for (Location location : locations) {
      for (Path path : getValidPaths(n, m, location, wrap)) {
        if (!potentialPaths.contains(path)) {
          potentialPaths.add(path);
        }
      }
    }
    return potentialPaths;
  }
}
