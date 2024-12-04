package org.chelonix.aoc2024.day4;

import java.util.ArrayList;
import java.util.List;

public class Grid {
  private final List<Character> grid;
  private final int rows;
  private final int cols;

  private Grid(int rows, int cols, List<Character> grid) {
    this.rows = rows;
    this.cols = cols;
    this.grid = grid;
  }

  public char getChatAt(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      return ' ';
    }
    return grid.get(row * cols + col);
  }

  public int getRowCount() {
    return rows;
  }

  public int getColumnCount() {
    return cols;
  }

  public String getWord(int row, int col, Direction direction) {
    StringBuilder sb = new StringBuilder();
    switch (direction) {
      case E -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row, col + 1))
            .append(getChatAt(row, col + 2))
            .append(getChatAt(row, col + 3));
      }
      case SE -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row + 1, col + 1))
            .append(getChatAt(row + 2, col + 2))
            .append(getChatAt(row + 3, col + 3));
      }
      case S -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row + 1, col))
            .append(getChatAt(row + 2, col))
            .append(getChatAt(row + 3, col));
      }
      case SW -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row + 1, col - 1))
            .append(getChatAt(row + 2, col - 2))
            .append(getChatAt(row + 3, col - 3));
      }
      case W -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row, col - 1))
            .append(getChatAt(row, col - 2))
            .append(getChatAt(row, col - 3));
      }
      case NW -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row - 1, col - 1))
            .append(getChatAt(row - 2, col - 2))
            .append(getChatAt(row - 3, col - 3));
      }
      case N -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row - 1, col))
            .append(getChatAt(row - 2, col))
            .append(getChatAt(row - 3, col));
      }
      case NE -> {
        sb.append(getChatAt(row, col))
            .append(getChatAt(row - 1, col + 1))
            .append(getChatAt(row - 2, col + 2))
            .append(getChatAt(row - 3, col + 3));
      }
    }
    return sb.toString();
  }

  public WordPair getXWord(int row, int col) {
    StringBuilder sb = new StringBuilder();
    sb.append(getChatAt(row - 1, col - 1))
        .append(getChatAt(row, col))
        .append(getChatAt(row + 1, col + 1));
    String word1 = sb.toString();
    sb = new StringBuilder();
    sb.append(getChatAt(row - 1, col + 1))
        .append(getChatAt(row, col))
        .append(getChatAt(row + 1, col - 1));
    String word2 = sb.toString();
    return new WordPair(word1, word2);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private final List<Character> grid;
    private int rows, cols;
    private Builder() {
      this.grid = new ArrayList<>();
    }

    public void addLine(String line) {
      cols = line.length();
      rows++;
      line.codePoints().forEach(c -> grid.add((char) c));
    }

    public Grid build() {
      return new Grid(rows, cols, grid);
    }
  }
}
