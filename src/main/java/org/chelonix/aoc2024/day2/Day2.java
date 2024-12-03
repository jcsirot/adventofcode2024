package org.chelonix.aoc2024.day2;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.IntStream;

public class Day2 {

    List<List<Long>> parseInput(String input) {
        List<List<Long>> reports = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(input));
        reader.lines().forEach(line -> reports.add(Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList()));
        return reports;
    }

    public String part1(String input) {
        List<List<Long>> reports = parseInput(input);
        long count = reports.stream().filter(report -> isSafe(report, false)).count();
        return "%s".formatted(count);
    }

    public String part2(String input) {
        List<List<Long>> reports = parseInput(input);
        long count = reports.stream().filter(report -> isSafe(report, true)).count();
        return "%s".formatted(count);
    }

    private boolean isSafe(List<Long> report, boolean skipAllowed) {
        if (skipAllowed) {
            if (isSafe(report, false)) {
                return true;
            }
            for (int i = 0; i < report.size(); i++) {
                List<Long> newReport = new ArrayList<>(report);
                newReport.remove(i);
                if (isSafe(newReport, false)) {
                    return true;
                }
            }
            return false;
        }
        List<Long> delta = IntStream.range(0, report.size() - 1).mapToObj(i -> report.get(i + 1) - report.get(i)).toList();
        return delta.stream().allMatch(n -> (n >= -3 && n <= -1)) ||
                delta.stream().allMatch(n -> (n >= 1 && n <= 3));
    }
}