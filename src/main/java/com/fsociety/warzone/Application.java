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

    // Global scanner for the whole program
    public static Scanner SCANNER;
    public static Console CONSOLE;

    /**
     * The main function.
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        Application.SCANNER = new Scanner(System.in);
        Application.CONSOLE = new Console();
        System.out.println("Welcome to Warzone!");
        mainMenu();
        System.out.println("Thanks for playing Warzone!");
    }

    /**
     * This function starts the main menu for the game.
     */
    private static void mainMenu() {
        String l_choice;
        do {
            System.out.println("1) Play game");
            System.out.println("2) Map Editor");
            System.out.println("3) Exit");
            System.out.println("Enter your choice: \t");
            l_choice = Application.SCANNER.nextLine();
            switch(l_choice) {
                case "1" -> {
                    System.out.println("New game");
                    GameEngine.playGame();
                }
                case "2" -> {
                    System.out.println("Map Editor");
                    MapEditor.editMap();
                }
                case "3" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");

            }
        } while(true);
    }
}