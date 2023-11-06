package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

public class EditPostLoad extends Edit {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_COUNTRY, Command.EDIT_CONTINENT, Command.EDIT_NEIGHBOUR,
            Command.SAVE_MAP, Command.LOAD_MAP, Command.VALIDATE_MAP};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands() +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(help);
    }

    @Override
    public void saveMap() {

    }

    @Override
    public void editContinent() {

    }

    @Override
    public void editCountry() {

    }

    @Override
    public void editNeighbour() {

    }

    @Override
    public void validateMap() {

    }

    @Override
    public void showMap() {

    }
}
