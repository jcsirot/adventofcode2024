package org.chelonix.aoc2024.day4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.chelonix.aoc2024.day2.Day2;
import org.junit.jupiter.api.Test;

class Day4Test {
  private static String PUZZLE_DATA = """
     MMMSXXMASM
     MSAMXMSMSA
     AMXSXMAAMM
     MSAMASMSMX
     XMASAMXAMM
     XXAMMXXAMA
     SMSMSASXSS
     SAXAMASAAA
     MAMMMXMMMM
     MXMXAXMASX""";

  @Test
  public void should_pass_part_1() {
    Day4 day4 = new Day4();
    String count = day4.part1(PUZZLE_DATA);
    assertThat(count).isEqualTo("18");
  }

  @Test
  public void should_pass_part_2() {
    Day4 day4 = new Day4();
    String count = day4.part2(PUZZLE_DATA);
    assertThat(count).isEqualTo("9");
  }
}