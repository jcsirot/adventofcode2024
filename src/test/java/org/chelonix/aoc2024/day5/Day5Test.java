package org.chelonix.aoc2024.day5;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day5Test {

  private static String PUZZLE_INPUT = """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13

      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47""";

    @Test
    public void should_pass_part_1() {
      Day5 day5 = new Day5();
      String count = day5.part1(PUZZLE_INPUT);
      assertThat(count).isEqualTo("143");
    }

  @Test
  public void should_pass_part_2() {
    Day5 day5 = new Day5();
    String count = day5.part2(PUZZLE_INPUT);
    assertThat(count).isEqualTo("123");
  }


}