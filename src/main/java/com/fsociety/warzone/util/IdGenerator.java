package com.fsociety.warzone.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Private constructor to prevent instantiation
     */
    private IdGenerator() {

    }

    public static Integer generateId() {
        return counter.incrementAndGet();
    }
}
