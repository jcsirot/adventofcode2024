package org.chelonix.aoc2024.day10;

import org.chelonix.aoc2024.day10.TopographicMap.Builder;

public class Day10 {

  private TopographicMap parse(String input) {
    Builder builder = new Builder();
    input.lines().forEach(line -> builder.addLine(line));
    return builder.build();
  }

  public String part1(String input) {
    TopographicMap map = parse(input);
    int score = map.findPathScores();
    return "%s".formatted(score);
  }

  public String part2(String input) {
    TopographicMap map = parse(input);
    int rating = map.findPathRatings();
    return "%s".formatted(rating);
  }
}
