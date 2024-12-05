package org.chelonix.aoc2024.day1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static String PUZZLE_DATA = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3""";

    @Test
    public void should_pass_part_1() {
        Day1 day1 = new Day1();
        String distance = day1.part1(PUZZLE_DATA);
        assertThat(distance).isEqualTo("11");
    }

    @Test
    public void should_pass_part_2() {
        Day1 day1 = new Day1();
        String distance = day1.part2(PUZZLE_DATA);
        assertThat(distance).isEqualTo("31");
    }
}