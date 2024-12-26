package org.chelonix.aoc2024.day25;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.chelonix.aoc2024.day24.Day24;
import org.junit.jupiter.api.Test;

class Day25Test {

  private static final String PUZZLE_INPUT = """
      #####
      .####
      .####
      .####
      .#.#.
      .#...
      .....
      
      #####
      ##.##
      .#.##
      ...##
      ...#.
      ...#.
      .....
      
      .....
      #....
      #....
      #...#
      #.#.#
      #.###
      #####
      
      .....
      .....
      #.#..
      ###..
      ###.#
      ###.#
      #####
      
      .....
      .....
      .....
      #....
      #.#..
      #.#.#
      #####""";

  @Test
  public void should_pass_part_1() {
    Day25 day25 = new Day25();
    String matches = day25.part1(PUZZLE_INPUT);
    assertThat(matches).isEqualTo("3");
  }


}