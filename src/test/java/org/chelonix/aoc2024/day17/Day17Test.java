package org.chelonix.aoc2024.day17;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day17Test {

  private static final String PUZZLE_INPUT_1 = """
      Register A: 729
      Register B: 0
      Register C: 0
      
      Program: 0,1,5,4,3,0""";

  private static final String PUZZLE_INPUT_2 = """
      Register A: 2024
      Register B: 0
      Register C: 0
      
      Program: 0,1,5,4,3,0""";

  private static final String PUZZLE_INPUT_3 = """
    Register A: 117440
    Register B: 0
    Register C: 0
  
    Program: 0,3,5,4,3,0""";

  private static final String PUZZLE_INPUT_4 = """
      Register A: 107416732707226
      Register B: 0
      Register C: 0
      
      Program: 2,4,1,5,7,5,1,6,4,3,5,5,0,3,3,0""";

  private static final String PUZZLE_INPUT_5 = """
      Register A: 107416732707226
      Register B: 0
      Register C: 0
      
      Program: 2,4,1,5,7,5,1,6,4,3,5,5,0,3,3,0""";

  static Stream<Arguments> providePuzzleInputsPart1() {
    return Stream.of(
        Arguments.of(PUZZLE_INPUT_1, "4,6,3,5,6,3,5,2,1,0"),
        Arguments.of(PUZZLE_INPUT_2, "4,2,5,6,7,7,7,7,3,1,0"),
        Arguments.of(PUZZLE_INPUT_3, "0,3,5,4,3,0"),
        Arguments.of(PUZZLE_INPUT_4, "2,4,1,5,7,5,1,6,4,3,5,5,0,3,3,0")
    );
  }

  @ParameterizedTest
  @MethodSource("providePuzzleInputsPart1")
  public void should_pass_part_1(String input, String expected) {
    Day17 day17 = new Day17();
    String output = day17.part1(input);
    assertThat(output).isEqualTo(expected);
  }
}