package org.chelonix.aoc2024.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    private static final String PUZZLE_INPUT = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3""";

    @Test
    public void should_pass_part_1() {
        Day14 day14 = new Day14();
        String score = day14.part1(PUZZLE_INPUT);
        assertThat(score).isEqualTo("12");
    }
}