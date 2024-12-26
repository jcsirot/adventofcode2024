package org.chelonix.aoc2024.day20;

import java.util.List;

public class Day20 {

  private RaceTrack parse(String input) {
    return RaceTrack.parse(input);
  }

  public String part1(String input) {
    return solve(input, 2, 100);
  }

  public String part2(String input) {
    return solve(input, 20, 100);
  }

  String solve(String input, int cheat, int saving) {
    RaceTrack raceTrack = parse(input);
    List<Coordinate> initPath = raceTrack.findShortestPath();
    long fastestPaths = 0;
    for (int i = 0; i < initPath.size() - 1; i++) {
      for (int j = i + saving; j < initPath.size(); j++) {
        Coordinate c1 = initPath.get(i);
        Coordinate c2 = initPath.get(j);
        if (c1.manhattan(c2) <= cheat) {
          int shortcut = (j - i + 1) - c1.manhattan(c2);
          if (shortcut > saving) {
            fastestPaths++;
          }
        }
      }
    }
    return "%s".formatted(fastestPaths);
  }
}
