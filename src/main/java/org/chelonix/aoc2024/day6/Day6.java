package org.chelonix.aoc2024.day6;

import java.util.HashSet;
import java.util.Set;
import org.chelonix.aoc2024.day6.Map.Builder;

public class Day6 {

  private Map parse(String input) {
    Builder builder = Map.newBuilder();
    input.lines().forEach(builder::addLine);
    return builder.build();
  }

  public String part1(String input) {
    Map map = parse(input);
    Set<Coordinate> path = new HashSet<>();
    Position pos = new Position(map.getStart(), Direction.UP);
    path.add(pos.coordinate());
    while (true) {
      pos = nextPosition(pos, map);
      if (pos == null) break;
      path.add(pos.coordinate());
    }
    return "%s".formatted(path.size());
  }

  public String part2(String input) {
    Map map = parse(input);
    int loopCount = 0;
    for (int y = 0; y < map.getHeight(); y++) {
      for (int x = 0; x < map.getWidth(); x++) {
        Coordinate c = new Coordinate(y, x);
        if (map.containsObstacle(c) || map.getStart().equals(c)) {
          continue;
        }
        Map newMap = map.addObstacle(c);
        Set<Position> path = new HashSet<>();
        Position pos = new Position(map.getStart(), Direction.UP);
        path.add(pos);
        while (true) {
          pos = nextPosition(pos, newMap);
          if (pos == null) break;
          if(! path.add(pos)) {
            loopCount++;
            break;
          }
        }
      }
    }
    return "%s".formatted(loopCount);
  }

  private Position nextPosition(Position pos, Map map) {
    Position nextPos = pos.step();
    if (map.isOutside(nextPos.coordinate())) {
      return null;
    }
    if (!map.containsObstacle(nextPos.coordinate())) {
      return nextPos;
    } else {
      return nextPosition(pos.turn(), map);
    }
  }
}
