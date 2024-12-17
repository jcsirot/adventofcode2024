package org.chelonix.aoc2024.day16;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day16Test {

  private static final String PUZZLE_INPUT_1 = """
      ###############
      #.......#....E#
      #.#.###.#.###.#
      #.....#.#...#.#
      #.###.#####.#.#
      #.#.#.......#.#
      #.#.#####.###.#
      #...........#.#
      ###.#.#####.#.#
      #...#.....#.#.#
      #.#.#.###.#.#.#
      #.....#...#.#.#
      #.###.#.#.#.#.#
      #S..#.....#...#
      ###############""";

  private static final String PUZZLE_INPUT_2 = """
      #################
      #...#...#...#..E#
      #.#.#.#.#.#.#.#.#
      #.#.#.#...#...#.#
      #.#.#.#.###.#.#.#
      #...#.#.#.....#.#
      #.#.#.#.#.#####.#
      #.#...#.#.#.....#
      #.#.#####.#.###.#
      #.#.#.......#...#
      #.#.###.#####.###
      #.#.#...#.....#.#
      #.#.#.#####.###.#
      #.#.#.........#.#
      #.#.#.#########.#
      #S#.............#
      #################""";


  private static final String PUZZLE_INPUT_3 = """
      ###########################
      #######################..E#
      ######################..#.#
      #####################..##.#
      ####################..###.#
      ###################..##...#
      ##################..###.###
      #################..####...#
      ################..#######.#
      ###############..##.......#
      ##############..###.#######
      #############..####.......#
      ############..###########.#
      ###########..##...........#
      ##########..###.###########
      #########..####...........#
      ########..###############.#
      #######..##...............#
      ######..###.###############
      #####..####...............#
      ####..###################.#
      ###..##...................#
      ##..###.###################
      #..####...................#
      #.#######################.#
      #S........................#
      ###########################""";

  private static final String PUZZLE_INPUT_4 = """
      ####################################################
      #......................................#..........E#
      #......................................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.................#...........#
      #....................#.............................#
      #S...................#.............................#
      ####################################################""";


  private static final String PUZZLE_INPUT_5 = """
      #######
      #....E#
      ##.#.##
      #.....#
      #S#...#
      #######""";

  static Stream<Arguments> providePuzzleInputsPart1() {
    return Stream.of(
        Arguments.of(PUZZLE_INPUT_1, "7036"),
        Arguments.of(PUZZLE_INPUT_2, "11048"),
        Arguments.of(PUZZLE_INPUT_3, "21148"),
        Arguments.of(PUZZLE_INPUT_4, "5078")
    );
  }

  @ParameterizedTest
  @MethodSource("providePuzzleInputsPart1")
  public void should_pass_part_1(String input, String expected) {
    Day16 day16 = new Day16();
    String sum = day16.part1(input);
    assertThat(sum).isEqualTo(expected);
  }


  static Stream<Arguments> providePuzzleInputsPart2() {
    return Stream.of(
        Arguments.of(PUZZLE_INPUT_1, "45"),
        Arguments.of(PUZZLE_INPUT_2, "64"),
        Arguments.of(PUZZLE_INPUT_3, "149"),
        Arguments.of(PUZZLE_INPUT_4, "413"),
        Arguments.of(PUZZLE_INPUT_5, "11")
    );
  }

  @ParameterizedTest
  @MethodSource("providePuzzleInputsPart2")
  public void should_pass_part_2(String input, String expected) {
    Day16 day16 = new Day16();
    String sum = day16.part2(input);
    assertThat(sum).isEqualTo(expected);
  }

}