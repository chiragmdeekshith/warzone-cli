package com.fsociety.warzone.model;

import java.util.HashMap;
import java.util.Map;

public class Continent {
    // ids of players -> Set of ids of countries
    private final Map<Integer, Integer> d_playerCountryCountMap;

    public Continent() {
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
}
