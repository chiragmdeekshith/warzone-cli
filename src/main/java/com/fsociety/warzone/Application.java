package com.fsociety.warzone;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.MapEditor;
import com.fsociety.warzone.util.Console;

import java.util.Scanner;

/**
 * This class is the main runner for the whole game which includes the Map Editor and the Gameplay itself.
 *
 */
public class Application {
    /**
     * The main function.
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Warzone!");
        GameRunner.mainMenu();
    }
}