package org.chelonix.aoc2024.day2;

import org.chelonix.aoc2024.day1.Day1;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    private static String PART_1_DATA = """
         7 6 4 2 1
         1 2 7 8 9
         9 7 6 2 1
         1 3 2 4 5
         8 6 4 4 1
         1 3 6 7 9""";

    @Test
    public void should_pass_part_1() {
        Day2 day2 = new Day2();
        String count = day2.part1(PART_1_DATA);
        assertThat(count).isEqualTo("2");
    }

    @Test
    public void should_pass_part_2() {
        Day2 day2 = new Day2();
        String count = day2.part2(PART_1_DATA);
        assertThat(count).isEqualTo("4");
    }
}