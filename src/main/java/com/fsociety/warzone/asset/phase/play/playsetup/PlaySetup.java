package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.play.Play;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands common to the PlayPreLoad and PlayPostLoad phases.
 */
public abstract class PlaySetup extends Play {

    /**
     * This method compiles and prints a help message of valid commands for the PlaySetup phase when the 'help' command
     * is entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.EXIT, Command.BACK};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

}
