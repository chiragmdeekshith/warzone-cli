package com.fsociety.warzone.controllers;

import com.fsociety.warzone.models.Player;
import com.fsociety.warzone.models.Continent;
import com.fsociety.warzone.models.Country;
import com.fsociety.warzone.models.map.PlayMap;
import com.fsociety.warzone.utils.MapTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GamePlayController {
    // continent id -> game state of continent
    private Map<Integer, Continent> d_continents;

    // country id -> game state of country
    private Map<Integer, Country> d_countries;

    private static PlayMap d_playMap;



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
