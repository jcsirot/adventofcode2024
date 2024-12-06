package org.chelonix.aoc2024.day5;

import java.util.List;

public record Print(List<Rule> rules, List<List<Integer>> updates) {

  public boolean validate(List<Integer> update) {
    for (Rule rule: rules) {
      if (!rule.validate(update)) {
        return false;
      }
    }
    return true;
  }

  public List<Integer> fix(List<Integer> update) {
    for (Rule rule: rules) {
      if (!rule.validate(update)) {
        List<Integer> fixed = rule.fix(update);
        return fix(fixed);
      }
    }
    return update;
  }
}
