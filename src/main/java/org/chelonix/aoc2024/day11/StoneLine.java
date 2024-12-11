package org.chelonix.aoc2024.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoneLine {

  private record LeftRight(long left, long right) { }

  private record Entry(long value, int count) { }

  private static boolean hasEvenDigitNumbers(long i)
  {
    int digitCount = String.valueOf(i).length();
    return digitCount % 2 == 0;
  }

  private static LeftRight split(long i) {
    String s = String.valueOf(i);
    int mid = s.length() / 2;
    return new LeftRight(Long.parseLong(s.substring(0, mid)), Long.parseLong(s.substring(mid)));
  }

  private final List<Long> stones;

  public StoneLine(long... stones) {
    this.stones = new LinkedList<>();
    for (long stone : stones) {
      this.stones.add(stone);
    }
  }

  public StoneLine(List<Long> stones) {
    this.stones = new ArrayList<>(stones);
  }

  public int length() {
    return stones.size();
  }

  private Map<Entry, Long> cache = new HashMap<>();

  public long blink(long value, int count) {
    Entry entry = new Entry(value, count);
    if (cache.containsKey(entry)) {
      return cache.get(entry);
    }
    if (count == 0) {
      return 1;
    }
    if (value == 0) {
      long v = blink(1L, count-1);
      cache.put(entry, v);
      return v;
    } else if (hasEvenDigitNumbers(value)) {
      LeftRight lr = split(value);
      long v1 = blink(lr.left, count-1);
      long v2 = blink(lr.right, count-1);
      cache.put(entry, v1+v2);
      return v1+v2;
    } else {
      long v = blink(value*2024, count-1);
      cache.put(entry, v);
      return v;
    }
  }

  public long blink(int count) {
    return stones.stream().mapToLong(v -> blink(v, count)).sum();
  }

  @Override
  public String toString() {
    return "StoneLine{" +
        "stones=" + stones.stream().map(l -> l.toString()).collect(Collectors.joining( " ")) +
        '}';
  }
}
