package org.chelonix.aoc2024.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {

  private List<String> parse(String input) {
    return input.lines().toList();
  }

  private String runPuzzle(String input, int padCount) {
    List<String> codes = parse(input);
    long complexity = 0;
    for (String code : codes) {
      long length = 0;
      NumPad numPad = new NumPad();
      for (char c : code.toCharArray()) {
        String to = String.valueOf(c);
        List<String> allPaths = numPad.generatePaths(to);
        long len = Long.MAX_VALUE;
        for (String path : allPaths) {
          len = Math.min(len, findShortest(path, padCount));
        }
        length += len;
      }
      complexity += Long.valueOf(code.substring(0, 3), 10) * length;
    }
    return "%s".formatted(complexity);
  }

  private Map<String, Long> cache = new HashMap<>();

  private long findShortest(String path, int level) {
    if (level == 0) {
      return path.length();
    }
    if (cache.containsKey(path + "_" + level)) {
      return cache.get(path + "_" + level);
    }
    long length = 0;
    DirPad pad = new DirPad();
    for (char c : path.toCharArray()) {
      long len = Long.MAX_VALUE;
      List<String> allPath = pad.generatePaths(String.valueOf(c));
      for (String p : allPath) {
        len = Math.min(len, findShortest(p, level - 1));
      }
      length += len;
    }
    cache.put(path + "_" + level, length);
    return length;
  }

  public String part1(String input) {
    return runPuzzle(input, 2);
  }

  public String part2(String input) {
    return runPuzzle(input, 25);
  }
}
