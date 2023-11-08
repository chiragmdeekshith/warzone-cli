package com.fsociety.warzone.model.map;

import com.fsociety.warzone.view.Console;
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
     * @return true if the continent is added, false otherwise
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
     * @return true if the continent is removed, false otherwise
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
     * @return true if the country is added, false otherwise
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
     * @return true if the country is removed, false otherwise
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
     * @return true if the neighbour is added, false otherwise
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
     * @return true if the neighbour is removed, false otherwise
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
     * Display the current Map for the map editor: show continents, countries and neighbours.
     */
    @Override
    public void showMap() {
        StringBuilder l_editMapString = new StringBuilder();
        l_editMapString.append("\nMap: ").append(d_fileName).append("\n");
        l_editMapString.append("--------------------" + "\n");
        l_editMapString.append("Continents" + "\n");
        l_editMapString.append("Continent: Bonus - [Countries]\n");
        for (Map.Entry<Integer, Set<Integer>> l_entry : this.d_countriesInContinent.entrySet()) {
            l_editMapString
                    .append(l_entry.getKey())
                    .append(", ")
                    .append(this.d_continentBonuses.get(l_entry.getKey()))
                    .append(": ")
                    .append(l_entry.getValue().toString())
                    .append("\n");
        }
        l_editMapString.append("--------------------\n");
        l_editMapString.append("Borders\n");
        l_editMapString.append("Country: [Neighbours]\n");
        for (Map.Entry<Integer, Set<Integer>> entry : this.d_neighbours.entrySet()) {
            l_editMapString
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue().toString())
                    .append("\n");
        }
        l_editMapString.append("--------------------\n");
        Console.print(l_editMapString.toString());
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
