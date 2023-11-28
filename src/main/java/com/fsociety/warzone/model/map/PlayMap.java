package com.fsociety.warzone.model.map;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.view.Console;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used by the GameplayController for gameplay.
 */
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
     * This method is used to update a country with initial ownership.
     *
     * @param p_countryId the id of the country to update
     * @param p_playerId  the id of the player to update
     * @param p_armies    the number of armies to update
     */
    public void updateCountry(int p_countryId, int p_playerId, int p_armies) {
        d_countries.get(p_countryId).setPlayerId(p_playerId);
        d_countries.get(p_countryId).setPlayer(GameplayController.getPlayerFromId(p_playerId));
        d_countries.get(p_countryId).setArmies(p_armies);
    }

    /**
     * This method updates the number of troops on a country when ownership does not change.
     * @param p_countryId the country to be updated
     * @param p_armies the number of troops to be stationed on the country
     */
    public void updateCountry(int p_countryId, int p_armies) {
        d_countries.get(p_countryId).setArmies(p_armies);
    }

    /**
     * This method updates the country Object when it is conquered by another player.
     * @param p_countryId the ID of the country being modified
     * @param p_playerId the ID of the player that conquered the country
     * @param p_armies the number of troops to be stationed on the country
     */
    public void conquerCountry(int p_countryId, int p_playerId, int p_armies) {
        int l_previousOwner = GameplayController.getPlayMap().getCountries().get(p_countryId).getPlayerId();
        Player l_newOwner = GameplayController.getPlayerFromId(p_playerId);
        GameplayController.getPlayerFromId(l_previousOwner).removeCountry(p_countryId);
        l_newOwner.addCountry(GameplayController.getPlayMap().getCountries().get(p_countryId));
        d_countries.get(p_countryId).setPlayerId(p_playerId);
        d_countries.get(p_countryId).setPlayer(l_newOwner);
        d_countries.get(p_countryId).setArmies(p_armies);
    }

    /**
     * Display the current Map for the gameplay details. Show owner and armies.
     */
    @Override
    public void showMap() {
        StringBuilder l_playMapString = new StringBuilder();
        l_playMapString.append("\nMap: ").append(d_fileName).append("\n");
        l_playMapString.append("--------------------" + "\n");
        l_playMapString.append("Continents" + "\n");
        l_playMapString.append("Continent: Bonus - [Countries]\n");
        for (Continent l_continent : this.d_continents.values()) {
            l_playMapString.append(l_continent.toString()).append("\n");
        }
        l_playMapString.append("--------------------\n");
        l_playMapString.append("Borders\n");
        l_playMapString.append("Country: Owned by, \tArmies - [Neighbours]\n");

        for (Map.Entry<Integer, Country> entry : this.d_countries.entrySet()) {
            int l_countryId = entry.getKey();
            Country l_country = entry.getValue();
            if(null != l_country.getPlayer()) {
                l_playMapString
                        .append(l_countryId)
                        .append(": ")
                        .append(l_country.getPlayer().getName())
                        .append(",\t")
                        .append(l_country.getArmies())
                        .append(" - ")
                        .append(d_neighbours.get(l_countryId).toString())
                        .append("\n");
            }
        }
        l_playMapString.append("--------------------\n");
        Console.print(l_playMapString.toString());
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

    // Getters and Setters

    /**
     * Get the current status of a country instance.
     *
     * @param p_countryId the country ID
     * @return the current game state for a country
     */
    public Country getCountryState(int p_countryId) {
        return d_countries.get(p_countryId);
    }

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

    /**
     * This function checks whether a country has enemy neighbours
     * @param p_country the country to be checked for
     * @return true if country has enemy neighbours, false otherwise
     */
    public boolean doesCountryHaveEnemyNeighbours(Country p_country) {
        int l_playerId = p_country.getPlayerId();
        Set<Integer> l_neighbourCountryIdList = d_neighbours.get(p_country.getCountryId());
        for(Integer l_neighbourId : l_neighbourCountryIdList) {
            Country l_neighbourCountry = d_countries.get(l_neighbourId);
            if(l_neighbourCountry.getPlayerId() != l_playerId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of countries that can be attacked by a country
     * @param p_country the country for which enemies need to be found
     * @return a list of enemy countries
     */
    public List<Country> getListOfEnemyNeighbours(Country p_country) {
        int l_playerId = p_country.getPlayerId();
        Set<Integer> l_neighbourIds = d_neighbours.get(p_country.getCountryId());
        List<Country> l_enemyNeighbourCountries = new ArrayList<>();
        l_neighbourIds.forEach(l_neighbourId -> {
            Country l_enemyNeighbourCountry = d_countries.get(l_neighbourId);
            if(l_enemyNeighbourCountry.getPlayerId() != l_playerId) {
                l_enemyNeighbourCountries.add(l_enemyNeighbourCountry);
            }
        });
        return l_enemyNeighbourCountries;
    }

    /**
     * Retrieves a list of countries that can be used for moving troops into
     * @param p_country the country for which allied neighbours need to be found
     * @return a list of allied neighbouring countries
     */
    public List<Country> getListOfAlliedNeighbours(Country p_country) {
        int l_playerId = p_country.getPlayerId();
        Set<Integer> l_neighbourIds = d_neighbours.get(p_country.getCountryId());
        List<Country> l_allyNeighbourCountries = new ArrayList<>();
        l_neighbourIds.forEach(l_neighbourId -> {
            Country l_enemyNeighbourCountry = d_countries.get(l_neighbourId);
            if(l_enemyNeighbourCountry.getPlayerId() == l_playerId) {
                l_allyNeighbourCountries.add(l_enemyNeighbourCountry);
            }
        });
        return l_allyNeighbourCountries;
    }

    /**
     * Get the list all neighbour countries. Enemy or Ally.
     * @param p_country The country for which the neighbours are to be returned
     * @return the list of all neighbour countries
     */
    public List<Country> getListOfAllNeighbours(Country p_country) {
        Set<Integer> l_neighbourIds = d_neighbours.get(p_country.getCountryId());
        List<Country> l_neighbourCountries = new ArrayList<>();
        l_neighbourIds.forEach(l_neighbourId -> {
            l_neighbourCountries.add(d_countries.get(l_neighbourId));
        });
        return l_neighbourCountries;
    }
}
