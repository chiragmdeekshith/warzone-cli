package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.controllers.MapEditorController;
import com.fsociety.warzone.phase.Menu;
import com.fsociety.warzone.utils.Console;

public abstract class Edit extends Menu {

    @Override
    public void back() {
        MapEditorController.resetMapEditor();
        GameRunner.setPhase(new Menu());
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
