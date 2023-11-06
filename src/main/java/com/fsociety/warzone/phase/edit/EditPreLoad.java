package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class EditPreLoad extends Edit {

    @Override
    public void help() {
        Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_MAP};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    @Override
    public void editMap(String p_fileName) {
        // TODO edit map stuff
    }
}
