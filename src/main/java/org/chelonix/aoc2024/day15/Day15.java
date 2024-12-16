package org.chelonix.aoc2024.day15;

import java.util.List;

public class Day15 {

  public record Puzzle(Warehouse warehouse, List<Move> moves) { }

  private Puzzle parse(String input) {
    String[] parts = input.split("\n\n");
    Warehouse.Builder builder = Warehouse.newBuilder();
    parts[0].lines().forEach(builder::parseLine);
    Warehouse warehouse = builder.build();
    List<Move> moves = parts[1].chars().filter(c -> c != '\n').mapToObj(c -> switch (c) {
      case '^' -> Move.UP;
      case 'v' -> Move.DOWN;
      case '<' -> Move.LEFT;
      case '>' -> Move.RIGHT;
      default -> throw new IllegalArgumentException("Invalid move: %c".formatted(c));
    }).toList();
    return new Puzzle(warehouse, moves);
  }

  public record LargePuzzle(LargeWarehouse warehouse, List<Move> moves) {
  }

  private LargePuzzle parse_part2(String input) {
    String[] parts = input.split("\n\n");
    LargeWarehouse.Builder builder = LargeWarehouse.newBuilder();
    parts[0].lines().forEach(builder::parseLine);
    LargeWarehouse warehouse = builder.build();
    List<Move> moves = parts[1].chars().filter(c -> c != '\n').mapToObj(c -> switch (c) {
      case '^' -> Move.UP;
      case 'v' -> Move.DOWN;
      case '<' -> Move.LEFT;
      case '>' -> Move.RIGHT;
      default -> throw new IllegalArgumentException("Invalid move: %c".formatted(c));
    }).toList();
    return new LargePuzzle(warehouse, moves);
  }

  public String part1(String input) {
    Puzzle puzzle = parse(input);
    System.out.println(puzzle.warehouse());
    puzzle.moves().forEach(m -> {
      puzzle.warehouse().tick(m);
    });
    long coordinates = puzzle.warehouse().allBoxCoordinates();
    return "%s".formatted(coordinates);
  }

  public String part2(String input) {
    LargePuzzle puzzle = parse_part2(input);
    System.out.println(puzzle.warehouse());
    return "%s".formatted("");
  }
}
