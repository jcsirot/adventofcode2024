package org.chelonix.aoc2024.day7;

public enum Operation {
    ADD {
        @Override
        public long apply(long a, long b) {
            return a + b;
        }
    },
    MUL {
        @Override
        public long apply(long a, long b) {
            return a * b;
        }
    },
    CONCAT {
        @Override
        public long apply(long a, long b) {
            return Long.parseLong(a + "" + b);
        }
    };

    public abstract long apply(long a, long b);

}
