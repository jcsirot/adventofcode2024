package org.chelonix.aoc2024.day13;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 {

  private List<LinearSystem> parse(String input) {
    List<String> lines = input.lines()
        .filter(line -> !line.isEmpty())
        .collect(Collectors.toList());

    return IntStream.range(0, lines.size() / 3)
        .mapToObj(i -> lines.subList(i * 3, i * 3 + 3))
        .map(LinearSystem::parse)
        .collect(Collectors.toList());
  }

  public String part1(String input) {
    List<LinearSystem> systems = parse(input);
    long tokenCount = systems.stream()
        .map(LinearSystem::solve)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .mapToLong(solution -> solution.x()*3 + solution.y())
        .sum();
    return "%s".formatted(tokenCount);
  }

  public String part2(String input) {
    List<LinearSystem> systems = parse(input);
    long tokenCount = systems.stream()
        .map(LinearSystem::solveLonger)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .mapToLong(solution -> solution.x()*3 + solution.y())
        .sum();
    return "%s".formatted(tokenCount);
  }
}
