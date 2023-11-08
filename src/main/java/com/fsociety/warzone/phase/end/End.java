package com.fsociety.warzone.phase.end;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.phase.Menu;
import com.fsociety.warzone.utils.Console;

public class End extends Menu {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.BACK, Command.EXIT};

    @Override
    public void showMap() {
        GameEngineController.getPlayMap().showMap();
    }

    @Override
    public void back() {
        GameEngineController.resetGameState();
        GameRunner.setPhase(new Menu());
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
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
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
