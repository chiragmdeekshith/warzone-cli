package com.fsociety.warzone.models.map;

import com.fsociety.warzone.utils.Console;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is used by the Map Editor for physically altering the map based on user input.
 */
public class EditMap extends AbstractMap {

    /**
     * Adds a new continent to the map.
     *
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    public boolean addContinent(int p_continentId, int p_continentBonus) {
        if (d_countriesInContinent.get(p_continentId) != null) {
            Console.print("Continent "+ p_continentId +" already exists.");
            return false;
        }
        d_countriesInContinent.put(p_continentId, new LinkedHashSet<>());
        d_continentBonuses.put(p_continentId, p_continentBonus);
        return true;
    }

    /**
     * Removes a continent from the map
     *
     * @param p_continentId the continent to remove
     */
    public boolean removeContinent(int p_continentId){
        if (d_countriesInContinent.get(p_continentId) == null) {
            Console.print("Continent " + p_continentId + " does not exist.");
            return false;
        }
        d_countriesInContinent.get(p_continentId).forEach(this::removeAdjacency);
        d_continentBonuses.remove(p_continentId);
        d_countriesInContinent.remove(p_continentId);
        return true;
    }

    /**
     * Adds a country to the map.
     *
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    public boolean addCountry(int p_countryId, int p_continentId) {
        if (d_countriesInContinent.get(p_continentId) == null) {
            Console.print("Continent "+ p_continentId +" does not exist in the Map.");
            return false;
        }
        if (d_neighbours.get(p_countryId) != null) {
            Console.print("Country "+ p_countryId +" already exists.");
            return false;
        }
        d_neighbours.put(p_countryId, new LinkedHashSet<>());
        d_countriesInContinent.get(p_continentId).add(p_countryId);
        return true;
    }

    /**
     * Removes a country from the map.
     *
     * @param p_countryId   the country to remove
     */
    public boolean removeCountry(int p_countryId){
        if (d_neighbours.get(p_countryId) == null) {
            Console.print("Country "+ p_countryId +" does not exist.");
            return false;
        }
        this.removeAdjacency(p_countryId);
        int l_continentId = getContinentIdForCountry(p_countryId);
        d_countriesInContinent.get(l_continentId).remove(p_countryId);
        return true;
    }

    /**
     * Adds a neighbour to a country.
     *
     * @param p_countryId          the id of the country to add the neighbour to
     * @param p_neighbourCountryId the id of the neighbour to add
     */
    public boolean addNeighbour(int p_countryId, int p_neighbourCountryId) {
        if (p_countryId == p_neighbourCountryId) {
            Console.print("Country "+ p_countryId +" cannot be neighbour of itself.");
            return false;
        }
        if (d_neighbours.get(p_countryId) == null) {
            Console.print("Country "+ p_countryId +" does not exist or is invalid.");
            return false;
        }
        if (d_neighbours.get(p_neighbourCountryId) == null) {
            Console.print("Neighbour Country " + p_neighbourCountryId + " does not exist or is invalid.");
            return false;
        }
        d_neighbours.get(p_countryId).add(p_neighbourCountryId);
        d_neighbours.get(p_neighbourCountryId).add(p_countryId);
        return true;
    }

    /**
     * Removes a neighbour from a country.
     *
     * @param p_countryId          the id of the country to remove the neighbour
     * @param p_neighbourCountryId the id of the neighbour to remove
     */
    public boolean removeNeighbour(int p_countryId, int p_neighbourCountryId){
        if (p_countryId == p_neighbourCountryId) {
            Console.print("Country "+ p_countryId +" cannot be neighbour of itself.");
            return false;
        }
        if (d_neighbours.get(p_countryId) == null) {
            Console.print("Country "+ p_countryId +" does not exist or is invalid.");
            return false;
        }
        if (d_neighbours.get(p_neighbourCountryId) == null) {
            Console.print("Neighbour Country "+ p_neighbourCountryId +" does not exist or is invalid.");
            return false;
        }
        d_neighbours.get(p_countryId).remove(p_neighbourCountryId);
        d_neighbours.get(p_neighbourCountryId).remove(p_countryId);
        return true;
    }

    /**
<<<<<<< HEAD:src/main/java/com/fsociety/warzone/models/map/EditMap.java
=======
     * Display the current Map for the map editor: show continents, countries and neighbours.
     */
    @Override
    public void showMap() {
        Console.print("\nMap: " + d_fileName);
        Console.print("--------------------");
        Console.print("Continents and their bonuses");
        for (Map.Entry<Integer, Integer> entry : this.d_continentBonuses.entrySet()) {
            Console.print(entry.getKey() + ": " + entry.getValue().toString());
        }
        Console.print("--------------------");
        Console.print("Continents and their Countries");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_countriesInContinent.entrySet()) {
            Console.print(entry.getKey() + ": " + entry.getValue().toString());
        }
        Console.print("--------------------");
        Console.print("Borders");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_neighbours.entrySet()) {
            Console.print(entry.getKey() + ": " + entry.getValue().toString());
        }
        Console.print("--------------------\n");
    }


    /**
>>>>>>> 852960947efa5fa0001ddb1cdda4a21646a934b4:src/main/java/com/fsociety/warzone/map/EditMap.java
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
