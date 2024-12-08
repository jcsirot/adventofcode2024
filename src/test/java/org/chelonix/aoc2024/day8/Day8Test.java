package org.chelonix.aoc2024.day8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {
    private static String PUZZLE_INPUT = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............""";

    @Test
    public void should_pass_part_1() {
        Day8 day8 = new Day8();
        String count = day8.part1(PUZZLE_INPUT);
        assertThat(count).isEqualTo("14");
    }

    @Test
    public void should_pass_part_2() {
        Day8 day8 = new Day8();
        String count = day8.part2(PUZZLE_INPUT);
        assertThat(count).isEqualTo("34");
    }
}