package com.fsociety.warzone;

import com.fsociety.warzone.view.Console;

/**
 * This class is the main runner for the whole game which includes the Map Editor and the Gameplay itself.
 */
public class Application {

    /**
     * The main function.
     *
     * @param p_args - command line arguments
     */
    public static void main(String[] p_args) {
        GameEngine.printLogo();
        GameEngine.mainMenu();
    }
}