package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class Attack extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.ADVANCE, Command.BOMB, Command.BLOCKADE, Command.AIRLIFT,
            Command.NEGOTIATE, Command.COMMIT};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]";
        Console.print(help);
    }

    @Override
    public void advance() {

    }

    @Override
    public void bomb() {

    }

    @Override
    public void blockade() {

    }

    @Override
    public void airlift() {

    }

    @Override
    public void negotiate() {

    }

    @Override
    public void commit() {

    }
}
