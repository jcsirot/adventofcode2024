package org.chelonix.aoc2024.day18;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day18Test {

  private static final String PUZZLE_INPUT = """
      5,4
      4,2
      4,5
      3,0
      2,1
      6,3
      2,4
      1,5
      0,6
      3,3
      2,6
      5,1
      1,2
      5,5
      2,5
      6,5
      1,4
      0,4
      6,4
      1,1
      6,1
      1,0
      0,5
      1,6
      2,0""";

  @Test
  public void should_pass_part_1() {
    Day18 day18 = new Day18();
    String score = day18.part1(PUZZLE_INPUT, 12);
    assertThat(score).isEqualTo("22");
  }

  @Test
  public void should_pass_part_2() {
    Day18 day18 = new Day18();
    String score = day18.part2(PUZZLE_INPUT);
    assertThat(score).isEqualTo("6,1");
  }
}