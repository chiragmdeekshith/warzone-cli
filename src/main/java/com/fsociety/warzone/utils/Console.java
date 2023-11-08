package com.fsociety.warzone.utils;

import com.fsociety.warzone.utils.log.Log;

import java.util.Scanner;

public class Console {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void print(String p_message) {
        System.out.println(p_message);
    }

    public static void print(String p_message, boolean p_logToFile) {
        print(p_message);
        if (p_logToFile) {
            Log.log(p_message);
        }
    }

    public static String commandPrompt() {
        System.out.print("Please enter a command.\n> ");
        return SCANNER.nextLine();
    }

}
