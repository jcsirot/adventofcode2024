package org.chelonix.aoc2024.day15;

import java.util.HashSet;
import java.util.Set;

public class Warehouse {

    private record Coordinate(int x, int y) {
        public Coordinate move(Move move) {
            return switch (move) {
                case UP -> new Coordinate(x, y-1);
                case DOWN -> new Coordinate(x, y+1);
                case LEFT -> new Coordinate(x-1, y);
                case RIGHT -> new Coordinate(x+1, y);
            };
        }
    }

    private record Tile(Coordinate c, Element element) {}
    private enum Element {
        WALL, BOX
    }

    private Coordinate robot;

    private Set<Tile> tiles;

    private Warehouse(Set<Tile> tiles, int rx, int ry) {
        this.tiles = tiles;
        this.robot = new Coordinate(rx, ry);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private boolean isEmpty(Coordinate c) {
        return !tiles.stream().anyMatch(t -> t.c().equals(c));
    }

    private boolean isWall(Coordinate c) {
        return tiles.stream().anyMatch(t -> t.c().equals(c) && t.element() == Element.WALL);
    }

    private boolean containsbox(Coordinate c) {
        return tiles.stream().anyMatch(t -> t.c().equals(c) && t.element() == Element.BOX);
    }

    private void move(Coordinate from, Coordinate to) {
        tiles.stream().filter(t -> t.c().equals(from)).findFirst().ifPresent(t -> {
            tiles.remove(t);
            tiles.add(new Tile(to, t.element()));
        });
    }

    public void tick(Move move) {
        Coordinate c = robot.move(move);
        if (isEmpty(c)) {
            robot = c;
        } else if (containsbox(c)) {
            if (moveBox(c, move)) {
                robot = c;
            }
        }
    }

    private boolean moveBox(Coordinate c, Move move) {
        Coordinate next = c.move(move);
        if (isEmpty(next)) {
            move(c, next);
            return true;
        } else if (containsbox(next)) {
            if (moveBox(next, move)) {
                move(c, next);
                return true;
            }
        }
        return false;
    }

    public long allBoxCoordinates() {
        return tiles.stream().filter(t -> t.element() == Element.BOX).mapToLong(t -> t.c().x() + t.c().y() * 100).sum();
    }

    public String toString() {
        int width = tiles.stream().mapToInt(t -> t.c().x()).max().orElse(0) + 1;
        int height = tiles.stream().mapToInt(t -> t.c().y()).max().orElse(0) + 1;
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Coordinate c = new Coordinate(x, y);
                if (robot.equals(c)) {
                    sb.append('@');
                } else if (isWall(c)) {
                    sb.append('#');
                } else if (containsbox(c)) {
                    sb.append('O');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static class Builder {
        private int height = 0;
        private int rx, ry;
        private Set<Tile> tiles = new HashSet<>();

        public void parseLine(String line) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '#') {
                    tiles.add(new Tile(new Coordinate(i, height), Element.WALL));
                } else if (c == 'O') {
                    tiles.add(new Tile(new Coordinate(i, height), Element.BOX));
                } else if (c == '@') {
                    rx = i;
                    ry = height;
                }
            }
            height++;
        }

        public Warehouse build() {
            return new Warehouse(tiles, rx, ry);
        }
    }

}
