package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.play.Play;
import com.fsociety.warzone.view.Console;

public abstract class PlaySetup extends Play {
    @Override
    public void help() {
        Command[] l_validCommands = {Command.EXIT, Command.BACK};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }
}
