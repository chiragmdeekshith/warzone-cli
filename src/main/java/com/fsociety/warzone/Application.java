package com.fsociety.warzone;

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
        System.out.println("Welcome to Warzone!");
        GameRunner.mainMenu();
    }
}