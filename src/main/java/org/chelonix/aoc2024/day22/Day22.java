package org.chelonix.aoc2024.day22;

import java.math.BigInteger;
import java.util.*;

public class Day22 {

    private record ChangeSequence(int c1, int c2, int c3, int c4) {};

    private List<Long> parse(String input) {
        return input.lines().map(Long::parseLong).toList();
    }

    private static BigInteger mix(BigInteger a, BigInteger b) {
        return a.xor(b);
    }

    private static final BigInteger _16777216 = BigInteger.valueOf(16777216);

    private static BigInteger prune(BigInteger l) {
        return l.mod(_16777216);
    }

    private BigInteger round(BigInteger secret) {
        BigInteger v = secret.shiftLeft(6);
        secret = mix(v, secret);
        secret = prune(secret);
        v = secret.shiftRight(5);
        secret = mix(v, secret);
        secret = prune(secret);
        v = secret.shiftLeft(11);
        secret = mix(v, secret);
        secret = prune(secret);
        return secret;
    }

    public String part1(String input) {
        List<Long> init = parse(input);
        BigInteger sum = BigInteger.ZERO;
        for (long s: init) {
            BigInteger secret = BigInteger.valueOf(s);
            for (int i = 0; i < 2000; i++) {
                secret = round(secret);
            }
            sum = sum.add(secret);
        }
        return "%s".formatted(sum);
    }

    public String part2(String input) {
        List<Long> init = parse(input);
        Set<ChangeSequence> seenSequences = new HashSet<>();
        List<Map<ChangeSequence, Integer>> sequences = new ArrayList<>();
        for (long s: init) {
            List<Integer> secrets = new ArrayList<>();
            BigInteger secret = BigInteger.valueOf(s);
            secrets.add(secret.mod(BigInteger.TEN).intValue());
            for (int i = 0; i < 2000; i++) {
                secret = round(secret);
                secrets.add(secret.mod(BigInteger.TEN).intValue());
            }
            Map<ChangeSequence, Integer> changes = new HashMap<>();
            for (int i = 4; i < secrets.size(); i++) {
                int s0 = secrets.get(i-4);
                int s1 = secrets.get(i-3);
                int s2 = secrets.get(i-2);
                int s3 = secrets.get(i-1);
                int s4 = secrets.get(i);
                ChangeSequence change = new ChangeSequence(s1-s0, s2-s1, s3-s2, s4-s3);
                int price = changes.getOrDefault(change, -1);
                if (price == -1) {
                    changes.put(change, s4);
                }
                seenSequences.add(change);
            }
            sequences.add(changes);
        }
        int bestPrice = 0;
        for (ChangeSequence change: seenSequences) {
            bestPrice = Math.max(bestPrice, sequences.stream().mapToInt(changes -> changes.getOrDefault(change, 0)).sum());
        }
        return "%s".formatted(bestPrice);
    }
}
