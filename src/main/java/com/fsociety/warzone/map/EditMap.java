package com.fsociety.warzone.map;


import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is used by the Map Editor for physically altering the map based on user input
 */
public class EditMap extends AbstractMap {

    /**
     * Default constructor calling the super class' constructor
     */
    public EditMap(){
        super();
    }

    /**
     * Adds a new continent to the map.
     *
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    public void addContinent(int p_continentId, int p_continentBonus) {
        if (d_countriesInContinent.get(p_continentId) != null) {
            System.out.println("Continent already exists.");
            return;
        }
        d_countriesInContinent.put(p_continentId, new LinkedHashSet<>());
        d_continentBonuses.put(p_continentId, p_continentBonus);
    }

    /**
     * Removes a continent from the map
     *
     * @param p_continentId the continent to remove
     */
    void removeContinent(int p_continentId){
        if (d_countriesInContinent.get(p_continentId) == null) {
            System.out.println("Continent does not exist.");
        } else {
            d_countriesInContinent.get(p_continentId).forEach(this::removeAdjacency);
            d_continentBonuses.remove(p_continentId);
            d_countriesInContinent.remove(p_continentId);
        }
    }

    /**
     * Adds a country to the map.
     *
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    public void addCountry(int p_countryId, int p_continentId) {
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
     * Removes a country from the map.
     *
     * @param p_countryId   the country to remove
     */
    void removeCountry(int p_countryId){
        if (d_neighbours.get(p_countryId) == null) {
            System.out.println("Country does not exist.");
        } else {
            this.removeAdjacency(p_countryId);
            int l_continentId = getContinentIdForCountry(p_countryId);
            d_countriesInContinent.get(l_continentId).remove(p_countryId);
        }
    }

    /**
     * Adds a neighbour to a country
     *
     * @param p_countryId          the id of the country to add the neighbour to
     * @param p_neighbourCountryId the id of the neighbour to add
     */
    public void addNeighbour(int p_countryId, int p_neighbourCountryId) {
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
     * Removes a neighbour from a country
     *
     * @param p_countryId          the id of the country to remove the neighbour
     * @param p_neighbourCountryId the id of the neighbour to remove
     */
    void removeNeighbour(int p_countryId, int p_neighbourCountryId){
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
     * Display the current Map for the map editor. Show continents, countries and neighbours
     */
    @Override
    void showMap() {
        System.out.println("\nMap: " + d_fileName);
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
     * Get the continent ID for a country
     *
     * @param p_countryId the country ID
     * @return the continent ID for a country
     */
    public int getContinentIdForCountry(int p_countryId) {
        List<Integer> l_continentIds = new ArrayList<>(d_countriesInContinent.keySet());
        for(int l_id : l_continentIds) {
            if(d_countriesInContinent.get(l_id).contains(p_countryId)) {
                return l_id;
            }
        }
        // Shouldn't reach this return
        return -1;
    }

    /**
     * Removes the adjacency to all neighbouring countries for the given country.
     * Removing current country from neighbours so that it doesn't point to a
     * country that doesn't exist. It can mainly happen when the current country is
     * adjacent to a country in a different continent.
     *
     * @param p_countryId the country to remove the adjacency
     */
    private void removeAdjacency(int p_countryId) {
        Set<Integer> l_neighbours = d_neighbours.get(p_countryId);
        l_neighbours.forEach(neighbourId -> d_neighbours.get(neighbourId).remove(p_countryId));
        d_neighbours.remove(p_countryId);
    }
}
