package com.fsociety.warzone;

import com.fsociety.warzone.command.CommandProcessor;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.MapEditor;
import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;

public class GameRunner {

    private Phase d_phase;

    /**
     * This function starts the main menu for the game.
     */
    public static void mainMenu() {
        Console.print("1) Play Game");
        Console.print("2) Map Editor");
        Console.print("3) Exit");
        String l_command;
        boolean result;
        do {
            l_command = Console.commandPrompt();
            result = CommandProcessor.processCommand(l_command);

//            switch(l_command) {
//                case "1" -> {
//                    Console.print("New Game");
//                    GameEngine.playGame();
//                }
//                case "2" -> {
//                    Console.print("Map Editor");
//                    MapEditor.editMap();
//                }
//                case "3" -> {
//                    Console.print("Exiting...");
//                    return;
//                }
//                default -> Console.print("Invalid choice!");
//            }
        } while(result);
    }

    public Phase getPhase() {
        return d_phase;
    }

    public void setPhase(Phase p_phase) {
        this.d_phase = p_phase;
    }
}
