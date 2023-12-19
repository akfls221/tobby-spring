package com.example.tobbyspring.entity;

import java.util.Arrays;

public enum Level {

    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    private final int value;
    private final Level nextLevel;

    Level(int value, Level nextLevel) {
        this.value = value;
        this.nextLevel = nextLevel;
    }

    public int intValue() {
        return this.value;
    }

    public Level nextLevel() {
        return this.nextLevel;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value : " + value));
    }
}
