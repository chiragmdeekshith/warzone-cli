package com.fsociety.warzone.models.map;

import com.fsociety.warzone.controllers.GamePlayController;
import com.fsociety.warzone.models.Player;
import com.fsociety.warzone.models.map.AbstractMap;
import com.fsociety.warzone.models.Continent;
import com.fsociety.warzone.models.Country;
import com.fsociety.warzone.utils.MapTools;

import java.util.*;

public class PlayMap extends AbstractMap {
    private static ArrayList<Player> d_players;
    private static HashMap<Integer, Player> d_playerList;
    private static PlayMap d_playMap;
    // continent id -> game state of continent
    private Map<Integer, Continent> d_continents;

    // country id -> game state of country
    private Map<Integer, Country> d_countries;


    /**
     * Parameterized constructor getting values of the map from the EditMap object
     */
    public PlayMap(EditMap l_loadMap) {
        this.d_countriesInContinent = l_loadMap.d_countriesInContinent;
        this.d_continentBonuses = l_loadMap.d_continentBonuses;
        this.d_neighbours = l_loadMap.d_neighbours;
        this.d_fileName = l_loadMap.d_fileName;
        this.d_countries = new HashMap<>();
        this.d_continents = new HashMap<>();
    }

    /**
     * Initialize the game state for all the countries in the map
     *
     */
    public void initGameMapElements() {
        d_neighbours.keySet().forEach(l_countryId -> {
            d_countries.put(l_countryId, new Country(l_countryId));
        });
        d_countriesInContinent
                .keySet()
                .forEach(l_continentId -> d_continents.put(
                        l_continentId,
                        new Continent(
                                l_continentId,
                                d_countriesInContinent.get(l_continentId),
                                d_countries,
                                d_continentBonuses.get(l_continentId)
                        )
                ));
    }

    /**
     * This method creates a map between Player objects and their playerIDs to be stored in the GameEngineController.
     */
    public static void initPlayerList() {
        d_playerList = new HashMap<>();
        for (Player l_player : d_playMap.getPlayers()) {
            d_playerList.put(l_player.getId(), l_player);
        }
    }

    /**
     * get the current status of a country instance
     *
     * @param p_countryId the country ID
     * @return the current game state for a country
     */
    public Country getCountryState(int p_countryId) {
        return d_countries.get(p_countryId);
    }

    // Getters and Setters

    /**
     * Getter for HashMap object with countryId->countries
     * @return d_countries
     */
    public Map<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Getter for HashMap object with continentId->continents
     * @return d_continents
     */
    public Map<Integer, Continent> getContinents() {
        return d_continents;
    }

    // Getters and Setters
    public ArrayList<Player> getPlayers() {
        return d_players;
    }
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
    }

    public static HashMap<Integer, Player> getPlayerList() {
        return d_playerList;
    }

    public static PlayMap getPlayMap() {
        return d_playMap;
    }

    public static void setPlayMap(PlayMap p_playMap) {
        d_playMap = p_playMap;
    }
}
