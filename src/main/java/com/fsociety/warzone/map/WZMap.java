package com.fsociety.warzone.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;

/**
 * This class implements the connected graph structure of the map. This map is used both for editing and in gameplay.
 */
public class WZMap {

    // country id -> Set of ids of neighbours
    private final Map<Integer, Set<Integer>> d_neighbours;

    // continent id -> Set of ids of countries
    private final Map<Integer, Set<Integer>> d_countriesInContinent;

    // continent id -> bonus for control of continent
    private final Map<Integer, Integer> d_continentBonuses;

    // country id -> game state of country
    private final Map<Integer, Country> d_countries;

    // continent id -> game state of continent
    private final Map<Integer, Continent> d_continents;

    // country id -> game state of continent
    private final Map<Integer, Continent> d_continentOfCountry;

    // file name of the map
    private String d_mapFileName;

    /**
     * Default constructor initializes the WZMap Object with empty lists and maps.
     */
    public WZMap() {
        this.d_neighbours = new LinkedHashMap<>();
        this.d_continentBonuses = new HashMap<>();
        this.d_countriesInContinent = new HashMap<>();
        this.d_countries = new HashMap<>();
        this.d_continents = new HashMap<>();
        this.d_continentOfCountry = new HashMap<>();
    }

    /**
     * Adds a new continent to the map.
     * 
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    public void addContinent(
            final Integer p_continentId,
            final Integer p_continentBonus) {
        if (d_countriesInContinent.get(p_continentId) != null) {
            System.out.println("Continent already exists.");
            return;
        }
        d_countriesInContinent.put(p_continentId, new LinkedHashSet<>());
        d_continentBonuses.put(p_continentId, p_continentBonus);
    }

    /**
     * Adds a country to the map.
     * 
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    public void addCountry(
            final Integer p_countryId,
            final Integer p_continentId) {
        if (d_countriesInContinent.get(p_continentId) == null) {
            System.out.println("Continent does not exist in the Map.");
        } else if (d_neighbours.get(p_countryId) != null) {
            System.out.println("Country already exists.");
        } else {
            d_neighbours.put(p_countryId, new LinkedHashSet<>());
            d_countriesInContinent.get(p_continentId).add(p_countryId);
        }
    }

    /**
     * Adds a neighbour to a country
     * 
     * @param p_countryId          the id of the country to add the neighbour to
     * @param p_neighbourCountryId the id of the neighbour to add
     */
    public void addNeighbour(
            final int p_countryId,
            final int p_neighbourCountryId) {
        if (p_countryId == p_neighbourCountryId) {
            System.out.println("Country cannot be neighbour of itself.");
        } else if (d_neighbours.get(p_countryId) == null) {
            System.out.println("Country does not exist or is invalid.");
        } else if (d_neighbours.get(p_neighbourCountryId) == null) {
            System.out.println("Neighbour Country does not exist or is invalid.");
        } else {
            d_neighbours.get(p_countryId).add(p_neighbourCountryId);
            d_neighbours.get(p_neighbourCountryId).add(p_countryId);
        }
    }

    /**
     * This function stores the file name of the map
     * @param p_fileName - the name of the file
     */
    public void setFileName(String p_fileName) {
        this.d_mapFileName = p_fileName;
    }

    /**
     * Removes a neighbour from a country
     * 
     * @param p_countryId          the id of the country to remove the neighbour
     * @param p_neighbourCountryId the id of the neighbour to remove
     */
    public void removeNeighbour(
            final int p_countryId,
            final int p_neighbourCountryId) {
        if (p_countryId == p_neighbourCountryId) {
            System.out.println("Country cannot be neighbour of itself.");
        } else if (d_neighbours.get(p_countryId) == null) {
            System.out.println("Country does not exist or is invalid.");
        } else if (d_neighbours.get(p_neighbourCountryId) == null) {
            System.out.println("Neighbour Country does not exist or is invalid.");
        } else {
            d_neighbours.get(p_countryId).remove(p_neighbourCountryId);
            d_neighbours.get(p_neighbourCountryId).remove(p_countryId);
        }
    }

    /**
     * Removes a continent from the map
     * 
     * @param p_continentId the continent to remove
     */
    public void removeContinent(final Integer p_continentId) {
        if (d_countriesInContinent.get(p_continentId) == null) {
            System.out.println("Continent does not exist.");
        } else {
            d_countriesInContinent.get(p_continentId).forEach(this::removeAdjacency);
            d_continentBonuses.remove(p_continentId);
            d_countriesInContinent.remove(p_continentId);
        }
    }

    /**
     * Removes the adjacency to all neighbouring countries for the given country.
     * Removing current country from neighbours so that it doesn't point to a
     * country that doesn't exist. It can mainly happen when the current country is
     * adjacent to a country in a different continent.
     *
     * @param p_countryId the country to remove the adjacency
     */
    public void removeAdjacency(final int p_countryId) {
        final Set<Integer> l_neighbours = d_neighbours.get(p_countryId);
        l_neighbours.forEach(neighbourId -> d_neighbours.get(neighbourId).remove(p_countryId));
        d_neighbours.remove(p_countryId);
    }

    /**
     * Removes a country from the map.
     * 
     * @param p_countryId   the country to remove
     */
    public void removeCountry(final int p_countryId) {
        if (d_neighbours.get(p_countryId) == null) {
            System.out.println("Country does not exist.");
        } else {
            removeAdjacency(p_countryId);
            int l_continentId = getContinentIdForCountry(p_countryId);
            d_countriesInContinent.get(l_continentId).remove(p_countryId);
        }
    }

    /**
     * get adjacencies of all the countries in the map
     * 
     * @return adjacencies of all the countries in the map
     */
    public Map<Integer, Set<Integer>> getAdjacencyMap() {
        return new LinkedHashMap<>(d_neighbours);
    }

    /**
     * get continent -> country mappings
     * 
     * @return continent -> country mappings
     */
    public Map<Integer, Set<Integer>> getContinentCountriesMap() {
        return new HashMap<>(d_countriesInContinent);
    }

    /**
     * Get the instance of continent game state map
     *
     * @return continent game state map
     */
    public Map<Integer, Continent> getContinents() { return this.d_continents; }

    /**
     * get continent -> bonus mappings
     * 
     * @return continent -> bonus mappings
     */
    public Map<Integer, Integer> getContinentBonusMap() {
        return new HashMap<>(d_continentBonuses);
    }

    /**
     * initialize the game state for all the countries in the map
     * 
     */
    public void initGameStates() {
        d_neighbours.keySet().forEach(l_countryId -> {
            d_countries.put(l_countryId, new Country(l_countryId));
        });
        d_countriesInContinent.keySet().forEach(l_continentId -> {
            d_continents.put(l_continentId, new Continent(l_continentId, d_countriesInContinent.get(l_continentId), d_countries, d_continents.get(l_continentId).getArmiesBonus()));
            d_countriesInContinent.get(l_continentId).forEach(countryId -> d_continentOfCountry.put(countryId, d_continents.get(l_continentId)));
        });
    }

    /**
     * update the game state for a country after a turn
     * 
     * @param p_countryId the id of the country to update
     * @param p_playerId  the id of the player to update
     * @param p_armies    the number of armies to update
     */
    public void updateGameState(
            final int p_countryId,
            final int p_playerId,
            final int p_armies) {
        d_countries.get(p_countryId).setPlayerId(p_playerId);
        d_countries.get(p_countryId).setArmies(p_armies);
        //d_countries.get(p_countryId).setPlayer(GameEngine.getPlayerList().get(p_playerId));
    }

    /**
     * get the current game state for a country
     *
     * @param p_countryId the country ID
     * @return the current game state for a country
     */
    public Country getGameState(final int p_countryId) {
        return d_countries.get(p_countryId);
    }

    /**
     * Display the current Map for the map editor. Show continents, countries and neighbours
     */
    public void showMapForEditor() {
        System.out.println("\nMap: " + d_mapFileName);
        System.out.println("--------------------");
        System.out.println("Continents and their bonuses");
        for (Map.Entry<Integer, Integer> entry : this.d_continentBonuses.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("--------------------");
        System.out.println("Continents and their Countries");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_countriesInContinent.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("--------------------");
        System.out.println("Borders");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_neighbours.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("--------------------\n");
    }

    /**
     * Display the current Map for the gameplay details. Show owner and armies
     */
    public void showMapForGame() {
        System.out.println("\nMap: " + d_mapFileName);
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
     * get the continent ID for a country
     *
     * @param p_countryId the country ID
     * @return the continent ID for a country
     */
    public Integer getContinentIdForCountry(Integer p_countryId) {
        List<Integer> l_continentIds = new ArrayList<>(d_countriesInContinent.keySet());
        for(Integer l_id : l_continentIds) {
            if(d_countriesInContinent.get(l_id).contains(p_countryId)) {
                return l_id;
            }
        }
        // Shouldn't reach this return
        return -1;
    }
}
