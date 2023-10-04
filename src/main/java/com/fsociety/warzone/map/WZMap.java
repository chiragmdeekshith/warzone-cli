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
    private final Map<Integer, Set<Integer>> d_adjacencyMap;

    // continent id -> Set of ids of countries
    private final Map<Integer, Set<Integer>> d_continentCountriesMap;

    // continent id -> bonus for control of continent
    private final Map<Integer, Integer> d_continentBonusMap;

    // country id -> game state of country
    private final Map<Integer, Country> d_countryGameStateMap;

    // continent id -> game state of continent
    private final Map<Integer, Continent> d_continentGameStateMap;

    // country id -> game state of continent
    private final Map<Integer, Continent> d_countryContinentGameStateMap;

    // file name of the map

    private String d_mapFileName;

    /**
     * Default constructor initializes the WZMap Object with empty lists and maps.
     */
    public WZMap() {
        this.d_adjacencyMap = new LinkedHashMap<>();
        this.d_continentBonusMap = new HashMap<>();
        this.d_continentCountriesMap = new HashMap<>();
        this.d_countryGameStateMap = new HashMap<>();
        this.d_continentGameStateMap = new HashMap<>();
        this.d_countryContinentGameStateMap = new HashMap<>();
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
        if (d_continentCountriesMap.get(p_continentId) != null) {
            System.out.println("Continent already exists.");
            return;
        }
        d_continentCountriesMap.put(p_continentId, new LinkedHashSet<>());
        d_continentBonusMap.put(p_continentId, p_continentBonus);
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
        if (d_continentCountriesMap.get(p_continentId) == null) {
            System.out.println("Continent does not exist in the Map.");
        } else if (d_adjacencyMap.get(p_countryId) != null) {
            System.out.println("Country already exists.");
        } else {
            d_adjacencyMap.put(p_countryId, new LinkedHashSet<>());
            d_continentCountriesMap.get(p_continentId).add(p_countryId);
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
        } else if (d_adjacencyMap.get(p_countryId) == null) {
            System.out.println("Country does not exist or is invalid.");
        } else if (d_adjacencyMap.get(p_neighbourCountryId) == null) {
            System.out.println("Neighbour Country does not exist or is invalid.");
        } else {
            d_adjacencyMap.get(p_countryId).add(p_neighbourCountryId);
            d_adjacencyMap.get(p_neighbourCountryId).add(p_countryId);
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
        } else if (d_adjacencyMap.get(p_countryId) == null) {
            System.out.println("Country does not exist or is invalid.");
        } else if (d_adjacencyMap.get(p_neighbourCountryId) == null) {
            System.out.println("Neighbour Country does not exist or is invalid.");
        } else {
            d_adjacencyMap.get(p_countryId).remove(p_neighbourCountryId);
            d_adjacencyMap.get(p_neighbourCountryId).remove(p_countryId);
        }
    }

    /**
     * Removes a continent from the map
     * 
     * @param p_continentId the continent to remove
     */
    public void removeContinent(final Integer p_continentId) {
        if (d_continentCountriesMap.get(p_continentId) == null) {
            System.out.println("Continent does not exist.");
        } else {
            d_continentCountriesMap.get(p_continentId).forEach(this::removeAdjacency);
            d_continentBonusMap.remove(p_continentId);
            d_continentCountriesMap.remove(p_continentId);
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
        final Set<Integer> l_neighbours = d_adjacencyMap.get(p_countryId);
        l_neighbours.forEach(neighbourId -> d_adjacencyMap.get(neighbourId).remove(p_countryId));
        d_adjacencyMap.remove(p_countryId);
    }

    /**
     * Removes a country from the map.
     * 
     * @param p_countryId   the country to remove
     */
    public void removeCountry(final int p_countryId) {
        if (d_adjacencyMap.get(p_countryId) == null) {
            System.out.println("Country does not exist.");
        } else {
            removeAdjacency(p_countryId);
            int l_continentId = getContinentIdForCountry(p_countryId);
            d_continentCountriesMap.get(l_continentId).remove(p_countryId);
        }
    }

    /**
     * get adjacencies of all the countries in the map
     * 
     * @return adjacencies of all the countries in the map
     */
    public Map<Integer, Set<Integer>> getAdjacencyMap() {
        return new LinkedHashMap<>(d_adjacencyMap);
    }

    /**
     * get continent -> country mappings
     * 
     * @return continent -> country mappings
     */
    public Map<Integer, Set<Integer>> getContinentCountriesMap() {
        return new HashMap<>(d_continentCountriesMap);
    }

    /**
     * Get the instance of continent game state map
     *
     * @return continent game state map
     */
    public Map<Integer, Continent> getContinents() { return this.d_continentGameStateMap; }

    /**
     * get continent -> bonus mappings
     * 
     * @return continent -> bonus mappings
     */
    public Map<Integer, Integer> getContinentBonusMap() {
        return new HashMap<>(d_continentBonusMap);
    }

    /**
     * initialize the game state for all the countries in the map
     * 
     */
    public void initGameStates() {
        d_adjacencyMap.keySet().forEach(l_countryId -> {
            d_countryGameStateMap.put(l_countryId, new Country(l_countryId));
        });
        d_continentCountriesMap.keySet().forEach(continentId -> {
            d_continentGameStateMap.put(continentId, new Continent(d_continentCountriesMap.get(continentId), d_countryGameStateMap, d_continentBonusMap.get(continentId)));
            d_continentCountriesMap.get(continentId).forEach(countryId -> {
                d_countryContinentGameStateMap.put(countryId, d_continentGameStateMap.get(continentId));
            });
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
        final int l_currentPlayerId = d_countryGameStateMap.get(p_countryId).getPlayerId();
        if (l_currentPlayerId != p_playerId) {
            if (l_currentPlayerId == -1) {
                d_countryContinentGameStateMap.get(p_countryId).initCountryCount(p_playerId);
            } else {
                d_countryContinentGameStateMap.get(p_countryId).updateCountryCount(l_currentPlayerId, p_playerId);
            }
        }
        d_countryGameStateMap.get(p_countryId).setPlayerId(p_playerId);
        d_countryGameStateMap.get(p_countryId).setArmies(p_armies);
        d_countryGameStateMap.get(p_countryId).setPlayer(GameEngine.getPlayerList().get(p_playerId));
    }

    /**
     * get the current game state for a country
     *
     * @param p_countryId the country ID
     * @return the current game state for a country
     */
    public Country getGameState(final int p_countryId) {
        return d_countryGameStateMap.get(p_countryId);
    }

    /**
     * Display the current Map for the map editor. Show continents, countries and neighbours
     */
    public void showMapForEditor() {
        System.out.println("\nMap: " + d_mapFileName);
        System.out.println("--------------------");
        System.out.println("Continents and their bonuses");
        for (Map.Entry<Integer, Integer> entry : this.d_continentBonusMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("--------------------");
        System.out.println("Continents and their Countries");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_continentCountriesMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
        System.out.println("--------------------");
        System.out.println("Borders");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_adjacencyMap.entrySet()) {
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
        for (Map.Entry<Integer, Continent> entry : this.d_continentGameStateMap.entrySet()) {
            entry.getValue().printContinent(entry.getKey(), entry.getValue());
        }
        System.out.println("--------------------");
        System.out.println("Borders");
        System.out.println("Country: Owned by, \tArmies - [Neighbours]");

        for (Map.Entry<Integer, Country> entry : this.d_countryGameStateMap.entrySet()) {
            int l_countryId = entry.getKey();
            Country l_country = entry.getValue();
            if(null != l_country.getPlayer()) {
                System.out.print(l_countryId + ": " + l_country.getPlayer().getName() + ",\t");
                System.out.print(l_country.getArmies() + " - ");
                System.out.println(d_adjacencyMap.get(l_countryId));
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
        List<Integer> l_continentIds = new ArrayList<>(d_continentCountriesMap.keySet());
        for(Integer l_id : l_continentIds) {
            if(d_continentCountriesMap.get(l_id).contains(p_countryId)) {
                return l_id;
            }
        }
        // Shouldn't reach this return
        return -1;
    }
}
