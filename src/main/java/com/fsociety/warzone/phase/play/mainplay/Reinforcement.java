package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.command.Command;

public class Reinforcement extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.DEPLOY};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]\n";
        Console.print(help);
    }

    @Override
    public void deploy(String[] p_args) {

    }

}
