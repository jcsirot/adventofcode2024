package org.chelonix.aoc2024.day11;

import java.util.Arrays;
import org.chelonix.aoc2024.day10.TopographicMap;
import org.chelonix.aoc2024.day10.TopographicMap.Builder;

public class Day11 {

  private StoneLine parse(String input) {
    String[] numbers = input.split("\\s");
    return new StoneLine(Arrays.stream(numbers).mapToLong(Long::parseLong).toArray());
  }

  public String part1(String input) {
    StoneLine stones = parse(input);
    long count = stones.blink(25);
    return "%s".formatted(count);
  }

  public String part2(String input) {
    StoneLine stones = parse(input);
    long count = stones.blink(75);
    return "%s".formatted(count);
  }
}
