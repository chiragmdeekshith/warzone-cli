package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.MapEditorController;
import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.view.Console;

import java.util.Map;
import java.util.Set;

/**
 * This Class implements the commands that are valid for map editing after a map has been loaded.
 */
public class EditPostLoad extends Edit {

    /**
     * This method compiles and prints a help message of valid commands for the EditPostLoad phase when the 'help'
     * command is entered.
     */
    @Override
    public void help() {
        Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_COUNTRY, Command.EDIT_CONTINENT, Command.EDIT_NEIGHBOUR,
                Command.SAVE_MAP, Command.LOAD_MAP, Command.VALIDATE_MAP, Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: \n" +
                getValidCommands(d_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(help);
    }

    /**
     * This method allows the user to save the map being edited by entering the 'savemap' command.
     * @param p_fileName the name of file to save the map to
     */
    @Override
    public void saveMap(String p_fileName) {
        if(!MapEditorController.saveMap(p_fileName)) {
            Console.print("File save for file \"" + p_fileName + "\" failed!");
            return;
        }
        Console.print("File saved successfully: \"" + p_fileName + "\".");
    }

    /**
     * This method allows the user to save the map being edited by entering the 'savegame' command.
     * @param p_fileName the name of file to save the map to
     */
    @Override
    public void saveGame(String p_fileName) {
        if(!GameplayController.saveGame(p_fileName)) {
            Console.print("File save for file \"" + p_fileName + "\" failed!");
            return;
        }
        Console.print("File saved successfully: \"" + p_fileName + "\".");
    }

    /**
     * This method allows the player to add or remove continents using the 'editcontinent' command.
     * @param p_continentsToAdd the array of continents to be added to the map
     * @param p_continentsToRemove the array of continents to be removed from the map
     */
    @Override
    public void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove){
        EditMap l_editMap = MapEditorController.getEditMap();
        for(Map.Entry<Integer, Integer> l_entry: p_continentsToAdd.entrySet()) {
            if(l_editMap.addContinent(l_entry.getKey(), l_entry.getValue())){
                Console.print("Added continent " + l_entry.getKey() + " with bonus value of " + l_entry.getValue());
            }
        }
        for(Integer l_continentId: p_continentsToRemove) {
            if(l_editMap.removeContinent(l_continentId)) {
                Console.print("Removed continent " + l_continentId);
            }
        }
    }

    /**
     * This method allows the player to add or remove countries using the 'editcountry' command.
     * @param p_countriesToAdd the countries to be added to the map
     * @param p_countriesToRemove the countries to be removed from the map
     */
    @Override
    public void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove) {
        EditMap l_editMap = MapEditorController.getEditMap();
        for(Map.Entry<Integer, Integer> l_entry: p_countriesToAdd.entrySet()) {
            if(l_editMap.addCountry(l_entry.getKey(), l_entry.getValue())){
                Console.print("Added country " + l_entry.getKey() + " in continent " + l_entry.getValue());
            }
        }
        for(Integer l_countryId: p_countriesToRemove) {
            if(l_editMap.removeCountry(l_countryId)) {
                Console.print("Removed country " + l_countryId);
            }
        }
    }

    /**
     * This method allows the player to add or remove a country's neighbours using the 'editcontinent' command.
     * @param p_neighboursToAdd the countries' neighbours to be added to the map
     * @param p_neighboursToRemove the countries' neighbours to be removed from the map
     */
    @Override
    public void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove) {
        EditMap l_editMap = MapEditorController.getEditMap();
        for(Map.Entry<Integer, Integer> l_entry: p_neighboursToAdd.entrySet()) {
            if(l_editMap.addNeighbour(l_entry.getKey(), l_entry.getValue())) {
                Console.print("Added neighbour " + l_entry.getValue() + " to country " + l_entry.getKey());
            }
        }
        for(Map.Entry<Integer, Integer> l_entry: p_neighboursToRemove.entrySet()) {
            if(l_editMap.removeNeighbour(l_entry.getKey(), l_entry.getValue())) {
                Console.print("Removed neighbour " + l_entry.getValue() + " from country " + l_entry.getKey());
            }
        }
    }

    /**
     * This method allows the user to validate the map being edited using the 'validatemap' command.
     */
    @Override
    public void validateMap() {
        if(!MapEditorController.validateMap()) {
            Console.print("The map is not valid!");
            return;
        }
        Console.print("The map is valid!");
    }

    /**
     * This method calls the map to be printed during map editing using the 'showmap' command.
     */
    @Override
    public void showMap() {
        MapEditorController.getEditMap().showMap();
    }
}
