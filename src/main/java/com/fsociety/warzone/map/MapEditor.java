package com.fsociety.warzone.map;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.util.command.CommandValidator;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;

/**
 * This class handles everything related to Editing maps. This class can be thought of as a Map Engine.
 */
public class MapEditor {
    private static EditMap d_editMap;

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
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(!CommandValidator.isValidCommand(l_inputRawCommand, Phase.MAP_EDITOR)) {
                continue;
            }

            String[] l_splitCommand = l_inputRawCommand.split(" ");
            String l_commandType = l_splitCommand[0];

            // Return to main menu
            if(MapEditorCommand.BACK.getCommand().equals(l_commandType)) {
                d_editMap = null;
                break;
            }

            // Try to load map first before any other command

            if(MapEditorCommand.EDIT_MAP.getCommand().equals(l_commandType)) {
                String l_filename = l_splitCommand[1];
                d_editMap = MapTools.loadAndValidateMap(l_filename);
                if(null == d_editMap) {
                    System.out.println("Failed to load the map from file! Please try another map file.");
                    continue;
                }
                System.out.println("Map '" + l_filename + "' is ready.");
                System.out.println("Edit the map by using the 'editcontinent', 'editcountry' and 'editneighbor' commands.");
            }

            // Ensure that the map is loaded into memory before proceeding with other
            if(null == d_editMap) {
                System.out.println("Please load a map before trying to continue with other commands.");
                continue;
            }

            // Logic for each command

            if(MapEditorCommand.EDIT_CONTINENT.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentBonusValue = Integer.parseInt(l_splitCommand[l_i++]);
                            d_editMap.addContinent(l_continentId, l_continentBonusValue);
                            System.out.println("Continent " + l_continentId + " with bonus of " + l_continentBonusValue + " added.");
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_editMap.removeContinent(l_continentId);
                            System.out.println("Continent " + l_continentId + " removed.");
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
                            d_editMap.addCountry(l_countryId, l_continentId);
                            System.out.println("Country " + l_countryId + " added to continent " + l_continentId + ".");
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_editMap.removeCountry(l_countryId);
                            System.out.println("Country " + l_countryId + " removed.");
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
                            d_editMap.addNeighbour(l_countryId, l_neighbourCountryId);
                            System.out.println("Country " + l_neighbourCountryId + " added as neighbour to country " + l_countryId + ".");
                        }
                        case MapEditorCommand.REMOVE -> {
                            int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(l_splitCommand[l_i++]);
                            d_editMap.removeNeighbour(l_countryId, l_neighbourCountryId);
                            System.out.println("Country " + l_neighbourCountryId + " removed as neighbour to country " + l_countryId + ".");
                        }
                    }
                }
            }

            if(MapEditorCommand.VALIDATE_MAP.getCommand().equals(l_commandType)) {
                boolean isMapValid = MapTools.validateMap(d_editMap);
                if(isMapValid) {
                    System.out.println("The map is valid!");
                }
                else {
                    System.out.println("The map is not valid!");
                }
            }

            if(MapEditorCommand.SAVE_MAP.getCommand().equals(l_commandType)) {
                String l_fileNameForSave = l_splitCommand[1];
                boolean isSaveSuccessful = MapTools.saveMapFile(d_editMap, l_fileNameForSave);
                if(isSaveSuccessful) {
                    System.out.println("File saved successfully: \"" + l_fileNameForSave + "\".");
                } else {
                    System.out.println("File save for file \"" + l_fileNameForSave + "\" failed!");
                }
            }

            if(MapEditorCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                d_editMap.showMap();
            }

        }

    }
}
