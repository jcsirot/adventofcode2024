package org.chelonix.aoc2024.day19;

import java.util.List;

public record Onsen(List<String> towels, List<String> designs) {

      public static Onsen parse(String input) {
          String[] parts = input.split("\n\n");
          return new Onsen(List.of(parts[0].split("\\s*,\\s*")), List.of(parts[1].split("\n")));
      }
}
