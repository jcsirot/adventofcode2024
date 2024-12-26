package org.chelonix.aoc2024.day23;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;

class Day23Test {

  private static final String PUZZLE_INPUT = """
      kh-tc
      qp-kh
      de-cg
      ka-co
      yn-aq
      qp-ub
      cg-tb
      vc-aq
      tb-ka
      wh-tc
      yn-cg
      kh-ub
      ta-co
      de-co
      tc-td
      tb-wq
      wh-td
      ta-ka
      td-qp
      aq-cg
      wq-ub
      ub-vc
      de-ta
      wq-aq
      wq-vc
      wh-yn
      ka-de
      kh-ta
      co-tc
      wh-qp
      tb-vc
      td-yn""";

  private static final String PUZZLE_INPUT_2 = """
      A-B
      A-C
      B-C
      -B-D
      C-D
      D-E""";

  @Test
  public void should_pass_part_1() {
    Day23 day23 = new Day23();
    String score = day23.part1(PUZZLE_INPUT);
    assertThat(score).isEqualTo("7");
  }

  @Test
  public void should_pass_part_2() {
    Day23 day23 = new Day23();
    String password = day23.part2(PUZZLE_INPUT);
    assertThat(password).isEqualTo("co,de,ka,ta");
  }

}