package org.chelonix.aoc2024.day16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Maze {

  private static final int TURN_COST = 1000;
  private static final int MOVE_COST = 1;

  private record Coordinate(int x, int y) { }

  private record Move(int x, int y, Direction direction) { }

  private record Tile(int x, int y, int score, Direction direction, int turns, List<Coordinate> path) {}

  private record Scores(int x, int y, int score) {}

  private enum Direction {
    NORTH(new Coordinate(0,-1)), EAST(new Coordinate(1, 0)), SOUTH(new Coordinate(0, 1)), WEST(new Coordinate(-1,0));

    private Coordinate coordinate;

    int x() {
      return coordinate.x();
    }

    int y() {
      return coordinate.y();
    }

    boolean opposite(Direction other) {
      return switch (this) {
        case NORTH -> other == SOUTH;
        case SOUTH -> other == NORTH;
        case EAST -> other == WEST;
        case WEST -> other == EAST;
      };
    }

    Direction(Coordinate coordinate) {
      this.coordinate = coordinate;
    }
  }

  private final char[][] maze;
  private final Coordinate start, end;

  public Maze(String input) {
    String[] rows = input.split("\n");

    int height = rows.length;
    int width = rows[0].length();

    int sx = 0, sy = 0, ex = 0, ey = 0;

    maze = new char[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        maze[i][j] = rows[i].charAt(j);
        if (maze[i][j] == 'S') {
          sx = j;
          sy = i;
        } else if (maze[i][j] == 'E') {
          ex = j;
          ey = i;
        }
      }
    }
    start = new Coordinate(sx, sy);
    end = new Coordinate(ex, ey);
  }

  public int findLowestScore() {
    PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingInt(Tile::score));
    queue.add(new Tile(start.x(), start.y(), 0, Direction.EAST, 0, null));

    Set<Move> visited = new HashSet<>();

    while (!queue.isEmpty()) {
      Tile current = queue.poll();

      if (end.equals(new Coordinate(current.x, current.y))) {
        return current.score();
      }

      if (visited.contains(new Move(current.x, current.y, current.direction))) {
        continue;
      }

      visited.add(new Move(current.x, current.y, current.direction));

      for (Direction direction : Direction.values()) {
        if (current.direction.opposite(direction)) {
          continue;
        }
        int newX = current.x + direction.x();
        int newY = current.y + direction.y();
        int newScore = current.score + (direction == current.direction ? MOVE_COST : TURN_COST + MOVE_COST);

        if (isValidMove(newX, newY)) {
          queue.add(new Tile(newX, newY, newScore, direction, current.turns + (direction != current.direction ? 1 : 0), null));
        }
      }
    }
    return -1; // No path found
  }

  public int countSeatsOnPathsWithLowestScore() {
    int lowestScore = findLowestScore();
    PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingInt(Tile::score));
    queue.add(new Tile(start.x(), start.y(), 0, Direction.EAST, 0, List.of(start)));

    Set<Coordinate> seats = new HashSet();
    Map<Move, Integer> visited = new HashMap<>();

    while (!queue.isEmpty()) {
      Tile current = queue.poll();

      if (end.equals(new Coordinate(current.x, current.y)) && lowestScore == current.score) {
        seats.addAll(current.path);
        continue;
        // return current.score();
      }

      Move key = new Move(current.x, current.y, current.direction);
      if (visited.containsKey(key) && visited.get(key) < current.score) {
        continue;
      }

      visited.put(key, current.score);

      for (Direction direction : Direction.values()) {
        if (current.direction.opposite(direction)) {
          continue;
        }
        int newX = current.x + direction.x();
        int newY = current.y + direction.y();
        int newScore = current.score + (direction == current.direction ? MOVE_COST : TURN_COST + MOVE_COST);

        if (current.score > lowestScore) {
          continue;
        }

        if (isValidMove(newX, newY)) {
          List<Coordinate> path = new ArrayList<>(current.path);
          path.add(new Coordinate(newX, newY));
          queue.add(new Tile(newX, newY, newScore, direction, current.turns + (direction != current.direction ? 1 : 0), path));
        }
      }
    }
    return seats.size();
  }

  private boolean isValidMove(int x, int y) {
    return x >= 0 && x < maze[0].length && y >= 0 && y < maze.length && maze[y][x] != '#';
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (char[] row : maze) {
      sb.append(new String(row)).append("\n");
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }

  public String toString(Tile current) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[i].length; j++) {
        if (current.x == j && current.y == i) {
          sb.append('X');
        } else if (maze[i][j] == '.') {
          sb.append(' ');
        } else {
          sb.append(maze[i][j]);
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
