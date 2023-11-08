package com.fsociety.warzone;

import com.fsociety.warzone.view.Console;

/**
 * This class is the main runner for the whole game which includes the Map Editor and the Gameplay itself.
 *
 */
public class Application {
    /**
     * The main function.
     *
     * @param p_args - command line arguments
     */
    public static void main(String[] p_args) {
        Console.print("""
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""");
        GameEngine.mainMenu();
    }
}