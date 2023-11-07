package com.fsociety.warzone.phase.play;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.phase.Menu;
import com.fsociety.warzone.util.Console;

public abstract class Play extends Menu {


    @Override
    public void back() {
        GameEngine.resetGameState();
        GameRunner.setPhase(new Menu());
        Console.print("Returning to Main Menu...");
        Console.print("""
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""");
    }

    @Override
    public void help() {
        Command[] l_validCommands = {Command.EXIT, Command.BACK};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

    // Invalid commands

    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }
}
