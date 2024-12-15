package org.chelonix.aoc2024.day13;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day13Test {

  private static final String PUZZLE_INPUT = """
      Button A: X+94, Y+34
      Button B: X+22, Y+67
      Prize: X=8400, Y=5400

      Button A: X+26, Y+66
      Button B: X+67, Y+21
      Prize: X=12748, Y=12176

      Button A: X+17, Y+86
      Button B: X+84, Y+37
      Prize: X=7870, Y=6450

      Button A: X+69, Y+23
      Button B: X+27, Y+71
      Prize: X=18641, Y=10279""";

  @Test
  public void should_pass_part_1() {
    Day13 day13 = new Day13();
    String score = day13.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("480");
  }

  @Test
  public void should_pass_part_2() {
    Day13 day13 = new Day13();
    String score = day13.part2(PUZZLE_INPUT);
    assertThat(score).isEqualTo("875318608908");
  }

}