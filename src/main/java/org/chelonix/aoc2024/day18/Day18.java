package org.chelonix.aoc2024.day18;

public class Day18 {

  private Memory parse(String input) {
    return new Memory(input);
  }

  public String part1(String input) {
    return part1(input, 1024);
  }

  String part1(String input, int steps) {
    Memory memory = parse(input);
    int pathLen = memory.shortestPath(steps);
    return "%s".formatted(pathLen);
  }

  public String part2(String input) {
    Memory memory = parse(input);
    String blocker = memory.findFirstBlocker();
    return "%s".formatted(blocker);
  }
}
