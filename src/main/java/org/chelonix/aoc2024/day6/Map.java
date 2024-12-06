package org.chelonix.aoc2024.day6;

import java.util.HashSet;
import java.util.Set;

public class Map {

  private final Set<Coordinate> obstacles;
  private final Coordinate start;
  private final int width, height;

  private Map(int width, int height, Coordinate start, Set<Coordinate> obstacles) {
    this.obstacles = obstacles;
    this.start = start;
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Map addObstacle(Coordinate coordinate) {
    Map newMap = new Map(width, height, coordinate, new HashSet<>(obstacles));
    newMap.obstacles.add(coordinate);
    return newMap;
  }

  public boolean containsObstacle(Coordinate target) {
    return obstacles.contains(target);
  }

  public boolean isOutside(Coordinate target) {
    return target.x() < 0 || target.x() >= width || target.y() < 0 || target.y() >= height;
  }

  public Coordinate getStart() {
    return start;
  }

  public static class Builder {
    private Set<Coordinate> obstacles = new HashSet<>();
    private Coordinate start;
    private int width;
    private int row = 0;

    private Builder() {
      this.obstacles = new HashSet<>();
    }

    public void addLine(String line) {
      width = line.length();
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == '#') {
          obstacles.add(new Coordinate(row, i));
        } else if (line.charAt(i) == '^') {
          start = new Coordinate(row, i);
        }
      }
      row++;
    }

    public Map build() {
      return new Map(width, row, start, obstacles);
    }
  }
}
