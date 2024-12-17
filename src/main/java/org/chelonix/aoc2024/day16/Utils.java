package org.chelonix.aoc2024.day16;

public class Utils {
  // Fonction pour effacer l'écran
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
