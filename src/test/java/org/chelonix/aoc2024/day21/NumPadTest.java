package org.chelonix.aoc2024.day21;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class NumPadTest {

  @Test
  void generatePaths() {
    NumPad pad = new NumPad();
    List<String> paths = pad.generatePaths("A", "1");
    System.out.println(paths);
  }
}