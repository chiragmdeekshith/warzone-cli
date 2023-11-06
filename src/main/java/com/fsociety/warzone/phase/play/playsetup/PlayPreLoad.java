package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class PlayPreLoad extends PlaySetup {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.LOAD_MAP};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands();
        Console.print(help);
    }

    @Override
    public void loadMap() {

    }
}
