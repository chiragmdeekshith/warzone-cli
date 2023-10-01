package com.fsociety.warzone.util;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class UserInstructionUtils {

    public static void promptUser(
            final String message,
            final String... options) {
        final AtomicInteger optionNumber = new AtomicInteger();
        System.out.println(message);
        Arrays.stream(options).forEach(option -> {
            System.out.println(optionNumber.incrementAndGet() + " -> " + option);
        });
        if (optionNumber.get() > 0) {
            System.out.print("Your Selection : ");
        }
    }
}