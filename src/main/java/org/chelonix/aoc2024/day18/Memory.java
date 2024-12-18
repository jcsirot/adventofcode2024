package org.chelonix.aoc2024.day18;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Memory {

  private record Cell(int x, int y, int distance) {}
  private record Direction(int dx, int dy) {
    static final List<Direction> DIRECTIONS = List.of(new Direction(0, 1), new Direction(1, 0), new Direction(0, -1), new Direction(-1, 0));
  }

  private int[][] memory;
  private int width = 0, height = 0;
  private int byteCount;

  public Memory(String input) {
    parse(input);
  }

  public String findFirstBlocker() {
    for (int i = 1; i < byteCount; i++) {
      if (shortestPath(i) == -1) {
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            if (memory[y][x] == i) {
              return "%s,%s".formatted(x, y);
            }
          }
        }
      }
    }
    return null;
  }

  public int shortestPath(int t) {
    int[][] dist = new int[height][width];
    for (int[] row : dist) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }
    dist[0][0] = 0;

    PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(c -> c.distance()));
    pq.offer(new Cell(0, 0, 0));

    while (!pq.isEmpty()) {
      Cell current = pq.poll();

      if (current.x() == width - 1 && current.y() == height - 1) {
        return current.distance();
      }

      for (Direction direction: Direction.DIRECTIONS) {
        int nx = current.x() + direction.dx();
        int ny = current.y() + direction.dy();

        if (nx >= 0 && nx < width && ny >= 0 && ny < height && (memory[ny][nx] > t || memory[ny][nx] == 0)) {
          int newDist = current.distance() + 1;
          if (newDist < dist[ny][nx]) {
            dist[ny][nx] = newDist;
            pq.offer(new Cell(nx, ny, newDist));
          }
        }
      }
    }
    return -1;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < memory.length; y++) {
      for (int x = 0; x < memory[y].length; x++) {
        sb.append(memory[y][x] > 0 ? "#" : ".");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public String toString(int tick) {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < memory.length; y++) {
      for (int x = 0; x < memory[y].length; x++) {
        sb.append(memory[y][x] <= tick && memory[y][x] > 0 ? "#" : ".");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private void parse(String input) {
    String[] lines = input.split("\n");

    for (String line : lines) {
      String[] parts = line.split(",");
      int x = Integer.parseInt(parts[0].trim());
      int y = Integer.parseInt(parts[1].trim());
      if (x >= width) {
        width = x+1;
      }
      if (y >= height) {
        height = y+1;
      }
    }

    memory = new int[height + 1][width + 1];

    int t = 0;
    for (String line : lines) {
      String[] parts = line.split(",");
      int x = Integer.parseInt(parts[0].trim());
      int y = Integer.parseInt(parts[1].trim());
      memory[y][x] = ++t;
    }
    byteCount = t;
  }
}
