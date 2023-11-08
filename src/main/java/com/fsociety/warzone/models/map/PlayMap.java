package com.fsociety.warzone.models.map;

import com.fsociety.warzone.controllers.GameEngineController;
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
     * Default constructor
     */
    public PlayMap(){
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
    }

    /**
     * Initialize map elements for all the countries and continents in the map.
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
     * Update the game state for a country after a turn.
     *
     * @param p_countryId the id of the country to update
     * @param p_playerId  the id of the player to update
     * @param p_armies    the number of armies to update
     */
    public void updateGameState(int p_countryId, int p_playerId, int p_armies) {
        d_countries.get(p_countryId).setPlayerId(p_playerId);
        d_countries.get(p_countryId).setArmies(p_armies);
        d_countries.get(p_countryId).setPlayer(GameEngineController.getPlayerNameMap().get(GameEngineController.getPlayerNameFromId(p_playerId)));
    }

    public void updateGameState(int p_countryId, int p_armies) {
        d_countries.get(p_countryId).setArmies(p_armies);
    }

    /**
     * Display the current Map for the gameplay details. Show owner and armies.
     */
    @Override
    public void showMap() {
        System.out.println("\nMap: " + d_fileName);
        System.out.println("--------------------");
        System.out.println("Continents");
        System.out.println("Continent: Bonus - [Countries]");
        for (Continent l_continent : this.d_continents.values()) {
            System.out.println(l_continent);
        }
    }

    /**
     * This method verifies whether the given country neighbours a country owned by the given player.
     * @param p_countryId the country ID
     * @param p_playerId the player ID
     * @return True if the country neighbours a country owned by the given player, false otherwise
     */
    public boolean isNeighbourOf(int p_countryId, int p_playerId) {
        for (Integer l_neighbouringCountry : d_neighbours.get(p_countryId))
            if (d_countries.get(l_neighbouringCountry).getPlayerId() == p_playerId) {
                return true;
            }
        return false;
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
