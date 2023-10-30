package com.fsociety.warzone.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class acts as a superclass for the Editable Map and the Playable Map
 */
public abstract class AbstractMap {

    protected String d_fileName;
    // country id -> Set of ids of neighbours
    protected Map<Integer, Set<Integer>> d_neighbours;

    // continent id -> Set of ids of countries
    protected Map<Integer, Set<Integer>> d_countriesInContinent;

    // continent id -> bonus for control of continent
    protected Map<Integer, Integer> d_continentBonuses;
    AbstractMap(){
        this.d_continentBonuses = new HashMap<>();
        this.d_countriesInContinent = new HashMap<>();
        this.d_neighbours = new LinkedHashMap<>();
    }

    abstract void showMap();

    public String getFileName() {
        return d_fileName;
    }

    public void setFileName(String d_fileName) {
        this.d_fileName = d_fileName;
    }

    public Map<Integer, Set<Integer>> getNeighbours() {
        return d_neighbours;
    }

    public void setNeighbours(Map<Integer, Set<Integer>> d_neighbours) {
        this.d_neighbours = d_neighbours;
    }

    public Map<Integer, Set<Integer>> getCountriesInContinent() {
        return d_countriesInContinent;
    }

    public void setCountriesInContinent(Map<Integer, Set<Integer>> d_countriesInContinent) {
        this.d_countriesInContinent = d_countriesInContinent;
    }

    public Map<Integer, Integer> getContinentBonuses() {
        return d_continentBonuses;
    }

    public void setContinentBonuses(Map<Integer, Integer> d_continentBonuses) {
        this.d_continentBonuses = d_continentBonuses;
    }
}
