package org.chelonix.aoc2024.day1;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {

    ListPair parseInput(String input) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(input));
        reader.lines().forEach(line -> {
            String[] values = line.split("\\s+");
            list1.add(Integer.parseInt(values[0]));
            list2.add(Integer.parseInt(values[1]));
        });
        return new ListPair(list1, list2);
    }

    public String part1(String input) {
        ListPair lists = parseInput(input);
        Collections.sort(lists.list1());
        Collections.sort(lists.list2());
        int distance = 0;
        for (int i = 0; i < lists.list1().size(); i++) {
            distance += Math.abs(lists.list1().get(i) - lists.list2().get(i));
        }
        return "%s".formatted(distance);
    }

    public String part2(String input) {
        ListPair lists = parseInput(input);
        Map<Integer, Long> count = lists.list2().stream().collect(
                Collectors.groupingBy(Function.identity(),HashMap::new,Collectors.counting()));
        long score = lists.list1().stream()
                .mapToLong(Integer::longValue)
                .reduce(0L,
                        (long res, long elt) -> res + elt * count.getOrDefault((int)elt, 0L));
        return "%s".formatted(score);
    }
}