package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class EditPreLoad extends Edit {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_MAP};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    @Override
    public void editMap(String p_fileName) {
        // TODO edit map stuff
    }
}
