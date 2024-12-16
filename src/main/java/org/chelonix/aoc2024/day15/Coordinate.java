package org.chelonix.aoc2024.day15;

record Coordinate(int x, int y) {

  public Coordinate move(Direction direction) {
    return switch (direction) {
      case UP -> new Coordinate(x, y - 1);
      case DOWN -> new Coordinate(x, y + 1);
      case LEFT -> new Coordinate(x - 1, y);
      case RIGHT -> new Coordinate(x + 1, y);
    };
  }
}
