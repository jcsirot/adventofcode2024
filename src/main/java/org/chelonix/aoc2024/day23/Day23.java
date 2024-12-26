package org.chelonix.aoc2024.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 {

  private record Vertex(String c1, String c2) {

  }

  private record Trio(String c1, String c2, String c3) {

  }

  private List<Vertex> parse(String input) {
    return input.lines().map(line -> {
      String[] computers = line.split("-");
      return new Vertex(computers[0], computers[1]);
    }).toList();
  }

  private static List<String> neighbours(String computer, List<Vertex> vertices) {
    return vertices.stream().filter(c -> computer.equals(c.c1) || computer.equals(c.c2))
        .map(c -> c.c1.equals(computer) ? c.c2 : c.c1).toList();
  }

  private static Map<String, Set<String>> buildAdjacencyList(List<Vertex> graph) {
    Map<String, Set<String>> adjacencyList = new HashMap<>();
    for (Vertex connection : graph) {
      adjacencyList.computeIfAbsent(connection.c1(), k -> new HashSet<>()).add(connection.c2());
      adjacencyList.computeIfAbsent(connection.c2(), k -> new HashSet<>()).add(connection.c1());
    }
    return adjacencyList;
  }

  private long containsT(Set<Set<String>> trios) {
    return trios.stream().filter(s -> s.stream().anyMatch(computer -> computer.startsWith("t")))
        .count();
  }

  public String part1(String input) {
    List<Vertex> graph = parse(input);
    Set<String> computers = new HashSet<>();
    graph.stream().forEach(c -> {
      computers.add(c.c1);
      computers.add(c.c2);
    });

    Set<Set<String>> trios = new HashSet<>();

    for (String c1 : computers) {
      for (String c2 : neighbours(c1, graph)) {
        for (String c3 : neighbours(c2, graph)) {
          for (String c4 : neighbours(c3, graph)) {
            if (c1.equals(c4)) {
              trios.add(Set.of(c1, c2, c3));
            }
          }
        }
      }
    }
    return "%s".formatted(containsT(trios));
  }

  public String part2(String input) {
    List<Vertex> graph = parse(input);
    Set<String> computers = new HashSet<>();
    graph.stream().forEach(c -> {
      computers.add(c.c1);
      computers.add(c.c2);
    });

    Set<String> maxClique = findMaxClique(graph);

    String password = maxClique.stream().toList().stream().sorted(String::compareTo).collect(Collectors.joining(","));

    System.out.println(maxClique);

    return "%s".formatted(password);
  }

  public Set<String> findMaxClique(List<Vertex> graph) {
    Map<String, Set<String>> adjacencyList = buildAdjacencyList(graph);

    Set<String> R = new HashSet<>();
    Set<String> P = new HashSet<>(adjacencyList.keySet());
    Set<String> X = new HashSet<>();

    List<Set<String>> cliques = new ArrayList<>();
    bronKerbosch(R, P, X, adjacencyList, cliques);

    return cliques.stream().max(Comparator.comparingInt(Set::size)).orElse(Set.of());
  }

  /**
   * Bron-Kerbosch algorithm
   */
  private static void bronKerbosch(Set<String> R, Set<String> P, Set<String> X,
      Map<String, Set<String>> adjacencyList, List<Set<String>> cliques) {
    if (P.isEmpty() && X.isEmpty()) {
      cliques.add(new HashSet<>(R));
      return;
    }

    String pivot = !P.isEmpty() ? P.iterator().next() : X.iterator().next();
    Set<String> neighborsOfPivot = adjacencyList.getOrDefault(pivot, Collections.emptySet());

    for (String v : new HashSet<>(P)) {
      if (!neighborsOfPivot.contains(v)) {
        Set<String> neighbors = adjacencyList.getOrDefault(v, Collections.emptySet());

        R.add(v);
        bronKerbosch(R, intersect(P, neighbors), intersect(X, neighbors), adjacencyList, cliques);
        R.remove(v);
        P.remove(v);
        X.add(v);
      }
    }
  }

  private static Set<String> intersect(Set<String> set1, Set<String> set2) {
    Set<String> result = new HashSet<>(set1);
    result.retainAll(set2);
    return result;
  }
}

