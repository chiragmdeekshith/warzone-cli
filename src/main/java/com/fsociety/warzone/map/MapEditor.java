package com.fsociety.warzone.map;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;

public class MapEditor {
    private static WZMap d_wzMap;
    public static void editMap() {
        System.out.println("Map Editor selected. Please start by loading a map.");
        String l_inputRawCommand;

        while(true) {
            System.out.println("Enter command. (Type 'back' to go to the previous menu.)");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(!CommandHandler.isValidCommand(l_inputRawCommand, Phase.MAP_EDITOR)) {
                System.out.println("Invalid command. Please start by loading a map.");
                continue;
            }

            String[] l_splitCommand = l_inputRawCommand.split(" ");
            String l_commandType = l_splitCommand[0];

            if(MapEditorCommand.BACK.getCommand().equals(l_commandType)) {
                break;
            }

            if(MapEditorCommand.EDIT_MAP.getCommand().equals(l_commandType)) {
                String l_filename = l_splitCommand[1];
                d_wzMap = MapTools.loadAndValidateMap(l_filename);
                if(null == d_wzMap) {
                    System.out.println("Failed to load the map from file! Please try another map file.");
                    continue;
                }
                System.out.println("Loaded map - " + l_filename);
                System.out.println("Edit continents / countries");
            }

            if(null == d_wzMap) {
                System.out.println("Please load a map before trying to continue with other commands.");
                continue;
            }

            if(MapEditorCommand.EDIT_CONTINENT.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                            int l_continentBonusValue = Integer.parseInt(l_splitCommand[l_i++]);
                            d_wzMap.addContinent(l_continentId, l_continentBonusValue);
                        }
                        case MapEditorCommand.REMOVE -> {
                            int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                            d_wzMap.removeContinent(l_continentId);
                        }
                    }
                }
            }

            if(MapEditorCommand.EDIT_COUNTRY.getCommand().equals(l_commandType)) {
                int l_i = 1;
                while(l_i < l_splitCommand.length) {
                    String l_operation = l_splitCommand[l_i++];
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                            int l_continentId = Integer.parseInt(l_splitCommand[l_i++]);
                            d_wzMap.addCountry(l_countryId, l_continentId);
                        }
                        case MapEditorCommand.REMOVE -> {
                            int l_countryId = Integer.parseInt(l_splitCommand[l_i++]);
                            d_wzMap.removeCountry(l_countryId);
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
                        }
                        case MapEditorCommand.REMOVE -> {
                            d_wzMap.removeNeighbour(l_countryId, l_neighbourCountryId);
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
                    System.out.println("The map is not valid");
                }
            }

            if(MapEditorCommand.SAVE_MAP.getCommand().equals(l_commandType)) {
                boolean isSaveSuccessful = MapTools.saveMapFile(d_wzMap);
            }

            if(MapEditorCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                d_wzMap.showMapForEditor();
            }

        }

    }
}
