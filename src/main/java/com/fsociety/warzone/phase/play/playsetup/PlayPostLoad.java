package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

public class PlayPostLoad extends PlaySetup{

    Command[] d_validCommands = {Command.SHOW_MAP, Command.GAME_PLAYER, Command.ASSIGN_COUNTRIES};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands() +
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
