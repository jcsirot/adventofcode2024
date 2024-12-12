package org.chelonix.aoc2024.day12;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Garden {

  private enum Direction {
    TOP, BOTTOM, LEFT, RIGHT
  }

  private record Region(int perimeter, int sides, int area) { }
  private record Side(Coordinate c, Direction direction) {}

  private record Coordinate(int x, int y) {

    public List<Coordinate> adjacents() {
      return List.of(
          new Coordinate(x() - 1, y()),
          new Coordinate(x() + 1, y()),
          new Coordinate(x(), y() - 1),
          new Coordinate(x(), y() + 1)
      );
    }

    public static boolean isConsecutive(Coordinate c1, Coordinate c2) {
      return (c1.x() == c2.x() && Math.abs(c1.y() - c2.y()) == 1) ||
          (c1.y() == c2.y() && Math.abs(c1.x() - c2.x()) == 1);
    }

    public Direction compareWith(Coordinate other) {
      if (x() == other.x()) {
        if (y() == other.y() - 1) {
          return Direction.BOTTOM;
        } else if (y() == other.y() + 1) {
          return Direction.TOP;
        }
      } else if (y() == other.y()) {
        if (x() == other.x() - 1) {
          return Direction.RIGHT;
        } else if (x() == other.x() + 1) {
          return Direction.LEFT;
        }
      }
      throw new IllegalArgumentException("Not adjacent");
    }
  }

  private final char[][] garden;
  private final int width, height;

  public Garden(char[][] garden, int width, int height) {
    this.garden = garden;
    this.width = width;
    this.height = height;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        sb.append(garden[r][c]);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  public int computeCost() {
    boolean[][] visited = new boolean[width][height];
    int totalCost = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!visited[i][j]) {
          Region region =  walkRegion(visited, new Coordinate(j, i), garden[i][j], false);
          totalCost += region.area() * region.perimeter();
        }
      }
    }
    return totalCost;
  }

  public int computeCostWithDiscount() {
    boolean[][] visited = new boolean[width][height];
    int totalCost = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!visited[i][j]) {
          Region region =  walkRegion(visited, new Coordinate(j, i), garden[i][j], true);
          totalCost += region.area() * region.sides();
        }
      }
    }
    return totalCost;
  }

  private Region walkRegion(boolean[][] visited, Coordinate current, char plantType, boolean withSides) {
    Set<Side> sides = new HashSet<>();
    int perimeter = 0;
    int area = 0;
    Queue<Coordinate> queue = new LinkedList<>();
    queue.add(current);
    visited[current.y()][current.x()] = true;

    while (!queue.isEmpty()) {
      Coordinate next = queue.poll();
      area++;
      for (Coordinate c: next.adjacents()) {
        if (c.x() >= 0 && c.x() < width && c.y() >= 0 && c.y() < height) {
          if (garden[c.y()][c.x()] == plantType && !visited[c.y()][c.x()]) {
            visited[c.y()][c.x()] = true;
            queue.add(c);
          } else if (garden[c.y()][c.x()] != plantType) {
            perimeter++;
            sides.add(new Side(next, next.compareWith(c)));
          }
        } else {
          perimeter++;
          sides.add(new Side(next, next.compareWith(c)));
        }
      }
    }
    int sideCount = withSides ? groupSides(sides) : 0;
    return new Region(perimeter, sideCount, area);
  }

  private static int groupSides(Set<Side> sides) {
    return Stream.of(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT)
        .map(direction -> {
          List<Coordinate> coordinates = sides.stream()
              .filter(s -> s.direction == direction)
              .map(Side::c)
              .collect(Collectors.toList());
          return direction == Direction.TOP || direction == Direction.BOTTOM ?
              sortCoordinatesByY(coordinates) : sortCoordinatesByX(coordinates);
        })
        .mapToInt(Garden::groupByStraightLine)
        .sum();
  }

  private static List<Coordinate> sortCoordinatesByX(List<Coordinate> coordinates) {
    return coordinates.stream()
        .sorted(Comparator.comparingInt(Coordinate::x).thenComparingInt(Coordinate::y))
        .collect(Collectors.toList());
  }

  private static List<Coordinate> sortCoordinatesByY(List<Coordinate> coordinates) {
    return coordinates.stream()
        .sorted(Comparator.comparingInt(Coordinate::y).thenComparingInt(Coordinate::x))
        .collect(Collectors.toList());
  }

  public static int groupByStraightLine(List<Coordinate> sortedCoordinates) {
    int groupCount = 0;
    Coordinate previous = null;

    for (Coordinate coordinate : sortedCoordinates) {
      if (previous == null || Coordinate.isConsecutive(previous, coordinate)) {
        previous = coordinate;
      } else {
        groupCount++;
        previous = coordinate;
      }
    }
    return groupCount + 1;
  }

  public static class Builder {

    private Map<Coordinate, Character> map;
    private int width;
    private int height;

    private Builder() {
      map = new HashMap<>();
      width = 0;
      height = 0;
    }

    public void addLine(String line) {
      if (width == 0) {
        width = line.length();
      } else if (line.length() != width) {
        throw new IllegalArgumentException("Inconsistent line length");
      }
      for (int i = 0; i < width; i++) {
        map.put(new Coordinate(height, i), line.charAt(i));
      }
      height++;
    }

    public Garden build() {
      char[][] garden = new char[width][height];
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          garden[r][c] = map.get(new Coordinate(r, c));
        }
      }
      return new Garden(garden, width, height);
    }
  }

}
