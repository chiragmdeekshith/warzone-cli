package com.fsociety.warzone.util;

public class StringUtils {
    public static boolean isBlank(final String str) {
        return str == null || "".equals(str);
    }
}