package com.fsociety.warzone.map;

import java.util.Map;
import java.util.Set;

public abstract class AbstractMap {

    protected String d_filename = null;
    // country id -> Set of ids of neighbours
    protected Map<Integer, Set<Integer>> d_neighbours = null;

    // continent id -> Set of ids of countries
    protected Map<Integer, Set<Integer>> d_countriesInContinent = null;

    // continent id -> bonus for control of continent
    protected Map<Integer, Integer> d_continentBonuses = null;

    abstract void showMap();

}
