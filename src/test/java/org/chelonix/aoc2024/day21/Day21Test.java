package org.chelonix.aoc2024.day21;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day21Test {

  private static final String PUZZLE_INPUT = """
      029A
      980A
      179A
      456A
      379A""";

  @Test
  public void should_pass_part_1() {
    Day21 day21 = new Day21();
    String score = day21.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("126384");
  }
}