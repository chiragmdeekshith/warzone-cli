package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class PlayPreLoad extends PlaySetup {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.LOAD_MAP};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(help);
    }

    @Override
    public void loadMap(String p_fileName) {

    }
}
