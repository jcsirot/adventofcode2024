package org.chelonix.aoc2024.day13;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinearSystem {

    public static record Solution(long x, long y) {}

    private final long m1, m2, m3, m4; // Matrix
    private final long v1, v2; // Vector

    public LinearSystem(long m1, long m2, long m3, long m4, long v1, long v2) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Optional<Solution> solve() {
        return solve(m1, m2, m3, m4, v1, v2);
    }

    public Optional<Solution> solveLonger() {
        return solve(m1, m2, m3, m4, v1+10000000000000L, v2+10000000000000L);
    }

    private Optional<Solution> solve(long m1, long m2, long m3, long m4, long v1, long v2) {
        long det = m1 * m4 - m2 * m3;
        long detX = v1 * m4 - v2 * m2;
        long detY = m1 * v2 - m3 * v1;
        long x = detX / det;
        long y = detY / det;
        // Verify if x & y are solutions
        if (m1 * x + m2 * y != v1 || m3 * x + m4 * y != v2) {
            return Optional.empty();
        }
        return Optional.of(new Solution(x, y));
    }

    public static LinearSystem parse(List<String> input) {
        String[] lines = input.toArray(String[]::new);

        Pattern pattern = Pattern.compile("X[+=](\\d+), Y[+=](\\d+)");
        Matcher matcher;

        long m1 = 0L, m2 = 0L, m3 = 0L, m4 = 0L, v1 = 0L, v2 = 0L;

        matcher = pattern.matcher(lines[0]);
        if (matcher.find()) {
            m1 = Long.parseLong(matcher.group(1));
            m3 = Long.parseLong(matcher.group(2));
        }

        matcher = pattern.matcher(lines[1]);
        if (matcher.find()) {
            m2 = Long.parseLong(matcher.group(1));
            m4 = Long.parseLong(matcher.group(2));
        }

        pattern = Pattern.compile("X=(\\d+), Y=(\\d+)");
        matcher = pattern.matcher(lines[2]);
        if (matcher.find()) {
            v1 = Long.parseLong(matcher.group(1));
            v2 = Long.parseLong(matcher.group(2));
        }
        return new LinearSystem(m1, m2, m3, m4, v1, v2);
    }
}
