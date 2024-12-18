package org.chelonix.aoc2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

  private Program parse(String input) {
    return new Program(input);
  }

  public String part1(String input) {
    Program program = parse(input);
    CPU cpu = new CPU();
    String output = cpu.run(program).stream().map(String::valueOf).collect(Collectors.joining(","));
    return "%s".formatted(output);
  }

  public String part2(String input) {
    Program program = parse(input);
    CPU cpu = new CPU();
//    System.out.println(cpu.decompile(program));
    long prefix = 0;
    List<Long> prefixes = new ArrayList<>();
    step(program.code().size()-1, prefix, program, cpu, prefixes);
//    for (long a : prefixes) {
//      Program p = new Program(a, program.b(), program.c(), program.code()); // Remove the last jump instruction
//      String output = cpu.run(p).stream().map(String::valueOf).collect(Collectors.joining(","));
//      System.out.println("%s -> %s".formatted(a, output));
//    }
    return "%s".formatted("%s".formatted(prefixes.get(0)));
  }

  private void step(int index, long prefix, Program program, CPU cpu, List<Long> prefixes) {
    if (index < 0) {
      prefixes.add(prefix);
      return;
    }
    long target = program.code().get(index);
    for (long v = 0; v < 8; v++) {
      long a = prefix << 3 | v;
      Program loop = new Program(a, program.b(), program.c(), program.code().subList(0, program.code().size() - 2)); // Remove the last jump instruction
      if (cpu.run(loop).get(0).equals(target)) {
        step(index - 1, a, program, cpu, prefixes);
      }
    }
  }
}
