package com.fsociety.warzone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Continent {
    // ids of players -> Set of ids of countries
    private final Map<Integer, Integer> d_playerCountryCountMap;

    private ArrayList<Country> d_countries;
    private int d_armiesBonus;
    private Player d_continentOwner;
    public int getArmiesBonus() {
        return this.d_armiesBonus;
    }
    public Player getContinentOwner() {
        return this.d_continentOwner;
    }

    public Continent(Set<Integer> p_countryIds, Map<Integer, Country> d_map, int p_bonus) {
        this.d_armiesBonus = p_bonus;
        this.d_countries = new ArrayList<>();
        p_countryIds.forEach(d_countryId -> {
            this.d_countries.add(d_map.get(d_countryId));
        });
        this.d_playerCountryCountMap = new HashMap<>();
    }

    public void initCountryCount(final int p_playerId) {
        this.d_playerCountryCountMap.put(p_playerId, 1);
    }

    public void updateCountryCount(
            final int p_currentPlayerId,
            final int p_newPlayerId) {
        this.d_playerCountryCountMap.put(p_currentPlayerId, this.d_playerCountryCountMap.get(p_currentPlayerId) - 1);
        if (this.d_playerCountryCountMap.get(p_currentPlayerId) == 0) {
            this.d_playerCountryCountMap.remove(p_currentPlayerId);
        }
        this.d_playerCountryCountMap.put(p_newPlayerId, this.d_playerCountryCountMap.get(p_newPlayerId) + 1);
    }

    /**
     * This method computes and stores the Player object of the player that owns every country in the continent,
     * if that player exists.
     */
    public void setContinentOwner() {
        Player l_computedContinentOwner = d_countries.get(0).getPlayer();
        if (l_computedContinentOwner != null) {
            for (int i = 1; i < d_countries.size(); i++) {
                if (!l_computedContinentOwner.equals(d_countries.get(i).getPlayer())) {
                    l_computedContinentOwner = null;
                    break;
                }
            }
        }
        this.d_continentOwner = l_computedContinentOwner;
    }

    public void printContinent(int continentId, Continent continent) {
        if(d_playerCountryCountMap != null && !d_playerCountryCountMap.isEmpty()) {
            System.out.println(continentId + ": " + continent.d_armiesBonus + " - [" + Country.printCountries(continent.d_countries) + "]");
        }
    }
}
