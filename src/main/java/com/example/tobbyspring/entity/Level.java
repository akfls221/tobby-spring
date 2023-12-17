package com.example.tobbyspring.entity;

import java.util.Arrays;

public enum Level {

    BASIC(1),
    SILVER(2),
    GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value : " + value));
    }
}
