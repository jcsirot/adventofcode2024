package org.chelonix.aoc2024.day3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public String part1(String input) {
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(input);
        int sum = 0;
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            sum += x * y;
        }
        return "%s".formatted(sum);
    }

    public String part2(String input) {
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(input);
        int sum = 0;
        boolean enabled = true;
        while (matcher.find()) {
            if ("do()".equals(matcher.group(0))) {
                enabled = true;
            } else if ("don't()".equals(matcher.group(0))) {
                enabled = false;
            } else if (enabled) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                sum += x * y;
            }
        }
        return "%s".formatted(sum);
    }
}