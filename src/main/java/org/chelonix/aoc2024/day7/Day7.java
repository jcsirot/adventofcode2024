package org.chelonix.aoc2024.day7;

import java.util.Arrays;
import java.util.List;

public class Day7 {

  private List<Calibration> parse(String input) {
    return input.lines().map(line -> {
      String[] parts = line.split(": ");
      String[] values = parts[1].split("\\s+");
      return new Calibration(Long.parseLong(parts[0]), Arrays.stream(values).map(Long::parseLong).toList());
    }).toList();
  }

  public String part1(String input) {
    List<Calibration> calibrations = parse(input);
    long count = 0;
    for (Calibration calibration : calibrations) {
      long result = calibration.result();
      List<Long> values = calibration.values();
      count += solve(result, values) ? result : 0;
    }
    return "%s".formatted(count);
  }

  private boolean solve(long result, List<Long> values) {
    int cases = 1 << values.size() - 1;
    for (int i = 0; i < cases; i++) {
      long sum = values.get(0);
      for (int j = 1; j < values.size(); j++) {
        long v = values.get(j);
        if ((i & (1 << j - 1)) != 0) {
          sum += v;
        } else {
          sum *= v;
        }
        if (sum > result) {
          break;
        }
      }
      if (sum == result) {
        return true;
      }
    }
    return false;
  }

  public String part2(String input) {
    List<Calibration> calibrations = parse(input);
    long count = 0;
    for (Calibration calibration : calibrations) {
      long result = calibration.result();
      List<Long> values = calibration.values();
      count += solve2(result, values.subList(1, values.size()), values.get(0)) ? result : 0;
      //count += solve2(result, values) ? result : 0;
    }
    return "%s".formatted(count);
  }

/*
  private boolean solve2(long result, List<Long> values) {
    int cases = (int) Math.pow(3, values.size()-1);
    for (int i = 0; i < cases; i++) {
      long sum = values.get(0);
      String s = String.format("%0" + (values.size() - 1) + "d", Long.parseLong(Long.toString(i, 3)));
      for (int j = 1; j <= s.length(); j++) {
        long v = values.get(j);
        switch (s.codePointAt(j-1)) {
          case '0':
            sum += v;
            break;
          case '1':
            sum *= v;
            break;
          case '2':
            sum = Long.parseLong(sum + "" + v);
            break;
        }
        if (sum > result) {
          break;
        }
      }
      if (sum == result) {
        return true;
      }
    }
    return false;
  }
*/

  private static final Operation[] OPS = { Operation.ADD, Operation.MUL, Operation.CONCAT };

  private boolean solve2(long result, List<Long> values, long acc) {
    long v = values.get(0);
    for (Operation op : OPS) {
      long r = op.apply(acc, v);
      if (r > result) {
        continue;
      }
      if (values.size() == 1) {
        if (r == result) {
          return true;
        }
      } else {
        if (solve2(result, values.subList(1, values.size()), r)) {
          return true;
        }
      }
    }
    return false;
  }
}
