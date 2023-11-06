package com.fsociety.warzone.util;

import java.util.Scanner;

public class Console {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void print(String p_message) {
        System.out.println(p_message);
    }

    public static void print(String p_message, boolean p_logToFile) {
        print(p_message);
        if (p_logToFile) {
            //Call the logger here.
        }
    }

    public static String commandPrompt() {
        System.out.println("Please enter a command.");
        System.out.println("> ");
        return SCANNER.nextLine();
    }

}
