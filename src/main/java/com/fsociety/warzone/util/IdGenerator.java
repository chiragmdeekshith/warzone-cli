package com.fsociety.warzone.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static final AtomicLong counter = new AtomicLong(0);

    /**
     * Private constructor to prevent instantiation
     */
    private IdGenerator() {

    }

    public static Long generateId() {
        return counter.incrementAndGet();
    }
}
