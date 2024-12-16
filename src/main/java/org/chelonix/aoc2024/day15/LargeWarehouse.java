package org.chelonix.aoc2024.day15;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class LargeWarehouse {

    private record Coordinate(int x, int y) {
        public Coordinate adjacent(Move move) {
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
        WALL, BOX_LEFT, BOX_RIGHT
    }

    private Coordinate robotCoordinate;

    private Set<Tile> tiles;

    private LargeWarehouse(Set<Tile> tiles, int rx, int ry) {
        this.tiles = tiles;
        this.robotCoordinate = new Coordinate(rx, ry);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private boolean isEmpty(Coordinate c) {
        return !tiles.stream().anyMatch(containsBoxPredicate(c).or(containsWallPredicate(c)));
    }

    private boolean isWall(Coordinate c) {
        return tiles.stream().anyMatch(containsWallPredicate(c));
    }

    private static Predicate<Tile> containsWallPredicate(Coordinate c) {
        return t -> t.c().equals(c) && t.element() == Element.WALL;
    }

    private boolean containsBox(Coordinate c) {
        return tiles.stream().anyMatch(containsBoxPredicate(c));
    }

    private boolean containsBoxLeft(Coordinate c) {
        return tiles.stream().anyMatch(containsBoxLeftPredicate(c));
    }

    private boolean containsBoxRight(Coordinate c) {
        return tiles.stream().anyMatch(containsBoxRightPredicate(c));
    }

    private static Predicate<Tile> containsBoxPredicate(Coordinate c) {
        return containsBoxLeftPredicate(c).or(containsBoxRightPredicate(c));
    }

    private static Predicate<Tile> containsBoxLeftPredicate(Coordinate c) {
        return t -> t.c().equals(c) && t.element() == Element.BOX_LEFT;
    }

    private static Predicate<Tile> containsBoxRightPredicate(Coordinate c) {
        return t -> t.c().equals(c) && t.element() == Element.BOX_RIGHT;
    }

    private void moveBox(Coordinate from, Coordinate to) {
        tiles.stream().filter(t -> t.c().equals(from)).findFirst().ifPresent(t -> {
            tiles.remove(t);
            tiles.add(new Tile(to, t.element()));
        });
    }

    public void tick(Move move) {
        Coordinate c = robotCoordinate.adjacent(move);
        if (isEmpty(c)) {
            robotCoordinate = c;
        } else if (containsBox(c)) {
            if (tryMoveBox(c, move)) {
                robotCoordinate = c;
            }
        }
    }

    /**
     * Try to move the box at coordinate c in the direction of move.
     * @param c coordinate of the box
     * @param move direction to move the box
     * @return true if the box can be moved, false otherwise
     */
    private boolean tryMoveBox(Coordinate c, Move move) {
        if (move == Move.RIGHT) {
            Coordinate next = c.adjacent(move);
            Coordinate nextNext = next.adjacent(move);
            if (isEmpty(nextNext)) {  // ->[]
                return true;
            } else if (containsBox(nextNext)) {  // ->[][]
                return tryMoveBox(nextNext, move);
            }
        } else if (move == Move.LEFT) {
            Coordinate next = c.adjacent(move);
            Coordinate nextNext = next.adjacent(move);
            if (isEmpty(next)) { // .[]<-
                return true;
            } else if (containsBox(nextNext)) { // [][]<-
                return tryMoveBox(nextNext, move);
            }
        } else {  // Up or Down
            Coordinate next = c.adjacent(move);
            // []
            // ^
            if (containsBoxLeft(c)) {
                Coordinate nextNext = next.adjacent(Move.RIGHT);
                // ..
                // []
                // ^
                if (isEmpty(next) && isEmpty(nextNext)) {
                    return true;
                // .[]
                // [].
                // ^
                } else if (isEmpty(next) && containsBox(nextNext)) {
                    return tryMoveBox(nextNext, move);
                // [].
                // .[]
                //  ^
                } else if (containsBox(next) && isEmpty(nextNext)) {
                    return tryMoveBox(next.adjacent(Move.LEFT), move);
                // []
                // []
                // ^
                } else if (containsBoxLeft(next) && containsBoxRight(nextNext)) {
                    return tryMoveBox(next, move);
                // [][]
                // .[].
                //  ^
                }  else if (containsBoxRight(next) && containsBoxLeft(nextNext)) {
                    return tryMoveBox(next.adjacent(Move.LEFT), move) && tryMoveBox(nextNext, move);
                }
            // []
            //  ^
            } else {
                Coordinate nextNext = next.adjacent(Move.LEFT);
                // ..
                // []
                //  ^
                if (isEmpty(next) && isEmpty(nextNext)) {
                    return true;
                // [].
                // .[]
                //   ^
                } else if (isEmpty(next) && containsBox(nextNext)) {
                    return tryMoveBox(nextNext, move);
                // .[]
                // [].
                //  ^
                } else if (containsBox(next) && isEmpty(nextNext)) {
                    return tryMoveBox(next.adjacent(Move.RIGHT), move);
                // []
                // []
                //  ^
                } else if (containsBoxLeft(next) && containsBoxRight(nextNext)) {
                    return tryMoveBox(next, move);
                // [][]
                // .[].
                //  ^
                }  else if (containsBoxRight(next) && containsBoxLeft(nextNext)) {
                    return tryMoveBox(next.adjacent(Move.LEFT), move) && tryMoveBox(nextNext, move);
                }
            }
        }
        return false;
    }

    public long allBoxCoordinates() {
        return tiles.stream().filter(t -> t.element() == Element.BOX_LEFT).mapToLong(t -> t.c().x() + t.c().y() * 100).sum();
    }

    @Override
    public String toString() {
        int width = tiles.stream().mapToInt(t -> t.c().x()).max().orElse(0) + 1;
        int height = tiles.stream().mapToInt(t -> t.c().y()).max().orElse(0) + 1;
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Coordinate c = new Coordinate(x, y);
                if (robotCoordinate.equals(c)) {
                    sb.append('@');
                } else if (isWall(c)) {
                    sb.append('#');
                } else if (containsBoxLeft(c)) {
                    sb.append('[');
                } else if (containsBoxRight(c)) {
                    sb.append(']');
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
                    tiles.add(new Tile(new Coordinate(2*i, height), Element.WALL));
                    tiles.add(new Tile(new Coordinate(2*i+1, height), Element.WALL));
                } else if (c == 'O') {
                    tiles.add(new Tile(new Coordinate(2*i, height), Element.BOX_LEFT));
                    tiles.add(new Tile(new Coordinate(2*i+1, height), Element.BOX_RIGHT));
                } else if (c == '@') {
                    rx = 2*i;
                    ry = height;
                }
            }
            height++;
        }

        public LargeWarehouse build() {
            return new LargeWarehouse(tiles, rx, ry);
        }
    }
}
