package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.util.Console;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.Map;
import java.util.Set;

public class EditPostLoad extends Edit {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_COUNTRY, Command.EDIT_CONTINENT, Command.EDIT_NEIGHBOUR,
            Command.SAVE_MAP, Command.LOAD_MAP, Command.VALIDATE_MAP};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" +
                getValidCommands(d_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(help);
    }

    @Override
    public void saveMap(String p_fileName) {
        // TODO save map
    }

    @Override
    public void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove){
        // TODO edit continent stuff
    }

    @Override
    public void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove) {
        // TODO edit country stuff
    }

    @Override
    public void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove) {
        // TODO edit neighbour stuff
    }

    @Override
    public void validateMap() {

    }

    @Override
    public void showMap() {

    }
}
