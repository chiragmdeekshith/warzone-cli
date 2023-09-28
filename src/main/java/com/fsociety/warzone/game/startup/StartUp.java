package com.fsociety.warzone.game.startup;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.play.PlayMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartUp {

    public static boolean start_up() {

        System.out.println("New game selected. Please start by loading a map.");
        String l_inputRawCommand;

        while(true) {

            System.out.println("Enter command. (Type 'back' to go to the previous menu.)");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP)){
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];
                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }
                if(StartupCommand.LOAD_MAP.getCommand().equals(l_commandType)) {
                    String l_filename = l_splitCommand[1];
                    loadMap(l_filename);
                    System.out.println("Loaded map - " + l_filename);
                    System.out.println("Add/Remove players");
                    if(!editPlayers()) {
                        return false;
                    }
                    break;
                }
            } else {
                System.out.println("Invalid command. Please start by loading a map. Try 'loadmap [filename].map'");
            }
        }
        if (!assignCountries()) {
            return false;
        }
        return true;
    }

    /**
     * @TODO Implement map loading using FileIO
     */
    public static void loadMap(String p_fileName) {

        PlayMap l_map = new PlayMap();

        // Call method to load map into map object

        GameEngine.setMap(l_map);

    }


    public static boolean editPlayers() {

        Map<String, Player> l_players = new HashMap<>();

        System.out.println("Please create list of players. Try '-add [name]' to add a player.");
        String l_inputRawCommand;

        while(true) {

            System.out.println("Enter command. (Type 'back' to go to the previous menu.)");
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

    /**
     * @TODO Assign countries randomly to each player based on map
     */
    public static boolean assignCountries() {
        PlayMap l_map = GameEngine.getMap();
        ArrayList<Player> l_players = GameEngine.getPlayers();

        // If map has no countries
        if (true) {
            System.out.println("There was a problem with your map file. Returning to main menu.");
            return false;
        }

        // Assign countries randomly

        return true;
    }

}
