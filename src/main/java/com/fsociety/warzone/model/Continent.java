package com.fsociety.warzone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Continent {
    // ids of players -> Set of ids of countries
    private final Map<Integer, Integer> d_playerCountryCountMap;

    private ArrayList<Country> d_countries;
    private int d_armiesBonus;
    private Player d_continentOwner;
    public int getArmiesBonus() {
        return this.d_armiesBonus;
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
     * This method stores the player object of the player that owns every country in the continent, if that player exists.
     *
     */
    public void setContinentOwner() {
        Player d_computedContinentOwner = d_countries.get(0).getPlayer();
        if (d_computedContinentOwner != null) {
            for (int i = 1; i < d_countries.size(); i++) {
                if (!d_computedContinentOwner.equals(d_countries.get(i).getPlayer())) {
                    d_computedContinentOwner = null;
                    break;
                }
            }
        }
        this.d_continentOwner = d_computedContinentOwner;
    }

    /**
     * This method returns the player object of the player that owns every country in the continent. If no such player
     * exists, this method returns null.
     * @return d_continentOwner
     */
    public Player getContinentOwner() {
        return this.d_continentOwner;
    }

    public void printContinent(int continentId)
    {
        if(d_playerCountryCountMap != null && !d_playerCountryCountMap.isEmpty())
        {
            System.out.println("For Continent " + continentId);
            for (Map.Entry<Integer, Integer> entry : this.d_playerCountryCountMap.entrySet()) {
                System.out.println("player" + entry.getKey() + " owns country " + entry.getValue().toString());
            }
        }

    }

}
