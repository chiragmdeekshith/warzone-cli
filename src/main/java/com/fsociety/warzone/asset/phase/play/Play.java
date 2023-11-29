package com.fsociety.warzone.asset.phase.play;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands common to the PlaySetup and MainPlay phases.
 */
public abstract class Play extends Menu {

    /**
     * This method allows the user to return to the main menu during gameplay using the 'back' command.
     */
    @Override
    public void back() {
        GameplayController.resetGameState();
        GameEngine.setPhase(new Menu());
        Console.print("Returning to Main Menu...");
        GameEngine.printLogo();
    }

    /**
     * This method compiles and prints a help message of valid commands for the Play phase when the 'help' command is
     * entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.EXIT, Command.BACK};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

    // Invalid commands

    /**
     * This method prints out the invalid command message when the 'playgame' command is entered during gameplay.
     */
    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'mapeditor' command is entered during gameplay.
     */
    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }
}
