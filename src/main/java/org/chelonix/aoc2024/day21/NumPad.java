package org.chelonix.aoc2024.day21;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumPad {

  private final Set<Button> buttons = Set.of(
      new Button("7", 0, 0),
      new Button("8", 1, 0),
      new Button("9", 2, 0),
      new Button("4", 0, 1),
      new Button("5", 1, 1),
      new Button("6", 2, 1),
      new Button("1", 0, 2),
      new Button("2", 1, 2),
      new Button("3", 2, 2),
      new Button("0", 1, 3),
      new Button("A", 2, 3)
  );

  private String current = "A";

  public List<String> generatePaths(String to) {
    List<String> allPaths = generatePaths(current, to);
    current = to;
    return allPaths;
  }

  public List<String> generatePaths(String from, String to) {
    Button fromButton = buttons.stream().filter(b -> from.equals(b.label())).findFirst().get();
    Button toButton = buttons.stream().filter(b -> to.equals(b.label())).findFirst().get();

    String v = toButton.y() >= fromButton.y() ? "v" : "^";
    String h = toButton.x() >= fromButton.x() ? ">" : "<";
    int diffX = Math.abs(toButton.x() - fromButton.x());
    int diffY = Math.abs(toButton.y() - fromButton.y());

    List<String> paths = new ArrayList<>();
    generatePaths(diffX, h, diffY, v, "", fromButton.x(), fromButton.y(), paths);

    return paths;
  }

  public static void generatePaths(int hc, String h, int vc, String v, String current, int x, int y,
      List<String> paths) {
    if (x == 0 && y == 3) {
      return;
    }
    if (hc == 0 && vc == 0) {
      paths.add(current + "A");
      return;
    }

    if (hc > 0) {
      generatePaths(hc - 1, h, vc, v, current + h, h.equals(">") ? x + 1 : x - 1, y, paths);
    }

    if (vc > 0) {
      generatePaths(hc, h, vc - 1, v, current + v, x, v.equals("v") ? y + 1 : y - 1, paths);
    }
  }
}
