package com.fsociety.warzone;

import com.fsociety.warzone.util.Console;

/**
 * This class is the main runner for the whole game which includes the Map Editor and the Gameplay itself.
 *
 */
public class Application {
    /**
     * The main function.
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        Console.print("                                           \n" +
                " _ _ _ _____ _____ _____ _____ _____ _____ \n" +
                "| | | |  _  | __  |__   |     |   | |   __|\n" +
                "| | | |     |    -|   __|  |  | | | |   __|\n" +
                "|_____|__|__|__|__|_____|_____|_|___|_____|\n" +
                "                                           ");
        GameRunner.mainMenu();
    }
}