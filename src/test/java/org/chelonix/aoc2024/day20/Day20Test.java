package org.chelonix.aoc2024.day20;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day20Test {

  private static final String PUZZLE_INPUT = """
      ###############
      #...#...#.....#
      #.#.#.#.#.###.#
      #S#...#.#.#...#
      #######.#.#.###
      #######.#.#...#
      #######.#.###.#
      ###..E#...#...#
      ###.#######.###
      #...###...#...#
      #.#####.#.###.#
      #.#...#.#.#...#
      #.#.#.#.#.#.###
      #...#...#...###
      ###############""";

  @Test
  public void should_pass_part_1() {
    Day20 day20 = new Day20();
    String score = day20.solve(PUZZLE_INPUT, 2, 20);
    assertThat(score).isEqualTo("5");
  }

  @Test
  public void should_pass_part_2() {
    Day20 day20 = new Day20();
    String score = day20.solve(PUZZLE_INPUT, 20, 74);
    assertThat(score).isEqualTo("7");
  }

}