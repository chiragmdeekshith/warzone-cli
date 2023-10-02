package com.fsociety.warzone.game.startup;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.FileIO;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.*;

public class StartUp {

    public static boolean start_up() {

        System.out.println("New game selected. Please start by loading a map.");
        String l_inputRawCommand;

        System.out.println("Enter command. (Type 'back' to go to the previous menu.)");

        while(true) {

            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(!CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP)) {
                System.out.println("Invalid command. Please start by loading a map. Try 'loadmap [filename].map'");
                continue;
            }

            String[] l_splitCommand = l_inputRawCommand.split(" ");
            String l_commandType = l_splitCommand[0];
            if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                return false;
            }
            if(StartupCommand.LOAD_MAP.getCommand().equals(l_commandType)) {
                String l_filename = l_inputRawCommand.replaceFirst(StartupCommand.LOAD_MAP.getCommand() + " ", "");
                if(!loadMap(l_filename)) {
                    System.out.println("Failed to load the map! Please try another map file.");
                    continue;
                }
                System.out.println("Loaded map - " + l_filename);
                System.out.println("Add/Remove players");
                if(!editPlayers()) {
                    return false;
                }
                break;
            }
        }
        if (!assignCountries()) {
            return false;
        }
        return true;
    }

    /**
     * @TODO Implement command parser to edit list of players
     */

    public static boolean loadMap(String p_fileName) {

        // Call method to load map data into map object
        WZMap l_wzMap = FileIO.loadAndValidateMap(p_fileName);
        if(null == l_wzMap) {
            System.out.println("Failed to load / validate map");
            return false;
        }
        GameEngine.setWZMap(l_wzMap);
        return true;
    }
    public static boolean editPlayers() {

        Map<String, Player> l_players = new HashMap<>();

        System.out.println("Please create list of players. Try '-add [name]' to add a player.");
        String l_inputRawCommand;

        while(true) {

            System.out.println("Enter command.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            // Ensure the command is valid for the current phase
            if(CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                if(StartupCommand.ASSIGN_COUNTRIES.getCommand().equals(l_commandType)) {
                    if(!l_players.isEmpty()) {
                        GameEngine.setPlayers(new ArrayList<>(l_players.values()));
                        GameEngine.initPlayerList();
                        return true;
                    }
                    System.out.println("Please add at least one player to the game to continue.");
                }

                // Add or remove players
                if(StartupCommand.GAME_PLAYER.getCommand().equals(l_commandType)) {

                    for (int i = 1; i < l_splitCommand.length; i+=2) {

                        String l_operation = l_splitCommand[i];
                        String l_playerName = l_splitCommand[i+1];

                        switch (l_operation) {
                            case StartupCommand.ADD -> {
                                if(l_players.containsKey(l_playerName)) {
                                    System.out.println("Player " + l_playerName + " already exists.");
                                } else {
                                    l_players.put(l_playerName, new Player(l_playerName));
                                    System.out.println("Player " + l_playerName + " added.");
                                }
                            }
                            case StartupCommand.REMOVE -> {
                                if(!l_players.containsKey(l_playerName)) {
                                    System.out.println("Player " + l_playerName + " does not exist.");
                                }
                                else {
                                    l_players.remove(l_playerName);
                                    System.out.println("Player " + l_playerName + " removed.");
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Invalid command. Please use commands 'gameplayer', 'assigncountries' or 'back'");
            }
        }
    }

    public static boolean assignCountries() {

        WZMap wzMap = GameEngine.getWZMap();
        ArrayList<Player> l_players = GameEngine.getPlayers();

        // Get list of all countries
        Set<Integer> l_countryIds = wzMap.getAdjacencyMap().keySet();

        Random random = new Random();

        // Assign countries randomly
        l_countryIds.forEach(l_countryId -> {
            int randomIndex = random.nextInt(l_players.size());
            Player l_player = l_players.get(randomIndex);
            wzMap.updateGameState(l_countryId, l_player.getId(), 0);
            l_player.addCountry(wzMap.getGameState(l_countryId));
            wzMap.getGameState(l_countryId).setPlayer(l_player);
        });

        return true;
    }

}
