package com.fsociety.warzone.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used for generating IDs
 */
public class IdGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Private constructor to prevent instantiation
     */
    private IdGenerator() {

    }

    /**
     * Generate an ID
     * @return the generated ID
     */
    public static Integer generateId() {
        return counter.incrementAndGet();
    }
}
