package org.chelonix.aoc2024.day3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {
    private static String PUZZLE_DATA_1 = """
         xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""";

    private static String PUZZLE_DATA_2 = """
         xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""";

    @Test
    public void should_pass_part_1() {
        Day3 day3 = new Day3();
        String count = day3.part1(PUZZLE_DATA_1);
        assertThat(count).isEqualTo("161");
    }

    @Test
    public void should_pass_part_2() {
        Day3 day3 = new Day3();
        String count = day3.part2(PUZZLE_DATA_2);
        assertThat(count).isEqualTo("48");
    }

}