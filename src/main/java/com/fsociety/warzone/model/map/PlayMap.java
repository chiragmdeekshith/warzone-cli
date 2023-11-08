package com.fsociety.warzone.model.map;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;

import java.util.*;

public class PlayMap extends AbstractMap {
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
    public void updateCountry(int p_countryId, int p_playerId, int p_armies) {
        d_countries.get(p_countryId).setPlayerId(p_playerId);
        d_countries.get(p_countryId).setArmies(p_armies);
        d_countries.get(p_countryId).setPlayer(GameplayController.getPlayerNameMap().get(GameplayController.getPlayerNameFromId(p_playerId)));
    }

    public void updateCountry(int p_countryId, int p_armies) {
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
}
