package org.chelonix.aoc2024.day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Circuit {

  private enum Operator {
    AND {
      @Override
      boolean execute(boolean in1, boolean in2) {
        return in1 && in2;
      }
    }, OR {
      @Override
      boolean execute(boolean in1, boolean in2) {
        return in1 || in2;
      }
    }, XOR {
      @Override
      boolean execute(boolean in1, boolean in2) {
        return in1 ^ in2;
      }
    };

    abstract boolean execute(boolean in1, boolean in2);
  }

  private record Operation(String in1, String in2, String out, Operator operator) {

    boolean execute(boolean in1, boolean in2) {
      return operator.execute(in1, in2);
    }

    boolean isInput(String input) {
      return in1.equals(input) || in2.equals(input);
    }
  }

  static Circuit parse(String input) {
    Map<String, Boolean> variables = new HashMap<>();
    List<Operation> operations = new ArrayList<>();

    Pattern variablePattern = Pattern.compile("([a-z]\\d{2}): (\\d)");
    Pattern operationPattern = Pattern.compile(
        "([a-z\\d]+) (AND|OR|XOR) ([a-z\\d]+) -> ([a-z\\d]+)");

    // Process each line.
    input.lines().forEach(line -> {
          Matcher variableMatcher = variablePattern.matcher(line);
          Matcher operationMatcher = operationPattern.matcher(line);

          if (variableMatcher.matches()) {
            // Parse a variable assignment.
            String name = variableMatcher.group(1);
            int value = Integer.parseInt(variableMatcher.group(2));
            variables.put(name, value == 1);
          } else if (operationMatcher.matches()) {
            // Parse an operation.
            String operand1 = operationMatcher.group(1);
            String operator = operationMatcher.group(2);
            String operand2 = operationMatcher.group(3);
            String output = operationMatcher.group(4);
            operations.add(new Operation(operand1, operand2, output, Operator.valueOf(operator)));
          }
        }
    );
    return new Circuit(operations, variables);
  }

  private final List<Operation> operations;

  private final Map<String, Boolean> variables;

  public Circuit(List<Operation> operations, Map<String, Boolean> variables) {
    this.operations = operations;
    this.variables = variables;
  }

  public Map<String, Boolean> analyze() {
    Map<String, Boolean> runtime = new HashMap<>();
    runtime.putAll(variables);
    List<Operation> ops = new LinkedList<>();
    ops.addAll(operations);
    List<Operation> excludes = new ArrayList<>();
    while (!ops.isEmpty()) {
      for (int i = 0; i < ops.size(); i++) {
        Operation op = ops.get(i);
        if (runtime.containsKey(op.in1) && runtime.containsKey(op.in2)) {
          boolean r = op.execute(runtime.get(op.in1), runtime.get(op.in2));
          runtime.put(op.out, r);
          excludes.add(op);
        }
      }
      ops.removeAll(excludes);
      excludes.clear();
    }
    return runtime.entrySet().stream().filter(e -> e.getKey().startsWith("z"))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
  }

  public long simulate(long x, long y) {
    for (int i = 0; i < 64; i++) {
      variables.put("x%02d".formatted(i), (x & (1L << i)) != 0);
    }
    for (int i = 0; i < 64; i++) {
      variables.put("y%02d".formatted(i), (y & (1L << i)) != 0);
    }

    return simulate();
  }

  public long simulate() {
    Map<String, Boolean> runtime = new HashMap<>();
    runtime.putAll(variables);
    List<Operation> ops = new LinkedList<>();
    ops.addAll(operations);
    List<Operation> excludes = new ArrayList<>();
    while (!ops.isEmpty()) {
      for (int i = 0; i < ops.size(); i++) {
        Operation op = ops.get(i);
        if (runtime.containsKey(op.in1) && runtime.containsKey(op.in2)) {
          boolean r = op.execute(runtime.get(op.in1), runtime.get(op.in2));
          runtime.put(op.out, r);
          excludes.add(op);
        }
      }
      ops.removeAll(excludes);
      excludes.clear();
    }
    String bin = runtime.entrySet().stream().filter(e -> e.getKey().startsWith("z"))
        .sorted(Comparator.comparing((Map.Entry<String, Boolean> e) -> e.getKey()).reversed())
        .map(e -> e.getValue() ? "1" : "0").collect(
            Collectors.joining());
    return Long.valueOf(bin, 2);
  }

  public String toGraphViz() {
    Set<String> X = operations.stream().flatMap(op -> List.of(op.in1, op.in2, op.out).stream())
        .filter(s -> s.startsWith("x")).collect(Collectors.toSet());
    Set<String> Y = operations.stream().flatMap(op -> List.of(op.in1, op.in2, op.out).stream())
        .filter(s -> s.startsWith("y")).collect(Collectors.toSet());
    Set<String> Z = operations.stream().flatMap(op -> List.of(op.in1, op.in2, op.out).stream())
        .filter(s -> s.startsWith("z")).collect(Collectors.toSet());

    // List<Operation> Xop = operations.stream().filter(op -> op.in1.startsWith("x") || op.in2.startsWith("x")).toList();

    StringBuilder sb = new StringBuilder();
    sb.append("digraph Circuit {\n");
    sb.append("\trankdir=LR\n");
    sb.append("\tsubgraph X {\n");
    sb.append("\t\tlabel = \"X input\";");
    for (String s : X) {
      sb.append("\t\t%s;\n".formatted(s));
    }
    sb.append("\t}\n");
    sb.append("\tsubgraph Y {\n");
    sb.append("\t\tlabel = \"Y input\";");
    for (String s : Y) {
      sb.append("\t\t%s;\n".formatted(s));
    }
    sb.append("\t}\n");
    sb.append("\tsubgraph Z {\n");
    sb.append("\t\tlabel = \"Z output\";");
    for (String s : Z) {
      sb.append("\t\t%s;\n".formatted(s));
    }
    sb.append("\t}\n");

    for (Operation op : operations) {
      sb.append("\t");
      sb.append("%s_%s_%s [label=\"%s\"];\n".formatted(op.in1, op.in2, op.out, op.operator));
    }

    for (String s : X) {
      operations.stream().filter(op -> op.isInput(s))
          .forEach(op -> sb.append("\t%s -> %s_%s_%s;\n".formatted(s, op.in1, op.in2, op.out)));
    }
    for (String s : Y) {
      operations.stream().filter(op -> op.isInput(s))
          .forEach(op -> sb.append("\t%s -> %s_%s_%s;\n".formatted(s, op.in1, op.in2, op.out)));
    }

    for (Operation operation : operations) {
      if (operation.out.startsWith("z")) {
        sb.append("\t%s_%s_%s -> %s\n".formatted(operation.in1, operation.in2, operation.out,
            operation.out()));
      } else {
        operations.stream().filter(op -> op.isInput(operation.out)).forEach(op -> sb.append(
            "\t%s_%s_%s -> %s_%s_%s [label=\"%s\"];\n".formatted(operation.in1, operation.in2,
                operation.out, op.in1, op.in2, op.out, operation.out)));
      }
    }

    sb.append("}");

    return sb.toString();
  }
}
