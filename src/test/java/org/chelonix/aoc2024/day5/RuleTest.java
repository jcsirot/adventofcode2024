package org.chelonix.aoc2024.day5;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class RuleTest {

  @Test
  void should_be_valid() {
    Rule r = new Rule(1, 2);
    List<Integer> update = List.of(5, 1, 9, 2);
    assertThat(r.validate(update));
  }

  @Test
  void should_be_invalid() {
    Rule r = new Rule(1, 2);
    List<Integer> update = List.of(2, 14, 9, 1);
    assertThat(r.validate(update));
  }
}