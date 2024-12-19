package org.chelonix.aoc2024.day19;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Day19 {

  private Onsen parse(String input) {
    return Onsen.parse(input);
  }

  public String part1(String input) {
    Onsen onsen = parse(input);
    int possibleDesigns = 0;
    Map<String, Boolean> cache = new HashMap<>();
    for (String design : onsen.designs()) {
      if (isValidDesign(design, onsen, cache)) {
        possibleDesigns++;
      }
    }
    return "%s".formatted(possibleDesigns);
  }

  private boolean isValidDesign(String design, Onsen onsen, Map<String, Boolean> cache) {
    if (design.isEmpty()) {
      return true;
    }
    if (cache.containsKey(design)) {
      return cache.get(design);
    }

    for (String towel : onsen.towels()) {
      if (design.startsWith(towel)) {
        if (isValidDesign(design.substring(towel.length()), onsen, cache)) {
          cache.put(design, true);
          return true;
        }
      }
    }

    cache.put(design, false);
    return false;
  }

  public String part2(String input) {
    Onsen onsen = parse(input);
    BigInteger assemblyCount = BigInteger.ZERO;
    Map<String, BigInteger> cache = new HashMap<>();
    for (String design : onsen.designs()) {
      assemblyCount = assemblyCount.add(countPossibleAssembly(design, onsen, cache));
    }
    return "%s".formatted(assemblyCount);
  }

  private BigInteger countPossibleAssembly(String design, Onsen onsen, Map<String, BigInteger> cache) {
    if (design.isEmpty()) {
      return BigInteger.ONE;
    }
    if (cache.containsKey(design)) {
      return cache.get(design);
    }

    BigInteger totalWays = BigInteger.ZERO;
    for (String towel : onsen.towels()) {
      if (design.startsWith(towel)) {
        totalWays = totalWays.add(countPossibleAssembly(design.substring(towel.length()), onsen, cache));
      }
    }
    cache.put(design, totalWays);
    return totalWays;
  }
}
