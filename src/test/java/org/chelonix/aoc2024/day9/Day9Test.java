package org.chelonix.aoc2024.day9;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day9Test {
  private static String PUZZLE_INPUT = """
        2333133121414131402""";

  @Test
  public void should_pass_part_1() {
    Day9 day9 = new Day9();
    String count = day9.part1(PUZZLE_INPUT);
    assertThat(count).isEqualTo("1928");
  }

  @Test
  public void should_pass_part_2() {
    Day9 day9 = new Day9();
    String count = day9.part2(PUZZLE_INPUT);
    assertThat(count).isEqualTo("2858");
  }
}