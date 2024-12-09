package org.chelonix.aoc2024.day9;

import org.chelonix.aoc2024.day9.MemoryMap.Builder;

public class Day9 {

  private MemoryMap parse(String input) {
    input = input.trim(); // fix added "\n"
    Builder builder = new MemoryMap.Builder();
    for (int i = 0; i < input.length(); i++) {
      int size = Integer.parseInt(input.substring(i, i + 1));
      if (i % 2 == 0) {
        builder.addFile(i >> 1, size);
      } else {
        builder.addFree(size);
      }
    }
    return builder.build();
  }

  public String part1(String input) {
    MemoryMap blocks = parse(input);
    blocks.compactAndFragment();
    return "%s".formatted(blocks.checksum());
  }

  public String part2(String input) {
    MemoryMap blocks = parse(input);
    blocks.compactSmart();
    return "%s".formatted(blocks.checksum());
  }
}
