package org.chelonix.aoc2024;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String... args) throws Exception {
        if (args.length > 0) {
            int day = Integer.parseInt(args[0]);
        }
        // runDay(1, 1, 2);
        //runDay(2, 1, 2);
        // runDay(3, 1, 2);
        // runDay(4, 1, 2);
        runDay(5, 1, 2);
    }

    private static void runDay(int day, int... parts) throws Exception {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = Main.class.getResourceAsStream("/org/chelonix/aoc2024/day%s/input.txt".formatted(day));
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            Class klass = Class.forName("org.chelonix.aoc2024.day%s.Day%s".formatted(day, day));
            for (int part: parts) {
                String result = (String) klass.getMethod("part%s".formatted(part), String.class).invoke(klass.getConstructors()[0].newInstance(), sb.toString());
                System.out.println("Day %s part %s: %s".formatted(day, part, result));
            }
        } else {
            System.out.println("Day %s: UNFINISHED".formatted(day));
        }
    }
}
