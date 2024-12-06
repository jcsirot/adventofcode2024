package org.chelonix.aoc2024.day6;

public record Position(Coordinate coordinate, Direction direction) {

  public Position step() {
    return switch (direction) {
      case UP -> new Position(new Coordinate(coordinate.y() - 1, coordinate.x()), direction);
      case DOWN -> new Position(new Coordinate(coordinate.y() + 1, coordinate.x()), direction);
      case LEFT -> new Position(new Coordinate(coordinate.y(), coordinate.x() - 1), direction);
      case RIGHT -> new Position(new Coordinate(coordinate.y(), coordinate.x() + 1), direction);
    };
  }

  public Position turn() {
    return switch (this.direction) {
      case UP -> new Position(coordinate, Direction.RIGHT);
      case DOWN -> new Position(coordinate, Direction.LEFT);
      case LEFT -> new Position(coordinate, Direction.UP);
      case RIGHT -> new Position(coordinate, Direction.DOWN);
    };
  }
}
