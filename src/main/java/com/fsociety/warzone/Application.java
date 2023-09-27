package com.fsociety.warzone;

import com.fsociety.warzone.game.engine.GameEngine;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("Welcome to Warzone!");
        mainMenu();
        System.out.println("Thanks for playing Warzone!");
    }

    private static void mainMenu() {
        Scanner l_scanner = new Scanner(System.in);
        String l_choice = "";
        while(!"3".equals(l_choice)) {
            System.out.println("1) Play game");
            System.out.println("2) Map Editor");
            System.out.println("3) Exit");
            System.out.println("Enter your choice: \t");
            l_choice = l_scanner.nextLine();
            switch(l_choice) {
                case "1":
                    System.out.println("New game");
                    GameEngine.play_game();
                    break;
                case "2":
                    System.out.println("Map editor");

                    break;
                case "3":
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }
}