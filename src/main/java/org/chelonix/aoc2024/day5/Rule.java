package org.chelonix.aoc2024.day5;

import java.util.ArrayList;
import java.util.List;

public record Rule(int before, int after) {

  public boolean validate(List<Integer> update) {
    for (int i = 0; i < update.size() - 1; i++) {
      for (int j = i+1; j < update.size(); j++) {
        if (!isValid(update.get(i), update.get(j))) {
          return false;
        }
      }
    }
    return true;
  }

  List<Integer> fix(List<Integer> update) {
    if (!validate(update)) {
      List<Integer> fixed = new ArrayList<>();
      fixed.addAll(update);
      int i = fixed.indexOf(before);
      int j = fixed.indexOf(after);
      int tmp = fixed.get(i);
      fixed.set(i, fixed.get(j));
      fixed.set(j, tmp);
      // System.out.println(update + " -> " + fixed);
      return fixed;
    } else {
      return update;
    }
  }

  private boolean isValid(int first, int second) {
    return !(first == after && second == before);
  }
}
