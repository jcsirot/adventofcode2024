package org.chelonix.aoc2024.day11;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.chelonix.aoc2024.day10.Day10;
import org.junit.jupiter.api.Test;

class Day11Test {
  private static String PUZZLE_INPUT = """
     125 17""";

  @Test
  public void should_pass_part_1() {
    Day11 day11 = new Day11();
    String score = day11.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("55312");
  }

  @Test
  public void should_pass_part_2() {
    Day11 day11 = new Day11();
    String rating = day11.part2(PUZZLE_INPUT);
    assertThat(rating).isEqualTo("65601038650482");
  }

}