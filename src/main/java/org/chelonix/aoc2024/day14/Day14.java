package org.chelonix.aoc2024.day14;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 {

  private RobotGrid parse(String input) {
    RobotGrid.Builder builder = RobotGrid.newBuilder();
    input.lines().forEach(builder::parseLine);
    return builder.build();
  }

  public String part1(String input) {
    RobotGrid robotGrid = parse(input);
    //System.out.println(robotGrid.tpString());
    for (int i = 0; i < 100; i++) {
      robotGrid.tick();
    }
    //System.out.println(robotGrid.tpString());
    long safetyFactor = robotGrid.safetyFactor();
    return "%s".formatted(safetyFactor);
  }

  public String part2(String input) {
    RobotGrid robotGrid = parse(input);
    long loopSize = robotGrid.loopSize();
    List<Integer> candidates = new ArrayList<>();
    try {
      Files.createDirectories(Path.of("target/day14"));
      for (int i = 0; i < loopSize; i++) {
        robotGrid.tick();
        if (robotGrid.canBeEasterEggCandidate()) {
          candidates.add(i+1);
          Files.writeString(Path.of("target/day14/%s.txt".formatted(i+1)), robotGrid.tpString());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "%s".formatted("candidates = %s".formatted(candidates.stream().map(i -> i.toString()).collect(Collectors.joining(", "))));
  }
}
