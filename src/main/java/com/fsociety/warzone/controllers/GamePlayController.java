package com.fsociety.warzone.controllers;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.models.Player;
import com.fsociety.warzone.models.map.AbstractMap;
import com.fsociety.warzone.models.map.EditMap;
import com.fsociety.warzone.models.Continent;
import com.fsociety.warzone.models.Country;
import com.fsociety.warzone.models.map.PlayMap;
import com.fsociety.warzone.utils.MapTools;
import com.fsociety.warzone.utils.command.CommandValidator;
import com.fsociety.warzone.utils.command.constant.Phase;
import com.fsociety.warzone.utils.command.constant.StartupCommand;

import java.util.*;

public class GamePlayController {
    // continent id -> game state of continent
    private Map<Integer, Continent> d_continents;

    // country id -> game state of country
    private Map<Integer, Country> d_countries;

    private static PlayMap d_playMap;
    /**
     * Parameterized constructor getting values of the map from the EditMap object
     */
    public GamePlayController() {
        //this.d_countriesInContinent = l_loadMap.d_countriesInContinent;
        //this.d_continentBonuses = l_loadMap.d_continentBonuses;
        //this.d_neighbours = l_loadMap.d_neighbours;
        //this.d_fileName = l_loadMap.d_fileName;
        this.d_countries = new HashMap<>();
        this.d_continents = new HashMap<>();
    }

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

            if(!CommandValidator.isValidCommand(l_inputRawCommand, Phase.START_UP)) {
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
                System.out.println("Loaded map \"" + l_fileName + "\"");
                if(!editPlayers()) {
                    return false;
                }
                break;
            }
            if(StartupCommand.SHOW_MAP.getCommand().equals((l_commandType))) {
                if(null == PlayMap.getPlayMap()) {
                    System.out.println("Please load a map before trying to display it.");
                } else {
                    showMap();
                }
            }
        }
        assignCountries();
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
            if(CommandValidator.isValidCommand(l_inputRawCommand, Phase.START_UP)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                if(StartupCommand.ASSIGN_COUNTRIES.getCommand().equals(l_commandType)) {
                    if(!l_players.isEmpty()) {
                        if (l_players.size() <= PlayMap.getPlayMap().getNeighbours().keySet().size()) {
                            PlayMap.setPlayers(new ArrayList<>(l_players.values()));
                            PlayMap.initPlayerList();
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
                    showMap();
                }

            } else {
                System.out.println("Invalid command. Please use commands 'gameplayer', 'assigncountries' or 'back'.");
            }
        }
    }

    /**
     * update the game state for a country after a turn
     *
     * @param p_countryId the id of the country to update
     * @param p_playerId  the id of the player to update
     * @param p_armies    the number of armies to update
     */
    public static void updateGameState(int p_countryId, int p_playerId, int p_armies){
        d_playMap.getCountries().get(p_countryId).setPlayerId(p_playerId);
        d_playMap.getCountries().get(p_countryId).setArmies(p_armies);
        d_playMap.getCountries().get(p_countryId).setPlayer(PlayMap.getPlayerList().get(p_playerId));
    }

    /**
     * This method calls the loadAndValidateMap() method from the MapTools class to load a file into memory for
     * gameplay.
     *
     * @param p_fileName the name of the .map file entered by the user
     * @return returns false if the map fails to load properly, and true otherwise
     */
    public static boolean loadMap(String p_fileName) {
        PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(p_fileName);
        if(null == l_playMap) {
            return false;
        }
        l_playMap.setPlayMap(l_playMap);
        d_playMap = l_playMap;
        return true;
    }

    /**
     * This method randomly assigns all countries in the map to the players in round-robin fashion. This method then
     * updates the map and player's country lists in order to reflect the assignment.
     */
    public static void assignCountries() {

        PlayMap l_playMap = PlayMap.getPlayMap();
        List<Player> l_players = l_playMap.getPlayers();

        List<Integer> l_countryIds = new ArrayList<Integer>(l_playMap.getNeighbours().keySet());

        Random l_random = new Random();

        int l_counter = 0;
        while (!l_countryIds.isEmpty()) {
            Player l_player = l_players.get(l_counter%l_players.size()); // Cycles through the players in round-robin
            int randomIndex = l_random.nextInt(l_countryIds.size());
            int l_countryId = l_countryIds.remove(randomIndex);
            updateGameState(l_countryId, l_player.getId(), 0);
            l_player.addCountry(l_playMap.getCountryState(l_countryId));
            l_playMap.getCountryState(l_countryId).setPlayer(l_player);
            l_counter++;
        }
    }

    /**
     * Display the current Map for the gameplay details. Show owner and armies
     */
    public static void showMap() {
        System.out.println("\nMap: " + d_playMap.getFileName());
        System.out.println("--------------------");
        System.out.println("Continents");
        System.out.println("Continent: Bonus - [Countries]");
        for (Continent l_continent : d_playMap.getContinents().values()) {
            System.out.println(l_continent);
        }
        System.out.println("--------------------");
        System.out.println("Borders");
        System.out.println("Country: Owned by, \tArmies - [Neighbours]");

        for (Map.Entry<Integer, Country> entry : d_playMap.getCountries().entrySet()) {
            int l_countryId = entry.getKey();
            Country l_country = entry.getValue();
            if(null != l_country.getPlayer()) {
                System.out.print(l_countryId + ": " + l_country.getPlayer().getName() + ",\t");
                System.out.print(l_country.getArmies() + " - ");
                System.out.println(d_playMap.getNeighbours().get(l_countryId));
            }
        }
        System.out.println("--------------------\n");
    }
}
