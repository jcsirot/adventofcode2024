package org.chelonix.aoc2024.day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    private static final String PUZZLE_INPUT = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20""";

    @Test
    void should_pass_part_1() {
        Day7 day7 = new Day7();
        String count = day7.part1(PUZZLE_INPUT);
        assertEquals("3749", count);
    }

    @Test
    void should_pass_part_2() {
        Day7 day7 = new Day7();
        String count = day7.part2(PUZZLE_INPUT);
        assertEquals("11387", count);
    }

}