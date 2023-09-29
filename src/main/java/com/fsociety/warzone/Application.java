package com.fsociety.warzone;

import com.fsociety.warzone.game.GameEngine;

import java.util.Scanner;

public class Application {

    public static Scanner SCANNER;
    public static void main(String[] args) {
        Application.SCANNER = new Scanner(System.in);
        System.out.println("Welcome to Warzone!");
        mainMenu();
        System.out.println("Thanks for playing Warzone!");
    }

    private static void mainMenu() {
        String l_choice;
        do {
            System.out.println("1) Play game");
            System.out.println("2) Map Editor");
            System.out.println("3) Exit");
            System.out.println("Enter your choice: \t");
            l_choice = Application.SCANNER.nextLine();
            switch(l_choice) {
                case "1":
                    System.out.println("New game");
                    GameEngine.playGame();
                    break;
                case "2":
                    System.out.println("Map Editor");
                    break;
                case "3":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while(!"3".equals(l_choice));
    }
}