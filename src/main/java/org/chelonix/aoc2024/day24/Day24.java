package org.chelonix.aoc2024.day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day24 {

  private Circuit parse(String input) {
    return Circuit.parse(input);
  }

  public String part1(String input) {
    Circuit circuit = parse(input);
    long value = circuit.simulate();
    return "%s".formatted(value);
  }

  public String part2(String input) throws IOException {
    Circuit circuit = parse(input);
    String graphviz = circuit.toGraphViz();
    Files.createDirectories(Path.of("target", "day24"));
    Files.writeString(Path.of("target", "day24", "graphviz.dot"), graphviz);
    return "%s".formatted("Graphviz file generated");
  }
}

