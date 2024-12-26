package org.chelonix.aoc2024.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class RaceTrack {

  private record Cell(Coordinate c, int distance) {}

  private final boolean[][] grid;
  private final int width, height;
  private final Coordinate start, end;

  public static RaceTrack parse(String input) {
    Coordinate start = null, end = null;
    String[] lines = input.trim().split("\n");
    int width = lines[0].length();
    int height = lines.length;
    boolean[][] grid = new boolean[height][width];
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < lines[j].length(); i++) {
        char c = lines[j].charAt(i);
        if (c == '#') {
          grid[j][i] = false;
        } else if (c == 'S') {
          start = new Coordinate(i, j);
          grid[j][i] = true;
        } else if (c == 'E') {
          grid[j][i] = true;
          end = new Coordinate(i, j);
        } else {
          grid[j][i] = true;
        }
      }
    }
    return new RaceTrack(grid, width, height, start, end);
  }

  public boolean isWall(Coordinate c) {
    return !grid[c.y()][c.x()];
  }

  public RaceTrack cheat(Coordinate cheat) {
    boolean[][] dup = new boolean[grid.length][];
    for (int i = 0; i < height; i++) {
      dup[i] = Arrays.copyOf(grid[i], grid[i].length);
    }
    dup[cheat.y()][cheat.x()] = true;
    return new RaceTrack(dup, width, height, start, end);
  }

  public RaceTrack(boolean[][] grid, int width, int height, Coordinate start, Coordinate end) {
    this.grid = grid;
    this.width = width;
    this.height = height;
    this.start = start;
    this.end = end;
  }

  public List<Coordinate> findShortestPath() {
    PriorityQueue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(c -> c.distance));
    Map<Coordinate, Integer> distances = new HashMap<>();
    Map<Coordinate, Coordinate> parent = new HashMap<>();

    queue.offer(new Cell(start, 0));
    distances.put(start, 0);

    while (!queue.isEmpty()) {
      Cell current = queue.poll();
      Coordinate coords = current.c();

      if (coords.equals(end)) {
        return reconstructPath(parent, start, end);
      }

      if (current.distance > distances.getOrDefault(coords, Integer.MAX_VALUE)) {
        continue; // Skip if we've already found a shorter path to this point
      }

      for (Coordinate next : coords.adjacents()) {
        if (isValidMove(next)) {
          int newDistance = current.distance + 1;
          if (newDistance < distances.getOrDefault(next, Integer.MAX_VALUE)) {
            distances.put(next, newDistance);
            parent.put(next, coords);
            queue.offer(new Cell(next, newDistance));
          }
        }
      }
    }

    return new ArrayList<>();
  }

  private boolean isValidMove(Coordinate c) {
    return c.x() >= 0 && c.x() < width && c.y() >= 0 && c.y() < height && grid[c.y()][c.x()];
  }

  private static List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> parent, Coordinate start, Coordinate end) {
    List<Coordinate> path = new ArrayList<>();
    for (Coordinate at = end; at != null; at = parent.get(at)) {
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (start.x() == x && start.y() == y) {
          sb.append("S");
        } else if (end.x() == x && end.y() == y) {
          sb.append("E");
        } else if (grid[y][x]) {
          sb.append(".");
        } else {
          sb.append("#");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
