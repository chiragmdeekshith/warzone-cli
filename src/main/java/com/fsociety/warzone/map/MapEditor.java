package com.fsociety.warzone.map;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.util.command.CommandValidator;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;

/**
 * This class handles everything related to Editing maps. This class can be thought of as a Map Engine.
 */
public class MapEditor {
    public static EditMap d_editMap;

    /**
     * The editMap() function loads a map (creates one if it doesn't exist), validates a map, and allows the user to edit the map by adding
     * and removing countries, continents and neighbours. It also allows the user to save the new map file.
     */
    public static void editMap() {
        System.out.println("Map Editor selected. Please start by loading a map. Type 'back' to go to the previous menu.");
        String l_inputRawCommand;

        // Keep prompting the user for new commands
        while(true) {
            System.out.println("Enter command.");
            System.out.print("> ");
            l_inputRawCommand = Console.commandPrompt();

            if(!CommandValidator.isValidCommand(l_inputRawCommand, Phase.MAP_EDITOR)) {
                continue;
            }

            String[] l_splitCommand = l_inputRawCommand.split(" ");
            String l_commandType = l_splitCommand[0];

            // Logic for each command

            if(MapEditorCommand.EDIT_CONTINENT.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentBonusValue = Integer.parseInt(l_splitCommand[l_i++]);
                            if (d_editMap.addContinent(l_continentId, l_continentBonusValue)) {
                                System.out.println("Continent " + l_continentId + " with bonus of " + l_continentBonusValue + " added.");
                            }
                        }
                        case MapEditorCommand.REMOVE -> {
                            if (d_editMap.removeContinent(l_continentId)) {
                                System.out.println("Continent " + l_continentId + " removed.");
                            }
                        }
                    }
                }
            }

            if(MapEditorCommand.EDIT_COUNTRY.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                            if (d_editMap.addCountry(l_countryId, l_continentId)) {
                                System.out.println("Country " + l_countryId + " added to continent " + l_continentId + ".");
                            }
                        }
                        case MapEditorCommand.REMOVE -> {
                            if (d_editMap.removeCountry(l_countryId)) {
                                System.out.println("Country " + l_countryId + " removed.");
                            }
                        }
                    }
                }
            }

            if(MapEditorCommand.EDIT_NEIGHBOUR.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];

                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(l_splitCommand[l_i++]);
                            if (d_editMap.addNeighbour(l_countryId, l_neighbourCountryId)) {
                                System.out.println("Country " + l_neighbourCountryId + " added as neighbour to country " + l_countryId + ".");
                            }
                        }
                        case MapEditorCommand.REMOVE -> {
                            int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(l_splitCommand[l_i++]);
                            if (d_editMap.removeNeighbour(l_countryId, l_neighbourCountryId)) {
                                System.out.println("Country " + l_neighbourCountryId + " removed as neighbour to country " + l_countryId + ".");
                            }
                        }
                    }
                }
            }

        }

    }

    public static void resetMapEditor() {
        d_editMap = null;
    }

    public static boolean validateMap() {
        return MapTools.validateMap(d_editMap);
    }

    public static boolean saveMap(String p_fileName) {
        return MapTools.saveMapFile(d_editMap, p_fileName);
    }

    public static EditMap getEditMap() {
        return d_editMap;
    }

    public static void setEditMap(EditMap p_editMap) {
        d_editMap = p_editMap;
    }

}
