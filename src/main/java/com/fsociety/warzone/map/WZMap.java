package com.fsociety.warzone.map;

import java.util.*;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.util.UserInstructionUtils;

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

    private String d_mapFileName;

    public WZMap() {
        this.d_adjacencyMap = new LinkedHashMap<>();
        this.d_continentBonusMap = new HashMap<>();
        this.d_continentCountriesMap = new HashMap<>();
        this.d_countryGameStateMap = new HashMap<>();
        this.d_continentGameStateMap = new HashMap<>();
        this.d_countryContinentGameStateMap = new HashMap<>();
        this.d_mapFileName="";
    }

    public WZMap(
            final Map<Integer, Set<Integer>> p_adjacencyMap,
            final Map<Integer, Integer> p_continentBonusMap,
            final Map<Integer, Set<Integer>> p_continentCountriesMap) {
        this.d_adjacencyMap = p_adjacencyMap;
        this.d_continentBonusMap = p_continentBonusMap;
        this.d_continentCountriesMap = p_continentCountriesMap;
        this.d_countryGameStateMap = new HashMap<>();
        this.d_continentGameStateMap = new HashMap<>();
        this.d_countryContinentGameStateMap = new HashMap<>();
        this.d_mapFileName="";
    }

    /**
     * Adds a new continent to the map
     * 
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    public void addContinent(
            final Integer p_continentId,
            final Integer p_continentBonus) {
        if (d_continentCountriesMap.get(p_continentId) != null) {
            UserInstructionUtils.promptUser("Continent already exists");
        }
        d_continentCountriesMap.put(p_continentId, new LinkedHashSet<>());
        d_continentBonusMap.put(p_continentId, p_continentBonus);
    }

    /**
     * Adds a country to the map
     * 
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    public void addCountry(
            final Integer p_countryId,
            final Integer p_continentId) {
        if (d_continentCountriesMap.get(p_continentId) == null) {
            throw new IllegalArgumentException("Continent does not exist in the Map");
        } else if (d_adjacencyMap.get(p_countryId) != null) {
            UserInstructionUtils.promptUser("Country already exists");
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
            UserInstructionUtils.promptUser("Country cannot be neighbour of itself");
        } else if (d_adjacencyMap.get(p_countryId) == null) {
            UserInstructionUtils.promptUser("Country does not exist or is invalid");
        } else if (d_adjacencyMap.get(p_neighbourCountryId) == null) {
            throw new IllegalArgumentException("Neighbour Country does not exist or is invalid");
        } else {
            d_adjacencyMap.get(p_countryId).add(p_neighbourCountryId);
            d_adjacencyMap.get(p_neighbourCountryId).add(p_countryId);
        }
    }

    public void setName(String name) {
        this.d_mapFileName = name;
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
            UserInstructionUtils.promptUser("Country cannot be neighbour of itself");
        } else if (d_adjacencyMap.get(p_countryId) == null) {
            UserInstructionUtils.promptUser("Country does not exist or is invalid");
        } else if (d_adjacencyMap.get(p_neighbourCountryId) == null) {
            throw new IllegalArgumentException("Neighbour Country does not exist or is invalid");
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
            UserInstructionUtils.promptUser("Continent does not exist");
        } else {
            d_continentCountriesMap.get(p_continentId).forEach(d_adjacencyMap::remove);
            d_continentBonusMap.remove(p_continentId);
            d_continentCountriesMap.remove(p_continentId);
        }
    }

    /**
     * Removes a country from the map
     * 
     * @param p_countryId   the country to remove
     */
    public void removeCountry(final int p_countryId) {
        if (d_adjacencyMap.get(p_countryId) == null) {
            UserInstructionUtils.promptUser("Country does not exist");
        } else {
            d_adjacencyMap.remove(p_countryId);
            int l_continentId = getContinentIdForCountry(p_countryId);
            d_continentCountriesMap.get(l_continentId).remove(p_countryId);
        }
    }

    public String getName() {
        return d_mapFileName;
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
        d_adjacencyMap.keySet().forEach(countryId -> {
            d_countryGameStateMap.put(countryId, new Country(countryId));
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
        final Integer l_currentPlayerId = d_countryGameStateMap.get(p_countryId).getPlayerId();
        if (l_currentPlayerId != p_playerId) {
            if (l_currentPlayerId == -1) {
                d_countryContinentGameStateMap.get(p_countryId).initCountryCount(p_playerId);
            } else {
                d_countryContinentGameStateMap.get(p_countryId).updateCountryCount(l_currentPlayerId, p_playerId);
            }
        }
        d_countryGameStateMap.get(p_countryId).setPlayerId(p_playerId).setArmies(p_armies);
        d_countryGameStateMap.get(p_countryId).setPlayer(GameEngine.getPlayerList().get(p_playerId));
    }

    /**
     * get the current game state for a country
     *
     * @param p_countryId
     * @return the current game state for a country
     */
    public Country getGameState(final int p_countryId) {
        return d_countryGameStateMap.get(p_countryId);
    }

    /**
     * Display the current Map for the map editor. Show continents, countries and neighbours
     */
    public void showMapForEditor() {
        System.out.println("Showing map for Editor");
    }

    /**
     * Display the current Map for the gameplay details. Show owner and armies
     */
    public void showMapForGame() {
        System.out.println("Showing map for Game");
    }

    /**
     * get the continent ID for a country
     *
     * @param p_countryId
     * @return the continent ID for a country
     */
    private Integer getContinentIdForCountry(Integer p_countryId) {
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
