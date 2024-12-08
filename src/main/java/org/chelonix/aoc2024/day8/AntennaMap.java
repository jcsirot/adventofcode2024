package org.chelonix.aoc2024.day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntennaMap {

  private final Set<Antenna> antennas;

  private final int width, height;

  private AntennaMap(int width, int height, Set<Antenna> antennas) {
    this.antennas = antennas;
    this.width = width;
    this.height = height;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Set<Coordinate> computeAntinodes() {
    Set<Coordinate> antinodes = new HashSet<>();
    for (Antenna a: antennas) {
        for (Antenna b: antennas) {
            if (a != b && a.frequency() == b.frequency()) {
              int dx = a.x() - b.x();
              int dy = a.y() - b.y();
              Coordinate c = new Coordinate(a.x()+dx, a.y()+dy);
              if (isInside(c)) {
                antinodes.add(c);
              }
            }
        }
    }
    return antinodes;
  }

  public Set<Coordinate> computeAntinodes2() {
    Set<Coordinate> antinodes = new HashSet<>();
    for (Antenna a: antennas) {
      for (Antenna b: antennas) {
        if (a != b && a.frequency() == b.frequency()) {
          int dx = a.x() - b.x();
          int dy = a.y() - b.y();
          Coordinate c = new Coordinate(a.x(), a.y());
          while (true) {
            c = new Coordinate(c.x()-dx, c.y()-dy);
            if (isInside(c)) {
              antinodes.add(c);
            } else {
              break;
            }
          }
        }
      }
    }
    return antinodes;
  }

  public boolean isInside(Coordinate coordinate) {
    return coordinate.x() >= 0 && coordinate.x() < width && coordinate.y() >= 0 && coordinate.y() < height;
  }

  public static class Builder {
    private Set<Antenna> antennas;
    private int width;
    private int row = 0;

    private Builder() {
      this.antennas = new HashSet<>();
    }

    public void addLine(String line) {
      width = line.length();
      for (int col = 0; col < width; col++) {
        if (line.charAt(col) != '.') {
          antennas.add(new Antenna(line.charAt(col), col, row));
        }
      }
      row++;
    }

    public AntennaMap build() {
      return new AntennaMap(width, row, antennas);
    }
  }
}
