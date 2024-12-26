package org.chelonix.aoc2024.day20;

import java.util.List;

record Coordinate(int x, int y) {

  public List<Coordinate> adjacents() {
    return List.of(new Coordinate(x+1, y), new Coordinate(x-1, y), new Coordinate(x, y+1), new Coordinate(x, y-1));
  }

  public Coordinate center(Coordinate other) {
    if (x == other.x && Math.abs(y - other.y) == 2) {
      return new Coordinate(x, Math.min(y, other.y) + 1);
    } else if (y == other.y && Math.abs(x - other.x) == 2) {
      return new Coordinate(Math.min(x, other.x) + 1, y);
    }
    return null;
  }

  public int manhattan(Coordinate other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }

}
