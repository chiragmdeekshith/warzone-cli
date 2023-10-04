package com.fsociety.warzone.map;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.util.command.CommandValidator;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;

public class MapEditor {
    private static WZMap d_wzMap;
    public static void editMap() {
        System.out.println("Map Editor selected. Please start by loading a map. Type 'back' to go to the previous menu.");
        String l_inputRawCommand;

        while(true) {
            System.out.println("Enter command.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(!CommandValidator.isValidCommand(l_inputRawCommand, Phase.MAP_EDITOR)) {
                continue;
            }

            String[] l_splitCommand = l_inputRawCommand.split(" ");
            String l_commandType = l_splitCommand[0];

            if(MapEditorCommand.BACK.getCommand().equals(l_commandType)) {
                d_wzMap = null;
                break;
            }

            if(MapEditorCommand.EDIT_MAP.getCommand().equals(l_commandType)) {
                String l_filename = l_splitCommand[1];
                d_wzMap = MapTools.loadAndValidateMap(l_filename);
                if(null == d_wzMap) {
                    System.out.println("Failed to load the map from file! Please try another map file.");
                    continue;
                }
                System.out.println("Loaded map \"" + l_filename + "\"");
                System.out.println("Edit the map by using the 'editcontinent', 'editcountry' and 'editneighbor' commands.");
            }

            if(null == d_wzMap) {
                System.out.println("Please load a map before trying to continue with other commands.");
                continue;
            }

            if(MapEditorCommand.EDIT_CONTINENT.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentBonusValue = Integer.parseInt(l_splitCommand[l_i++]);
                            d_wzMap.addContinent(l_continentId, l_continentBonusValue);
                            System.out.println("Continent " + l_continentId + " with bonus of " + l_continentBonusValue + " added.");
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_wzMap.removeContinent(l_continentId);
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
                            d_wzMap.addCountry(l_countryId, l_continentId);
                            System.out.println("Country " + l_countryId + " added to continent " + l_continentId + ".");
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_wzMap.removeCountry(l_countryId);
                            System.out.println("Country " + l_countryId + " removed.");
                        }
                    }
                }
            }

            if(MapEditorCommand.EDIT_NEIGHBOUR.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                    int l_neighbourCountryId = Integer.parseInt(l_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            d_wzMap.addNeighbour(l_countryId, l_neighbourCountryId);
                            System.out.println("Country " + l_neighbourCountryId + " added as neighbour to country " + l_countryId + ".");
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_wzMap.removeNeighbour(l_countryId, l_neighbourCountryId);
                            System.out.println("Country " + l_neighbourCountryId + " removed as neighbour to country " + l_countryId + ".");
                        }
                    }
                }
            }

            if(MapEditorCommand.VALIDATE_MAP.getCommand().equals(l_commandType)) {
                boolean isMapValid = MapTools.validateMap(d_wzMap);

                if( isMapValid) {
                    System.out.println("The map is valid!");
                }
                else {
                    System.out.println("The map is not valid!");
                }
            }

            if(MapEditorCommand.SAVE_MAP.getCommand().equals(l_commandType)) {
                String l_fileNameForSave = l_splitCommand[1];
                boolean isSaveSuccessful = MapTools.saveMapFile(d_wzMap, l_fileNameForSave);
                if(isSaveSuccessful) {
                    System.out.println("File saved successfully: \"" + l_fileNameForSave + "\".");
                } else {
                    System.out.println("File save for file \"" + l_fileNameForSave + "\" failed!");
                }
            }

            if(MapEditorCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                d_wzMap.showMapForEditor();
            }

        }

    }
}
