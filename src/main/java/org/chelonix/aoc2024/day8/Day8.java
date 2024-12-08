package org.chelonix.aoc2024.day8;

import java.util.Set;

public class Day8 {

  private AntennaMap parse(String input) {
    AntennaMap.Builder builder = AntennaMap.newBuilder();
    input.lines().forEach(builder::addLine);
    return builder.build();
  }

  public String part1(String input) {
    AntennaMap map = parse(input);
    Set<Coordinate> antinodes = map.computeAntinodes();
    return "%s".formatted(antinodes.size());
  }

  public String part2(String input) {
    AntennaMap map = parse(input);
    Set<Coordinate> antinodes = map.computeAntinodes2();
    return "%s".formatted(antinodes.size());
  }
}
