package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.phase.play.Play;
import com.fsociety.warzone.util.Console;

public abstract class PlaySetup extends Play {
    @Override
    public void help() {
        Command[] l_validCommands = {Command.EXIT, Command.BACK};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(help);
    }
}
