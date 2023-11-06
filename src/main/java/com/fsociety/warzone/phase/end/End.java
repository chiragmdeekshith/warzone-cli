package com.fsociety.warzone.phase.end;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.phase.Menu;
import com.fsociety.warzone.util.Console;

public class End extends Menu {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.BACK};

    @Override
    public void showMap() {

    }

    @Override
    public void back() {

    }

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands();
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
