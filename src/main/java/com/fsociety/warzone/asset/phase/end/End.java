package com.fsociety.warzone.asset.phase.end;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands that are valid when the game ends after a player is declared the winner.
 */
public class End extends Menu {

    /**
     * This method calls the map to be printed when the game is won using the 'showmap' command.
     */
    @Override
    public void showMap() {
        GameplayController.getPlayMap().showMap();
    }

    /**
     * This method allows the user to return to the main menu once a game has ended using the 'back' command.
     */
    @Override
    public void back() {
        GameplayController.resetGameState();
        GameEngine.setPhase(new Menu());
        Console.print("""
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""");
    }

    /**
     * This method compiles and prints a help message of valid commands for the End phase when the 'help' command is
     * entered.
     */
    @Override
    public void help() {
        Command[] d_validCommands = {Command.SHOW_MAP, Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    // Invalid commands

    /**
     * This method prints out the invalid command message when the 'playgame' command is entered once the game is over.
     */
    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'mapeditor' command is entered once the game is over.
     */
    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }

}
