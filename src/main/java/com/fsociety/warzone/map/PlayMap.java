package com.fsociety.warzone.map;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;

import java.util.HashMap;
import java.util.Map;

public class PlayMap extends AbstractMap {
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
        d_countries.get(p_countryId).setPlayer(GameEngine.getPlayerList().get(p_playerId));
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
        System.out.println("--------------------");
        System.out.println("Borders");
        System.out.println("Country: Owned by, \tArmies - [Neighbours]");

        for (Map.Entry<Integer, Country> entry : this.d_countries.entrySet()) {
            int l_countryId = entry.getKey();
            Country l_country = entry.getValue();
            if(null != l_country.getPlayer()) {
                System.out.print(l_countryId + ": " + l_country.getPlayer().getName() + ",\t");
                System.out.print(l_country.getArmies() + " - ");
                System.out.println(d_neighbours.get(l_countryId));
            }
        }
        System.out.println("--------------------\n");
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
