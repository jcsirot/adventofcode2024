package org.chelonix.aoc2024.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

  private Print parse(String input) {
    List<Rule> rules = new ArrayList<>();
    List<List<Integer>> updates = new ArrayList<>();
    input.lines().forEach(line -> {
      if (line.contains("|")) {
        int[] pages = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        rules.add(new Rule(pages[0], pages[1]));
      } else if (! line.isEmpty()) {
        updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
      }
    });
    return new Print(rules, updates);
  }

  public String part1(String input) {
    Print print = parse(input);
    int sum = print.updates().stream()
        .filter(print::validate)
        .mapToInt(update -> update.get(update.size()/2))
        .sum();
    return "%s".formatted(sum);
  }

  public String part2(String input) {
    Print print = parse(input);
    int sum = print.updates().stream()
        .filter(update -> !print.validate(update))
        .map(update -> print.fix(update))
        .mapToInt(update -> update.get(update.size()/2))
        .sum();
    return "%s".formatted(sum);
  }

}
