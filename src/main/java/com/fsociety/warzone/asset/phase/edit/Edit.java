package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.controller.MapEditorController;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands common to the EditPostLoad and EditPreLoad phases.
 */
public abstract class Edit extends Menu {

    /**
     * This method allows the user to return to the main menu during map editing using the 'back' command.
     */
    @Override
    public void back() {
        MapEditorController.resetMapEditor();
        GameEngine.setPhase(new Menu());
        Console.print("Returning to Main Menu...");
        GameEngine.printLogo();
    }

    /**
     * This method compiles and prints a help message of valid commands for the Edit phase when the 'help' command is
     * entered.
     */
    @Override
    public void help() {
        Command[] d_validCommands = {Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    // Invalidate commands

    /**
     * This method prints out the invalid command message when the 'playgame' command is entered during map editing.
     */
    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'mapeditor' command is entered during map editing.
     */
    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }
}
