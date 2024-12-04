package org.chelonix.aoc2024.day4;

import org.chelonix.aoc2024.day4.Grid.Builder;

public class Day4 {

    Grid parseInput(String input) {
        Builder builder = Grid.newBuilder();
        input.lines().forEach(builder::addLine);
        return builder.build();
    }

    public String part1(String input) {
        Grid grid = parseInput(input);
        int count = 0;
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                char c = grid.getChatAt(i, j);
                if (c == 'X') {
                    for (Direction direction : Direction.values()) {
                        if ("XMAS".equals(grid.getWord(i, j, direction))) {
                            count++;
                        }
                    }
                }
            }
        }
        return "%s".formatted(count);
    }

    public String part2(String input) {
        Grid grid = parseInput(input);
        int count = 0;
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                char c = grid.getChatAt(i, j);
                if (c == 'A') {
                    WordPair pair = grid.getXWord(i, j);
                    if (("MAS".equals(pair.word1()) || "SAM".equals(pair.word1())) &&
                        ("MAS".equals(pair.word2()) || "SAM".equals(pair.word2()))) {
                        count++;
                    }
                }
            }
        }
        return "%s".formatted(count);
    }

}