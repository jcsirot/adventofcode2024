package org.chelonix.aoc2024.day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopographicMap {

  private final Map<Coordinate, Integer> heights;
  private final int width, height;

  private List<Coordinate> adjacents(Coordinate point) {
    return List.of(
        new Coordinate(point.x() - 1, point.y()),
        new Coordinate(point.x() + 1, point.y()),
        new Coordinate(point.x(), point.y() - 1),
        new Coordinate(point.x(), point.y() + 1)).stream()
        .filter(c -> c.x() >= 0 && c.x() < width && c.y() >= 0 && c.y() < height)
        .toList();
  }

  public TopographicMap(Map<Coordinate, Integer> heights, int width, int height) {
    this.heights = heights;
    this.width = width;
    this.height = height;
  }

  public int findPathScores() {
    return heights.keySet().stream()
        .filter(c -> heights.get(c) == 0)
        .mapToInt(c -> {
          List<List<Coordinate>> paths = new ArrayList<>();
          findPathLengths(c, 0, new ArrayList<>(), paths);
          return paths.stream().map(List::getLast).collect(Collectors.toUnmodifiableSet()).size();
        })
        .sum();
  }

  public int findPathRatings() {
    return heights.keySet().stream()
        .filter(c -> heights.get(c) == 0)
        .mapToInt(c -> {
          List<List<Coordinate>> paths = new ArrayList<>();
          findPathLengths(c, 0, new ArrayList<>(), paths);
          return paths.size();
        })
        .sum();
  }

  private void findPathLengths(Coordinate c, int h, List<Coordinate> current, List<List<Coordinate>> paths) {
    current.add(c);
    if (h == 9) {
      paths.add(current);
      return;
    }
    adjacents(c).stream()
        .filter(a -> heights.get(a) == h + 1)
        .forEach(a -> findPathLengths(a, h+1, new ArrayList<>(current), paths));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        sb.append(heights.get(new Coordinate(x, y)));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public static class Builder {

    private Map<Coordinate, Integer> heights;
    private int width;
    private int height;

    public Builder() {
      heights = new HashMap<>();
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
        heights.put(new Coordinate(i, height), Integer.parseInt("" + line.charAt(i)));
      }
      height++;
    }

    public TopographicMap build() {
      return new TopographicMap(heights, width, height);
    }
  }
}
