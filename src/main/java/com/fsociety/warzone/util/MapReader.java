package com.fsociety.warzone.util;

import com.fsociety.warzone.model.map.AbstractMap;
import com.fsociety.warzone.view.Console;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * This class is used by the DominationMapTools and ConquestMapTools to load the data into the AbstractMap object.
 */
public class MapReader {
    /**
     *
     * This functions loads the data from the file into the AbstractMap object.
     *
     * @param p_map - the AbstractMap object that we need to load data into
     * @param p_data - the StringBuilder object that contains the data from the file
     * @return true if the data is loaded in correctly, false otherwise
     */
    public static boolean loadDataFromFile(AbstractMap p_map, StringBuilder p_data, String p_mapType) {
        if (p_mapType.equals(("domination"))) {
            String[] l_continentData = p_data.substring(p_data.toString().toLowerCase().indexOf("[continents]")+13, p_data.toString().toLowerCase().indexOf("[countries]")).split("\n");
            String[] l_countryData = p_data.substring(p_data.toString().toLowerCase().indexOf("[countries]")+12, p_data.toString().toLowerCase().indexOf("[borders]")).split("\n");
            String[] l_neighborData = p_data.substring(p_data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
            int l_counter = 0;
            for(String l_s:l_continentData) {
                int l_continentId = ++l_counter;
                String[] l_splitData = l_s.split(" ");
                int l_continentBonusValue = Integer.parseInt(l_splitData[1]);
                if (!loadContinent(p_map,l_continentId, l_continentBonusValue)){
                    return false;
                }
            }
            for(String l_s:l_countryData) {
                String[] l_splitData = l_s.split(" ");
                int l_countryId = Integer.parseInt(l_splitData[0]);
                int l_continentId = Integer.parseInt(l_splitData[2]);
                if (!loadCountry(p_map,l_countryId, l_continentId)) {
                    return false;
                }
            }
            for(String l_s:l_neighborData) {
                String[] l_splitData = l_s.split(" ");
                int[] l_neighborList = new int[l_splitData.length];
                int l_countryId= Integer.parseInt(l_splitData[0]);
                for (int l_iterator=1;l_iterator<l_splitData.length;l_iterator++) {
                    if(Arrays.binarySearch(l_neighborList,Integer.parseInt(l_splitData[l_iterator]))>0) {
                        Console.print("Duplicate neighbours cannot exist.");
                        return false;
                    }
                    l_neighborList[l_iterator] = Integer.parseInt(l_splitData[l_iterator]);
                    if (!loadNeighbour(p_map,l_countryId,l_neighborList[l_iterator])) {
                        return false;
                    }
                }
            }
        }
        else if (p_mapType.equals("conquest")) {
            String[] l_continentData = p_data.substring(p_data.toString().toLowerCase().indexOf("[continents]")+13, p_data.toString().toLowerCase().indexOf("[territories]")).split("\n");
            String[] l_territoriesData = p_data.substring(p_data.toString().toLowerCase().indexOf("[territories]")+14).split("\n");
            HashMap<String,Integer> l_continentMap= new HashMap<>();
            HashMap<String,Integer> l_countryMap= new HashMap<>();
            int l_counter = 0;
            for (String l_s:l_continentData) {
                int l_continentId = ++l_counter;
                String[] l_splitData = l_s.split("=");
                l_continentMap.put(l_splitData[0],l_continentId);
                int l_continentBonusValue = Integer.parseInt(l_splitData[1]);
                if (!loadContinent(p_map,l_continentId, l_continentBonusValue)){
                    return false;
                }
            }
            l_counter = 0;
            for (String l_s:l_territoriesData) {
                int l_countryId = ++l_counter;
                String[] l_splitData = l_s.split(",");
                l_countryMap.put(l_splitData[0],l_countryId);
                if(l_continentMap.get(l_splitData[3]) == null) {
                    Console.print("Continent "+l_splitData[3]+" does not exist.");
                    return false;
                }
                else if(!loadCountry(p_map,l_countryId, l_continentMap.get(l_splitData[3]))) {
                    return false;
                }
            }
            for (String l_s:l_territoriesData) {
                String[] l_splitData = l_s.split(",");
                int l_countryId = l_countryMap.get(l_splitData[0]);
                for (int l_iterator=4;l_iterator<l_splitData.length;l_iterator++) {
                    if(l_countryMap.get(l_splitData[l_iterator]) == null) {
                        Console.print("Country "+l_splitData[l_iterator]+" does not exist.");
                        return false;
                    }
                    else if (!loadNeighbour(p_map,l_countryId,l_countryMap.get(l_splitData[l_iterator]))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Loads a new continent to the AbstractMap object.
     *
     * @param p_map            the AbstractMap object to which we are adding the continent to
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    private static boolean loadContinent(AbstractMap p_map, int p_continentId, int p_continentBonus) {
        if (p_map.getCountriesInContinent().get(p_continentId) != null) {
            Console.print("Continent "+p_continentId+" already exists.");
            return false;
        } else {
            p_map.getCountriesInContinent().put(p_continentId, new LinkedHashSet<>());
            p_map.getContinentBonuses().put(p_continentId, p_continentBonus);
            return true;
        }
    }

    /**
     * Loads a country to the AbstractMap object, and it's to the respective continent.
     *
     * @param p_map         the AbstractMap object to which we are adding the country to
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    private static boolean loadCountry(AbstractMap p_map, int p_countryId, int p_continentId) {
        if (p_map.getCountriesInContinent().get(p_continentId) == null) {
            Console.print("Continent"+p_continentId+" does not exist.");
            return false;
        } else if (p_map.getNeighbours().get(p_countryId) != null) {
            Console.print("Country"+p_countryId+" already exists.");
            return false;
        } else {
            p_map.getNeighbours().put(p_countryId, new LinkedHashSet<>());
            p_map.getCountriesInContinent().get(p_continentId).add(p_countryId);
            return true;
        }
    }

    /**
     * Loads a neighbour to a AbstractMap object, and it's to the respective country.
     *
     * @param p_map                the AbstractMap object to which we are adding the country to
     * @param p_countryId          the id of the country to add the neighbour to
     * @param p_neighbourCountryId the id of the neighbour to add
     */
    private static boolean loadNeighbour(AbstractMap p_map, int p_countryId, int p_neighbourCountryId) {
        if (p_countryId == p_neighbourCountryId) {
            Console.print("Country cannot be neighbour of itself.");
            return false;
        } else if (p_map.getNeighbours().get(p_countryId) == null) {
            Console.print("Country "+p_countryId+" does not exist or is invalid.");
            return false;
        } else if (p_map.getNeighbours().get(p_neighbourCountryId) == null) {
            Console.print("Neighbour Country "+p_neighbourCountryId+" does not exist or is invalid.");
            return false;
        } else {
            p_map.getNeighbours().get(p_countryId).add(p_neighbourCountryId);
            p_map.getNeighbours().get(p_neighbourCountryId).add(p_countryId);
            return true;
        }
    }
}