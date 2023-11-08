package com.fsociety.warzone.model.map;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * This class acts as a superclass for the Editable Map and the Playable Map.
 */
public abstract class AbstractMap {

    protected String d_fileName;
    // country id -> Set of ids of neighbours
    protected Map<Integer, Set<Integer>> d_neighbours;

    // continent id -> Set of ids of countries
    protected Map<Integer, Set<Integer>> d_countriesInContinent;

    // continent id -> bonus for control of continent
    protected Map<Integer, Integer> d_continentBonuses;

    /**
     * Default Constructor for the class. Initializes the data members.
     */
    AbstractMap(){
        this.d_continentBonuses = new HashMap<>();
        this.d_countriesInContinent = new HashMap<>();
        this.d_neighbours = new LinkedHashMap<>();
    }

    /**
     * A common function for the implementing subclasses which needs to be overridden to suit its needs.
     */
    abstract void showMap();

    // Getters and Setters

    /**
     * Get the name of the file loaded.
     * @return the file name
     */
    public String getFileName() {
        return d_fileName;
    }

    /**
     * Set the name of the file to be saved.
     * @param d_fileName the new file name
     */
    public void setFileName(String d_fileName) {
        this.d_fileName = d_fileName;
    }

    /**
     * Retrieve the neighbours HashMap object containing HashSets of neighbouring countries
     * @return the neighbours HashMap
     */
    public Map<Integer, Set<Integer>> getNeighbours() {
        return d_neighbours;
    }

    /**
     * Set the neighbours object
     * @param p_neighbours the neighbours HashMap
     */
    public void setNeighbours(Map<Integer, Set<Integer>> p_neighbours) {
        this.d_neighbours = p_neighbours;
    }

    /**
     * Retrieve the HashMap containing the list of continents and their respective countries stored
     * in their respective HashSets
     * @return d_countriesInContinent - the object with the countries in each continent
     */
    public Map<Integer, Set<Integer>> getCountriesInContinent() {
        return d_countriesInContinent;
    }

    /**
     * Set the HashMap containing the list of continents and their respective countries stored
     * in their respective HashSets
     * @param p_countriesInContinent - the object with the countries in each continent
     */
    public void setCountriesInContinent(Map<Integer, Set<Integer>> p_countriesInContinent) {
        this.d_countriesInContinent = p_countriesInContinent;
    }

    /**
     * Retrieve the object which stores the bonus values for each continent.
     * @return d_continentBonuses - the bonus values for each continent
     */
    public Map<Integer, Integer> getContinentBonuses() {
        return d_continentBonuses;
    }

    /**
     * Set the object which stores the bonus values for each continent.
     * @param p_continentBonuses - the bonus values for each continent
     */
    public void setContinentBonuses(Map<Integer, Integer> p_continentBonuses) {
        this.d_continentBonuses = p_continentBonuses;
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

}
