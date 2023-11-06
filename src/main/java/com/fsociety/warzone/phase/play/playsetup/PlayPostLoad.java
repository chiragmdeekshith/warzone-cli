package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class PlayPostLoad extends PlaySetup{

    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.GAME_PLAYER, Command.ASSIGN_COUNTRIES};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(help);
    }

    @Override
    public void showMap() {

    }

    @Override
    public void assignCountries() {

    }

    @Override
    public void gamePlayer() {

    }
}
