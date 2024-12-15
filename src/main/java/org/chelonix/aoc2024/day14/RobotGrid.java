package org.chelonix.aoc2024.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotGrid {

    private record Robot(int x, int y, int vx, int vy) {}
    private final int width, height;
    private List<Robot> robots;

    private RobotGrid(List<Robot> robots, int width, int height) {
        this.width = width;
        this.height = height;
        this.robots = robots;
    }

    public void tick() {
        List<Robot> newRobots = new ArrayList<>();
        for (Robot robot: robots) {
            robot = new Robot(
                    Math.floorMod(robot.x() + robot.vx(), width),
                    Math.floorMod(robot.y() + robot.vy(), height),
                    robot.vx(),
                    robot.vy());
            newRobots.add(robot);
        }
        this.robots = newRobots;
    }

    public long safetyFactor() {
        long q1 = robots.stream().filter(r -> r.x() < width/2 && r.y() < height/2).count();
        long q2 = robots.stream().filter(r -> r.x() > width/2 && r.y() < height/2).count();
        long q3 = robots.stream().filter(r -> r.x() < width/2 && r.y() > height/2).count();
        long q4 = robots.stream().filter(r -> r.x() > width/2 && r.y() > height/2).count();
        return q1*q2*q3*q4;
    }

    public boolean canBeEasterEggCandidate() {
        return robots.stream().anyMatch(this::isSurroundedByEightRobots);
    }

    private boolean isSurroundedByEightRobots(Robot robot) {
        int x = robot.x();
        int y = robot.y();
        int[][] directions = {
                {-1, -1}, {0, -1}, {1, -1},
                {-1,  0},         {1,  0},
                {-1,  1}, {0,  1}, {1,  1}
        };
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            boolean hasNeighbor = robots.stream().anyMatch(r -> r.x() == x + dx && r.y() == y + dy);
            if (!hasNeighbor) {
                return false;
            }
        }
        return true;
    }

    public long loopSize() {
        List<Integer> loops = new ArrayList<>();
        for (Robot robot: robots) {
            int px = robot.x();
            int py = robot.y();
            Robot r = new Robot(robot.x(), robot.y(), robot.vx(), robot.vy());
            int c = 1;
            while (true) {
                r = new Robot(
                        Math.floorMod(r.x() + r.vx(), width),
                        Math.floorMod(r.y() + r.vy(), height),
                        robot.vx(),
                        robot.vy());
                if (r.x() == px && r.y() == py) {
                    loops.add(c);
                    break;
                }
                c++;
            }
        }
        return lcm(loops);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    private static int lcm(List<Integer> numbers) {
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
    }

    public String tpString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int finalX = x;
                int finalY = y;
                long count = robots.stream().filter(robot -> robot.x() == finalX && robot.y() == finalY).count();
                sb.append(count > 0 ? "%s".formatted(count) : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int width = 0, height = 0;
        private List<Robot> robots = new ArrayList<>();

        public Builder parseLine(String input) {
            Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int vx = Integer.parseInt(matcher.group(3));
                int vy = Integer.parseInt(matcher.group(4));
                robots.add(new Robot(x, y, vx, vy));
                if (width <= x) {
                    width = x + 1;
                }
                if (height <= y) {
                    height = y + 1;
                }
            }
            return this;
        }

        public Builder robots(List<Robot> robots) {
            this.robots = robots;
            return this;
        }

        public RobotGrid build() {
            return new RobotGrid(robots, width, height);
        }
    }
}
