package org.chelonix.aoc2024.day10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day10Test {
  private static String PUZZLE_INPUT = """
     89010123
     78121874
     87430965
     96549874
     45678903
     32019012
     01329801
     10456732""";

  @Test
  public void should_pass_part_1() {
    Day10 day10 = new Day10();
    String score = day10.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("36");
  }

  @Test
  public void should_pass_part_2() {
    Day10 day10 = new Day10();
    String rating = day10.part2(PUZZLE_INPUT);
    assertThat(rating).isEqualTo("81");
  }

}