package org.chelonix.aoc2024.day22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {

    private static final String PUZZLE_INPUT = """
            1
            10
            100
            2024""";

    private static final String PUZZLE_INPUT_2 = """
            1
            2
            3
            2024""";
    @Test
    public void should_pass_part_1() {
        Day22 day22 = new Day22();
        String score = day22.part1(PUZZLE_INPUT);
        assertThat(score).isEqualTo("37327623");
    }

    @Test
    public void should_pass_part_2() {
        Day22 day22 = new Day22();
        String score = day22.part2(PUZZLE_INPUT_2);
        assertThat(score).isEqualTo("23");
    }
}