package com.fsociety.warzone.view;

import com.fsociety.warzone.view.log.Log;

import java.util.Scanner;

/**
 * This class implements console I/O for user interaction.
 */
public class Console {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * This method takes in a message and prints it.
     * @param p_message the message to be printed
     */
    public static void print(String p_message) {
        System.out.println(p_message);
    }

    /**
     * This method takes in a message and prints it, as well as sends it to the log to be written to a file.
     * @param p_message the message to be printed and logged
     * @param p_logToFile true if the message should be written to a file
     */
    public static void print(String p_message, boolean p_logToFile) {
        print(p_message);
        if (p_logToFile) {
            Log.log(p_message);
        }
    }

    /**
     * This method prompts a user for input and returns their input.
     * @return the user's input
     */
    public static String commandPrompt() {
        System.out.print("Please enter a command.\n> ");
        return SCANNER.nextLine();
    }

}
