package org.chelonix.aoc2024.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class LargeWarehouse {

    private record Coordinate(int x, int y) {
        public Coordinate adjacent(Direction direction) {
            return switch (direction) {
                case UP -> new Coordinate(x, y-1);
                case DOWN -> new Coordinate(x, y+1);
                case LEFT -> new Coordinate(x-1, y);
                case RIGHT -> new Coordinate(x+1, y);
            };
        }
    }

    private record Tile(Coordinate c, Element element) {}

    private record Box(Coordinate c) {
        List<Coordinate> coordinatesAt(Direction m) {
            return switch (m) {
                case UP -> List.of(c.adjacent(Direction.UP), c.adjacent(Direction.UP).adjacent(
                    Direction.RIGHT));
                case DOWN -> List.of(c.adjacent(Direction.DOWN), c.adjacent(Direction.DOWN).adjacent(
                    Direction.RIGHT));
                case LEFT -> List.of(c.adjacent(Direction.LEFT));
                case RIGHT -> List.of(c.adjacent(Direction.RIGHT).adjacent(Direction.RIGHT));
            };
        }
    }

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

    private void moveBoxes(Set<Box> boxes, Direction direction) {
        List<Tile> newTiles = new ArrayList<>();
        for (Box b : boxes) {
            tiles.remove(new Tile(b.c(), Element.BOX_LEFT));
            tiles.remove(new Tile(b.c().adjacent(Direction.RIGHT), Element.BOX_RIGHT));
            newTiles.add(new Tile(b.c().adjacent(direction), Element.BOX_LEFT));
            newTiles.add(new Tile(b.c().adjacent(Direction.RIGHT).adjacent(direction), Element.BOX_RIGHT));
        }
        tiles.addAll(newTiles);
    }

    public void tick(Direction direction) {
        Coordinate c = robotCoordinate.adjacent(direction);
        if (isEmpty(c)) {
            robotCoordinate = c;
        } else if (containsBox(c)) {
            Set<Box> movedBoxes = tryMoveBox(boxAt(c), direction);
            if (! movedBoxes.isEmpty()) {
                moveBoxes(movedBoxes, direction);
                robotCoordinate = c;
            }
        }
    }

    private Box boxAt(Coordinate c) {
        return tiles.stream().filter(t -> t.c().equals(c)).map(t -> {
            if (t.element() == Element.BOX_LEFT) {
                return new Box(t.c());
            } else {
                return new Box(t.c().adjacent(Direction.LEFT));
            }
        }).findFirst().orElseGet(() -> null);
    }

    /**
     * Try to move a box in the given direction.
     * @param c the coordinate of the box
     * @param direction the direction to move the box
     * @return the list of box to move
     */
    private Set<Box> tryMoveBox(Box box, Direction direction) {
        Set<Box> boxes = new HashSet<>();
        for (Coordinate c: box.coordinatesAt(direction)) {
            if (isEmpty(c)) {
                boxes.add(box);
            } else if (containsBox(c)) {
                Set<Box> movedBox = tryMoveBox(boxAt(c), direction);
                if (movedBox.isEmpty()) {
                    return Set.of();
                } else {
                    boxes.add(box);
                    boxes.addAll(movedBox);
                }
            } else {
                return Set.of();
            }
        }
        return boxes;
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
