package org.chelonix.aoc2024.day16;

public class Day16 {

  private Maze parse(String input) {
    return new Maze(input);
  }

  public String part1(String input) {
    Maze maze = parse(input);
    // System.out.println(maze);
    int score = maze.findLowestScore();
    return "%s".formatted(score);
  }

  public String part2(String input) {
    Maze maze = parse(input);
    int score = maze.countSeatsOnPathsWithLowestScore();
    return "%s".formatted(score);
  }
}
