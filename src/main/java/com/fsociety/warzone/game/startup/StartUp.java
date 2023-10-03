package com.fsociety.warzone.game.startup;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.*;

public class StartUp {

    /**
     * This method serves as the set-up phase for a new game. The user is prompted in-order to load a map and then edit
     * a list of players. If any commands are invalid, the user is prompted again.
     *
     * @return returns false if a user types in the 'back' command in order to return to the main menu
     */
    public static boolean startUp() {

        System.out.println("New game selected. Please start by loading a map.");
        String l_inputRawCommand;
        System.out.println("Enter command. (Type 'back' at any time to return to the main menu.)");

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
                String l_fileName = l_splitCommand[1];
                if(!loadMap(l_fileName)) {
                    System.out.println("Failed to load the map! Please try another map file.");
                    continue;
                }
                System.out.println("Loaded map  \"" + l_fileName + "\"");
                if(!editPlayers()) {
                    return false;
                }
                break;
            }
            if(StartupCommand.SHOW_MAP.getCommand().equals((l_commandType))) {
                if(null == GameEngine.getWZMap()) {
                    System.out.println("Please load a map before trying to display it.");
                } else {
                    GameEngine.getWZMap().showMapForGame();
                }
            }
        }
        assignCountries();
        return true;
    }

    /**
     * This method calls the loadAndValidateMap() method from the MapTools class to load a file into memory for
     * gameplay.
     *
     * @param p_fileName the name of the .map file entered by the user
     * @return returns false if the map fails to load properly, and true otherwise
     */
    public static boolean loadMap(String p_fileName) {
        WZMap l_wzMap = MapTools.loadAndValidateMap(p_fileName);
        if(null == l_wzMap) {
            return false;
        }
        GameEngine.setWZMap(l_wzMap);
        return true;
    }

    /**
     * This method allows a user to create and modify a list of players. In order to finish editing the list of
     * players, the user must use the 'assigncountries' command to begin the game. The list of players must include
     * at least one player to begin the game, but may not have more players than there are countries on the map.
     *
     * @return returns false if a user types in the 'back' command in order to return to the main menu
     */
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
                        if (l_players.size() <= GameEngine.getWZMap().getAdjacencyMap().keySet().size()) {
                            GameEngine.setPlayers(new ArrayList<>(l_players.values()));
                            GameEngine.initPlayerList();
                            return true;
                        } else {
                            System.out.println("Too many players for this map. Please remove players to continue.");
                        }
                    } else {
                        System.out.println("Please add at least one player to the game to continue.");
                    }
                }

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

                if(StartupCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                    GameEngine.getWZMap().showMapForGame();
                }

            } else {
                System.out.println("Invalid command. Please use commands 'gameplayer', 'assigncountries' or 'back'");
            }
        }
    }

    /**
     * This method randomly assigns all countries in the map to the players in round-robin fashion. This method then
     * updates the map and player's country lists in order to reflect the assignment.
     */
    public static void assignCountries() {

        WZMap wzMap = GameEngine.getWZMap();
        ArrayList<Player> l_players = GameEngine.getPlayers();

        ArrayList<Integer> l_countryIds = new ArrayList<>(wzMap.getAdjacencyMap().keySet());

        Random random = new Random();

        int l_counter = 0;
        while (!l_countryIds.isEmpty()) {
            Player l_player = l_players.get(l_counter%l_players.size()); // Cycles through the players in round-robin
            int randomIndex = random.nextInt(l_countryIds.size());
            int l_countryId = l_countryIds.remove(randomIndex);
            wzMap.updateGameState(l_countryId, l_player.getId(), 0);
            l_player.addCountry(wzMap.getGameState(l_countryId));
            wzMap.getGameState(l_countryId).setPlayer(l_player);
            l_counter++;
        }
    }
}
