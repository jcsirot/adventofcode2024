package org.chelonix.aoc2024.day12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day12Test {
  private static String PUZZLE_INPUT_1 = """
      AAAA
      BBCD
      BBCC
      EEEC""";

  private static String PUZZLE_INPUT_2 = """
     OOOOO
     OXOXO
     OOOOO
     OXOXO
     OOOOO""";

  private static String PUZZLE_INPUT_3 = """
    RRRRIICCFF
    RRRRIICCCF
    VVRRRCCFFF
    VVRCCCJFFF
    VVVVCJJCFE
    VVIVCCJJEE
    VVIIICJJEE
    MIIIIIJJEE
    MIIISIJEEE
    MMMISSJEEE""";

  private static String PUZZLE_INPUT_4 = """
     EEEEE
     EXXXX
     EEEEE
     EXXXX
     EEEEE""";

  private static String PUZZLE_INPUT_5 = """
    AAAAAA
    AAABBA
    AAABBA
    ABBAAA
    ABBAAA
    AAAAAA""";


  static Stream<Arguments> providePuzzleInputsPart1() {
    return Stream.of(
        Arguments.of(PUZZLE_INPUT_1, "140"),
        Arguments.of(PUZZLE_INPUT_2, "772"),
        Arguments.of(PUZZLE_INPUT_3, "1930")
    );
  }

  static Stream<Arguments> providePuzzleInputsPart2() {
    return Stream.of(
        Arguments.of(PUZZLE_INPUT_1, "80"),
        Arguments.of(PUZZLE_INPUT_2, "436"),
        Arguments.of(PUZZLE_INPUT_4, "236"),
        Arguments.of(PUZZLE_INPUT_5, "368"),
        Arguments.of(PUZZLE_INPUT_3, "1206")
    );
  }


  @ParameterizedTest
  @MethodSource("providePuzzleInputsPart1")
  public void should_pass_part_1(String puzzleInput, String expectedScore) {
    Day12 day12 = new Day12();
    String score = day12.part1(puzzleInput);
    assertThat(score).isEqualTo(expectedScore);
  }

  @ParameterizedTest
  @MethodSource("providePuzzleInputsPart2")
  public void should_pass_part_2(String puzzleInput, String expectedScore) {
    Day12 day12 = new Day12();
    String rating = day12.part2(puzzleInput);
    assertThat(rating).isEqualTo(expectedScore);
  }
}