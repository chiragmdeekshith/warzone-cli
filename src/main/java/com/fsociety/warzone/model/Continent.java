package com.fsociety.warzone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Continent {
    // ids of players -> Set of ids of countries
    private final Map<Integer, Integer> d_playerCountryCountMap;

    private ArrayList<Country> d_countries;

    public Continent(Set<Integer> p_countryIds, Map<Integer, Country> d_map) {
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
     * This method returns the player object that owns every country in the continent if it exists.
     * @return
     */
    public Player continentOwner() {
        return null;
    }


}
