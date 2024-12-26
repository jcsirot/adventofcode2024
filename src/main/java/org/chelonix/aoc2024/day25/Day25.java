package org.chelonix.aoc2024.day25;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day25 {

  private record KeysAndLockers(List<int[]> keys, List<int[]> lockers) {

  }

  private KeysAndLockers parse(String input) {
    String[] schematics = input.split("\n\n");
    List<int[]> keys = new ArrayList<>();
    List<int[]> lockers = new ArrayList<>();
    for (String s : schematics) {
      int[] heights = new int[]{-1, -1, -1, -1, -1};
      if (s.startsWith("#")) {
        // Locker
        s.lines().forEach(line -> {
          for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
              heights[i]++;
            }
          }
        });
        lockers.add(heights);
      } else {
        // Key
        s.lines().forEach(line -> {
          for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
              heights[i]++;
            }
          }
        });
        keys.add(heights);
      }
    }
    return new KeysAndLockers(keys, lockers);
  }

  public String part1(String input) {
    KeysAndLockers keysAndLockers = parse(input);
    int match = 0;
    key: for (int[] key : keysAndLockers.keys()) {
      locker: for (int[] locker : keysAndLockers.lockers()) {
        match += matches(key, locker) ? 1 : 0;
      }
    }
    return "%s".formatted(match);
  }

  private boolean matches(int[] key, int[] locker) {
    for (int i = 0; i < 5; i++) {
      if (key[i] + locker[i] > 5) {
        return false;
      }
    }
    return true;
  }

  public String part2(String input) throws IOException {
    return "%s".formatted("");
  }
}

