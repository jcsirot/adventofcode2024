package org.chelonix.aoc2024.day19;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day19Test {

  private static final String PUZZLE_INPUT = """
      r, wr, b, g, bwu, rb, gb, br
      
      brwrr
      bggr
      gbbr
      rrbgbr
      ubwu
      bwurrg
      brgr
      bbrgwb""";

  @Test
  public void should_pass_part_1() {
    Day19 day19 = new Day19();
    String score = day19.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("6");
  }

  @Test
  public void should_pass_part_2() {
    Day19 day19 = new Day19();
    String score = day19.part2(PUZZLE_INPUT);
    assertThat(score).isEqualTo("16");
  }
}