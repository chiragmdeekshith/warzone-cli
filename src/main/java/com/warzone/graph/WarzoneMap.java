package com.warzone.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.warzone.model.Country;

public class WarzoneMap {

    private final Map<String, LinkedList<Country>> adjacencyList;
    private final Map<String, List<String>> continentVsCountries;

    public WarzoneMap() {
        this.adjacencyList = new LinkedHashMap<>();
        this.continentVsCountries = new LinkedHashMap<>();
    }

    /**
     * Adds a continent to the map
     * 
     * @param continentName the name of the continent to add
     */
    public void addContinent(final String continentName) {
        if (continentVsCountries.get(continentName) != null) {
            throw new IllegalArgumentException("Continent already exists");
        } else {
            continentVsCountries.put(continentName, new ArrayList<>());
        }
    }

    /**
     * Adds a country to the map
     * 
     * @param country the country to add
     */
    public void addCountry(final Country country) {
        if (country.getName() == null) {
            throw new IllegalArgumentException("Country name cannot be null");
        } else if (adjacencyList.get(country.getName()) != null) {
            throw new IllegalArgumentException("Country already exists");
        } else {
            adjacencyList.put(country.getName(), new LinkedList<>());
        }
    }

    /**
     * Adds a neighbour to a country
     * 
     * @param country   the country to add the neighbour to
     * @param neighbour the neighbour to add
     */
    public void addNeighbour(
            final Country country,
            final Country neighbour) {
        if (adjacencyList.get(country.getName()) == null) {
            throw new IllegalArgumentException("Country does not exist");
        } else if (adjacencyList.get(neighbour.getName()) != null) {
            throw new IllegalArgumentException("Neighbour already exists");
        } else {
            adjacencyList.get(country.getName()).add(neighbour);
        }
    }

    /**
     * Removes a country from the map
     * 
     * @param country the country to remove
     */
    public void removeCountry(final Country country) {
        if (adjacencyList.get(country.getName()) == null) {
            throw new IllegalArgumentException("Country does not exist");
        } else {
            adjacencyList.remove(country.getName());
        }
    }
}
