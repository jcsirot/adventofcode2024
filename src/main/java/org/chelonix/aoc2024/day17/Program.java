package org.chelonix.aoc2024.day17;

import java.util.List;

public record Program(long a, long b, long c, List<Integer> code) {

  public Program(String input) {
    this(
        Long.parseLong(input.split("\n")[0].split(": ")[1]),
        Long.parseLong(input.split("\n")[1].split(": ")[1]),
        Long.parseLong(input.split("\n")[2].split(": ")[1]),
        List.of(input.split("\n")[4].split(": ")[1].split(",")).stream()
            .map(Integer::parseInt)
            .toList()
    );
  }
}