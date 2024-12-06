package org.chelonix.aoc2024.day6;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day6Test {
  private static String PUZZLE_INPUT = """
      ....#.....
      .........#
      ..........
      ..#.......
      .......#..
      ..........
      .#..^.....
      ........#.
      #.........
      ......#...""";

  @Test
  public void should_pass_part_1() {
    Day6 day6 = new Day6();
    String count = day6.part1(PUZZLE_INPUT);
    assertThat(count).isEqualTo("41");
  }

  @Test
  public void should_pass_part_2() {
    Day6 day6 = new Day6();
    String count = day6.part2(PUZZLE_INPUT);
    assertThat(count).isEqualTo("6");
  }
}