package org.chelonix.aoc2024.day12;

import org.chelonix.aoc2024.day12.Garden.Builder;

public class Day12 {

  private Garden parse(String input) {
    Builder builder = Garden.builder();
    input.lines().forEach(builder::addLine);
    return builder.build();
  }

  public String part1(String input) {
    Garden garden = parse(input);
    long cost = garden.computeCost();
    return "%s".formatted(cost);
  }

  public String part2(String input) {
    Garden garden = parse(input);
    long cost = garden.computeCostWithDiscount();
    return "%s".formatted(cost);
  }
}
