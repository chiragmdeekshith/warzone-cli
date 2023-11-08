package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.controller.MapEditorController;
import com.fsociety.warzone.view.Console;

public abstract class Edit extends Menu {

    @Override
    public void back() {
        MapEditorController.resetMapEditor();
        GameEngine.setPhase(new Menu());
    }

    @Override
    public void help() {
        Command[] d_validCommands = {Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    // Invalidate commands

    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }
}
